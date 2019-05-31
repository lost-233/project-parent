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

import java.util.List;

import static com.zhaoming.web.thymeleaf.ThymeleafFacade.evaluateAsStringsWithDelimiter;
import static com.zhaoming.web.thymeleaf.ThymeleafFacade.getRawValue;

/**
 * StringProcessor
 * @author think
 * @date 2017/7/5
 */
public class StringProcessor extends AbstractAttributeTagProcessor {

    private static final String DELIMITER = ",";

    private static final String ATTR_NAME = "string";
    private static final int PRECEDENCE = 100;

    private static final String LongTextFormat = "longTextFormat";
    private static final String CARD = "card";
    private static final String MOBILE = "mobile";
    private static final String IDENTITY = "identity";
    private static final String CSN = "csn";

    public StringProcessor(final String dialectPrefix) {
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

        final String rawValue = getRawValue(tag, attributeName);
        String method = null;
        String exper = null;
        if (StringUtils.isNotEmpty(rawValue)) {
            method = rawValue.split(CommonConstant.COLON)[0];
            exper = rawValue.split(CommonConstant.COLON)[1];
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
            if (CARD.equals(method)) {
                model.add(modelFactory.createText(HtmlEscape.escapeHtml5(getCardNo(value))));
            } else if (MOBILE.equals(method)) {
                model.add(modelFactory.createText(HtmlEscape.escapeHtml5(getMobile(value))));
            } else if (IDENTITY.equals(method)) {
                model.add(modelFactory.createText(HtmlEscape.escapeHtml5(getIdentity(value))));
            } else if (CSN.equals(method)) {
                model.add(modelFactory.createText(HtmlEscape.escapeHtml5(getCsn(value))));
            } else if (LongTextFormat.equals(method)) {
                int precLen = StringUtils.isEmpty(tag.getAttributeValue("prec")) ? 10 : Integer.parseInt(tag.getAttributeValue("prec"));
                model.add(modelFactory.createText(HtmlEscape.escapeHtml5(getLongTextFormat(value, precLen))));
            }
        }
        //添加模型 标签
        model.add(modelFactory.createCloseElementTag(elementCompleteName));
        //替换页面标签
        structureHandler.replaceWith(model, false);
    }

    private String getLongTextFormat(String value, int i) {
        if (StringUtils.isEmpty(value) || value.length() < i) {
            return value;
        } else {
            return value.substring(0, i) + "...";
        }
    }

    protected String getCardNo(String cardNo) {
        if (StringUtils.isNotBlank(cardNo) && cardNo.length() >= 9) {
            return cardNo.substring(0, 4) + cardNo.substring(4, cardNo.length() - 3).replaceAll("[0-9]", "*") + cardNo.substring(cardNo.length() - 4, cardNo.length());
        }
        return cardNo;
    }

    protected static String getIdentity(String val) {
        if (StringUtils.isEmpty(val) || val.length() < 9) {
            return val;
        } else {
            return val.substring(0, 4) + val.substring(4, val.length() - 4).replaceAll("[0-9]", "*") + val.substring(val.length() - 4, val.length());
        }
    }

    /**
     * 前四后四显示
     *
     * @param val
     * @return
     */
    protected static String getMobile(String val) {
        if (StringUtils.isEmpty(val) || val.length() < 9) {
            return val;
        } else {
            return val.substring(0, 3) + val.substring(4, val.length() - 3).replaceAll("[0-9]", "*") + val.substring(val.length() - 4, val.length());
        }
    }

    /**
     * 星星显示
     *
     * @param val
     * @return
     */
    protected String getCsn(String val) {
        if (StringUtils.isEmpty(val) || val.length() < 12) {
            return val;
        } else {
            return val.substring(0, 2) + val.substring(2, val.length() - 3).replaceAll("[0-9a-zA-Z]", "*") + val.substring(val.length() - 6, val.length());
        }
    }

}