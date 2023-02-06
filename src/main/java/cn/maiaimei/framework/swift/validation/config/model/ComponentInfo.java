package cn.maiaimei.framework.swift.validation.config.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Component config
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ComponentInfo extends BaseValidationInfo {
    /**
     * The first component index is 1, the second component index is 2, etc.
     */
    private Integer index;

    /**
     * Multiple components shared the same config, use this config item
     */
    private Integer startIndex;

    /**
     * Multiple components shared the same config, use this config item
     */
    private Integer endIndex;
}
