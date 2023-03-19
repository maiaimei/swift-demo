package cn.maiaimei.framework.swift.validation;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationResult {
    private List<String> errorMessages;

    public void addErrorMessage(String errorMessage) {
        if (StringUtils.isBlank(errorMessage)) {
            return;
        }
        this.errorMessages.add(errorMessage);
    }

    public static ValidationResult newInstance() {
        ValidationResult result = new ValidationResult();
        result.setErrorMessages(new ArrayList<>());
        return result;
    }
}
