package cn.maiaimei.framework.swift.validation.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SequenceInfo {
    private String sequenceName;
    private List<FieldInfo> fields;
    private boolean isMandatory;
}
