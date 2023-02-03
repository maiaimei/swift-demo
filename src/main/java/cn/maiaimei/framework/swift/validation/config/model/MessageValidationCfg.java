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
public class MessageValidationCfg {
    private String subMessageType;
    private List<FieldInfo> fields;
    private List<SequenceInfo> sequences;
    private List<String> rules;
}
