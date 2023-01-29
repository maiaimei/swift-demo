package cn.maiaimei.example.validation;

import lombok.Data;

import java.util.List;

@Data
public class ValidationConfigRule {
    private Integer key;
    private String type;
    private String datetimeFormat;
    private List<String> enumItems;
}
