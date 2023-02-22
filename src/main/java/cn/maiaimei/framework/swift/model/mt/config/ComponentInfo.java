package cn.maiaimei.framework.swift.model.mt.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ComponentInfo extends FieldComponentInfo {
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

    private String label;
}
