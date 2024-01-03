package com.leezekee.validation;

import com.leezekee.anno.MultiFieldAssociationCheck;
import com.leezekee.utils.SpelUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MultiFieldAssociationCheckValidator implements ConstraintValidator<MultiFieldAssociationCheck, Object> {
    private static final String SPEL_TEMPLATE = "%s%s%s";
    private static final String SPEL_PREFIX = "#{";
    private static final String SPEL_SUFFIX = "}";
    private String when;

    private String must;
    @Override
    public void initialize(MultiFieldAssociationCheck constraintAnnotation) {
        this.when = constraintAnnotation.when();
        this.must = constraintAnnotation.must();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (StringUtils.isBlank(when) || StringUtils.isBlank(must)) {
            return true;
        }
        Map<String, Object> spelMap = getSpelMap(value);
        //when属性是一个spel表达式，执行这个表达式可以得到一个boolean值
        boolean whenCheck = Boolean.parseBoolean(SpelUtils.parseSpel(String.format(SPEL_TEMPLATE, SPEL_PREFIX, when, SPEL_SUFFIX), spelMap));
        if (whenCheck) {
            //判断must是否满足条件
            boolean mustCheck = Boolean.parseBoolean(SpelUtils.parseSpel(String.format(SPEL_TEMPLATE, SPEL_PREFIX, must, SPEL_SUFFIX), spelMap));
            if (!mustCheck) {
                //获取注解中的message属性值
                String message = context.getDefaultConstraintMessageTemplate();
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return false;
            }
        }
        return true;
    }



    @SneakyThrows
    private Map<String,Object> getSpelMap(Object value){
        Field[] declaredFields = value.getClass().getDeclaredFields();
        Map<String,Object> spelMap = new HashMap<>();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            //将对象中的属性名和属性值放入map中
            spelMap.put(declaredField.getName(),declaredField.get(value));
        }
        return spelMap;
    }
}
