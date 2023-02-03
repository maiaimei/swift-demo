package cn.maiaimei.framework.swift.validation.config;

import cn.maiaimei.framework.swift.validation.config.model.MessageValidationCfg;
import com.google.gson.Gson;
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
    public MessageValidationCfg mt784MessageValidationCfg() {
        return initMessageValidationCfg("validation/mt7xx/MT784.json");
    }

    @Bean
    public MessageValidationCfg mt760MessageValidationCfg() {
        return initMessageValidationCfg("validation/mt7xx/MT760.json");
    }

    @SneakyThrows
    private MessageValidationCfg initMessageValidationCfg(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream = resource.getInputStream();
        String json = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
        return GSON.fromJson(json, MessageValidationCfg.class);
    }
}
