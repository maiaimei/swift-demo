package cn.maiaimei.example.validation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.InputStreamReader;

@Configuration
public class ValidationConfig {
    private static final Gson GSON = new Gson();

    @Bean
    public ValidationConfigBean mt784ValidationConfig() {
        return initValidationConfig("validation/MT784ValidationConfig.json");
    }

    @SneakyThrows
    private ValidationConfigBean initValidationConfig(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream = resource.getInputStream();
        String json = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
        return GSON.fromJson(json, new TypeToken<ValidationConfigBean>() {
        });
    }
}
