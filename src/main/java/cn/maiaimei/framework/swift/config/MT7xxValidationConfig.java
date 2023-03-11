package cn.maiaimei.framework.swift.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Documentary Credits & Guarantees
 */
@Configuration
@ConditionalOnExpression("#{'true'.equals('${swift.mt.validation.enabled}') or 'true'.equals('${swift.mt.validation.enabled-mt7xx}')}")
@ImportResource("classpath*:validation/mt7xx/*.xml")
public class MT7xxValidationConfig {
}
