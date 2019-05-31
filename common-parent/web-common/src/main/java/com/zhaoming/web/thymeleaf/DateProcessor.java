package com.zhaoming.web.thymeleaf;

import com.zhaoming.base.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import static com.zhaoming.web.thymeleaf.ThymeleafFacade.evaluateAsStringsWithDelimiter;
import static com.zhaoming.web.thymeleaf.ThymeleafFacade.getRawValue;

/**
 * DateProcessor
 * long 2 dateString
 * @author hhx
 */
public class DateProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTR_NAME = "date";
    private static final int PRECEDENCE = 99;

    /**
     * 默认格式  yyyy-MM-dd hh:mm:ss
     */
    private static final String STANDARD_LAYOUT = "yyyy-MM-dd hh:mm:ss";

    public DateProcessor(final String dialectPrefix) {
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
        String layout = null;
        String exper = rawValue;
        int colonIndex = rawValue.lastIndexOf(CommonConstant.COLON);
        if(StringUtils.isNotEmpty(rawValue)){
            if(colonIndex > 0){
                //获取类型
                layout = rawValue.substring(0, colonIndex);
                //获取表达式
                exper = rawValue.substring(colonIndex + 1);
            }
        }
        //通过IStandardExpression 解析器 解析表达式获取参数
        final List<String> values = evaluateAsStringsWithDelimiter(context, exper, ",");
        //标签名
        final String elementCompleteName = tag.getElementCompleteName();
        //创建模型
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();
        //添加模型 标签
        model.add(modelFactory.createOpenElementTag(elementCompleteName));
        for (String value : values) {
            //创建 html5标签 文本返回数据
            if(StringUtils.isBlank(layout)){
                // 默认格式
                try{
                    Long millis = NumberFormat.getInstance().parse(value).longValue();
                    model.add(modelFactory.createText(HtmlEscape.escapeHtml5(formatDate(millis, STANDARD_LAYOUT))));
                }catch (ParseException e){
                    model.add(modelFactory.createText(HtmlEscape.escapeHtml5("date format error")));
                }
            }else{
                // 自定义格式
                try{
                    Long millis = NumberFormat.getInstance().parse(value).longValue();
                    model.add(modelFactory.createText(HtmlEscape.escapeHtml5(formatDate(millis, layout))));
                }catch (ParseException e){
                    model.add(modelFactory.createText(HtmlEscape.escapeHtml5("date format error")));
                }
            }
        }
        //添加模型 标签
        model.add(modelFactory.createCloseElementTag(elementCompleteName));
        //替换页面标签
        structureHandler.replaceWith(model, false);
    }

    private String formatDate(Long millis, String layout) {
        FastDateFormat instance = FastDateFormat.getInstance(layout);
        return instance.format(millis);
    }
}
