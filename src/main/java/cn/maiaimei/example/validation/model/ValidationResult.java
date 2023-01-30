package cn.maiaimei.example.validation.model;

import lombok.Data;

import java.util.List;

@Data
public class ValidationResult {
    private boolean hasError;
    private List<String> errorMessages;

    public void addErrorMessage(String errorMessage) {
        this.errorMessages.add(errorMessage);
    }
}
