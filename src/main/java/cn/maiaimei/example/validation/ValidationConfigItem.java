package cn.maiaimei.example.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationConfigItem {
    private String tag;
    private String format;
    private boolean required;
    private List<ValidationConfigRule> rules;
}
