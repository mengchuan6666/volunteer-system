package com.tiger.common.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @programName: com.tiger.common.config
 * @ClassName:ValidConfig
 * @Description TODO
 * @Author: wwding
 * @Create: 2024/3/22 8:38
 **/
@Configuration
public class ValidConfig {

    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败模式
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
