package cn.maiaimei.framework.swift.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Cash Management & Customer Status
 */
@Configuration
@ConditionalOnExpression("#{'true'.equals(environment['swift.mt.validation.enabled']) or 'true'.equals(environment['swift.mt.validation.enabled-mt9xx'])}")
@ImportResource("classpath*:validation/mt9xx/*.xml")
public class MT9xxValidationConfig {
}
