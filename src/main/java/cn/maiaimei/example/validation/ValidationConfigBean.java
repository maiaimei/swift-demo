package cn.maiaimei.example.validation;

import lombok.Data;

import java.util.List;

@Data
public class ValidationConfigBean {
    private String subMessageType;
    private List<ValidationConfigItem> configItems;
}
