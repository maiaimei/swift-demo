package cn.maiaimei.framework.swift;

import cn.maiaimei.framework.swift.config.SwiftMTConfig;
import cn.maiaimei.framework.swift.validation.ValidationEngine;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest(classes = SwiftMTConfig.class)
public class ValidationTest {
    @Autowired
    ValidationEngine validationEngine;

    @Test
    void testValidateMT798() {
        MT798 mt798 = readFileAsMT798("MT798_760.txt");
        ValidationResult result = validationEngine.validate(mt798);
        if (!CollectionUtils.isEmpty(result.getErrorMessages())) {
            System.out.println("Validate error");
            for (String errorMessage : result.getErrorMessages()) {
                System.out.println(errorMessage);
            }
        } else {
            System.out.println("Validate success");
        }
    }

    @Test
    void testReflections() throws InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections("com.prowidesoftware.swift.model.field");
        Set<Class<? extends Field>> subFields = reflections.getSubTypesOf(Field.class);
        Set<Class<? extends Field>> subFields1 = subFields.stream()
                .filter(w -> w.getSimpleName().startsWith("Field")).collect(Collectors.toSet());
        Comparator<Class<? extends Field>> comparing = Comparator.comparing(field -> {
            String name = field.getSimpleName();
            return name.substring(5);
        }, Comparator.naturalOrder());
        LinkedHashSet<Class<? extends Field>> sortedSubFields1 = subFields1.stream()
                .filter(w -> String.valueOf(ValidatorUtils.getNumber(w.getSimpleName())).length() == 2)
                .sorted(comparing)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        LinkedHashSet<Class<? extends Field>> sortedSubFields2 = subFields1.stream()
                .filter(w -> String.valueOf(ValidatorUtils.getNumber(w.getSimpleName())).length() == 3)
                .sorted(comparing)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        LinkedHashSet<Class<? extends Field>> sortedSubFields = new LinkedHashSet<>();
        sortedSubFields.addAll(sortedSubFields1);
        sortedSubFields.addAll(sortedSubFields2);
        for (Class<? extends Field> fieldClass : sortedSubFields) {
            Field field = fieldClass.newInstance();
            String name = field.getName();
            String validatorPattern = field.validatorPattern();
            System.out.println(String.format("public static final String FIELD_%s = \"%s\";", name, name));
            System.out.println(String.format("public static final String FIELD_%s_FORMAT = \"%s\";", name, validatorPattern));
        }
        System.out.println();
    }

    @SneakyThrows
    MT798 readFileAsMT798(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream = resource.getInputStream();
        String content = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
        return MT798.parse(content);
    }
}
