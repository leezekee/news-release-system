package com.leezekee.anno;

import com.auth0.jwt.interfaces.Payload;
import com.leezekee.validation.MultiFieldAssociationCheckValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 多属性关联校验注解
 * 用于校验多个属性之间的关联关系
 * 当when条件满足时，必须满足must条件否则校验不通过
 * 注意：如果解析spel表达式错误将抛出异常
 * @author wangzhen
 */
@Documented
@Constraint(validatedBy = { MultiFieldAssociationCheckValidator.class })
@Target({TYPE_USE })
@Retention(RUNTIME)
@Repeatable(MultiFieldAssociationCheck.List.class)
public @interface MultiFieldAssociationCheck {

    /**
     * 错误信息描述，必填
     */
    String message();

    /**
     * 分组校验
     */
    Class<?>[] groups() default { };

    /**
     * 负载
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * 当什么条件下校验,必须是一个spel表达式
     */
    String when();

    /**
     * 必须满足什么条件,必须是一个spel表达式
     */
    String must();
    @Target({TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        MultiFieldAssociationCheck[] value();
    }
}