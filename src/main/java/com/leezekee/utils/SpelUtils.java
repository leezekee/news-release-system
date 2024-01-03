package com.leezekee.utils;

import io.micrometer.common.util.StringUtils;
import org.springframework.context.expression.BeanFactoryAccessor;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

public class SpelUtils {
    public static String parseSpel( String spel, Map<String, Object> map) {
        if (StringUtils.isBlank(spel)) {
            return "";
        } else {
            ExpressionParser parser = new SpelExpressionParser();
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setVariables(map);
            context.addPropertyAccessor(new MapAccessor());
            context.addPropertyAccessor(new BeanFactoryAccessor());
            return parser.parseExpression(spel, new TemplateParserContext()).getValue(context, String.class);
        }
    }
}
