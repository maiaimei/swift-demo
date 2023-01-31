package cn.maiaimei.framework.swift.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldEnum {
    private String tag;
    private int number;
    private boolean required;
    private List<String> enumItems;
}
