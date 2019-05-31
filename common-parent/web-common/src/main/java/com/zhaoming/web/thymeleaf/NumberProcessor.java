package com.zhaoming.web.thymeleaf;

import com.zhaoming.base.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import static com.zhaoming.web.thymeleaf.ThymeleafFacade.evaluateAsStringsWithDelimiter;
import static com.zhaoming.web.thymeleaf.ThymeleafFacade.getRawValue;

/**
 * NumberProcessor
 *
 * @author hhx
 */
public class NumberProcessor extends AbstractAttributeTagProcessor {

    private static final String DELIMITER = ",";

    private static final String ATTR_NAME = "number";
    private static final int PRECEDENCE = 100;

    /**
     * 金额分转元保留2位小数
     */
    private static final String F2Y="fen2yuan";

    public NumberProcessor(final String dialectPrefix) {
        super(
                TemplateMode.HTML,
                dialectPrefix,
                null,
                false,
                ATTR_NAME,
                true,
                PRECEDENCE,
                true);
    }

    @Override
    protected void doProcess(ITemplateContext context,
                             IProcessableElementTag tag, AttributeName attributeName,
                             String attributeValue, IElementTagStructureHandler structureHandler) {
        //获取标签内容表达式
        final String rawValue = getRawValue(tag, attributeName);
        String method=null;
        String exper=null;
        if(StringUtils.isNotEmpty(rawValue)){
            //获取类型
            method=rawValue.split(CommonConstant.COLON)[0];
            //获取表达式
            exper=rawValue.split(CommonConstant.COLON)[1];
        }
        //通过IStandardExpression 解析器 解析表达式获取参数
        final List<String> values = evaluateAsStringsWithDelimiter(context, exper, DELIMITER);
        //标签名
        final String elementCompleteName = tag.getElementCompleteName();
        //创建模型
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();
        //添加模型 标签
        model.add(modelFactory.createOpenElementTag(elementCompleteName));
        for (String value : values) {
            //创建 html5标签 文本返回数据
            if(F2Y.equalsIgnoreCase(method)){
                try{
                    double n = NumberFormat.getInstance().parse(value).doubleValue();
                    model.add(modelFactory.createText(HtmlEscape.escapeHtml5(formatMoney(n))));
                }catch (ParseException e){
                    model.add(modelFactory.createText(HtmlEscape.escapeHtml5("0")));
                }

            }
        }
        //添加模型 标签
        model.add(modelFactory.createCloseElementTag(elementCompleteName));
        //替换页面标签
        structureHandler.replaceWith(model, false);
    }

    private String formatMoney(double value) {
        //格式化小数，不足的补0
        DecimalFormat df = new DecimalFormat("0.00");
        //返回的是String类型的
        return df.format(value/100);
    }
}
