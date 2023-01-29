package cn.maiaimei.example;

import cn.maiaimei.example.validation.ValidationResult;
import cn.maiaimei.example.validation.ValidationUtils;
import cn.maiaimei.example.validation.ValidatorUtils;
import com.google.gson.Gson;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@SpringBootTest
public class ValidationTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ValidationUtils validationUtils;

    @Test
    void testValidateMT784() {
        MT798 mt798 = readFileAsMT798("MT798_784.txt");
        ValidationResult result = validationUtils.validate(mt798);
        if (result.isHasError()) {
            System.out.println("Validate error");
            for (String errorMessage : result.getErrorMessages()) {
                System.out.println(errorMessage);
            }
        } else {
            System.out.println("Validate success");
        }
    }

    @Test
    void testInitValidationConfig() {
        Object mt784ValidationConfig = applicationContext.getBean("mt784ValidationConfig");
        Gson gson = new Gson();
        System.out.println(gson.toJson(mt784ValidationConfig));
    }

    @Test
    void testPattern() {
        System.out.println(Pattern.matches(ValidatorUtils.COMPOSITE_FORMAT_A, "8!n4!n"));
        System.out.println(Pattern.matches(ValidatorUtils.COMPOSITE_FORMAT_A, "1!n/1!n"));
        System.out.println(Pattern.matches(ValidatorUtils.COMPOSITE_FORMAT_A, "6!n[/6!n]"));
        System.out.println(Pattern.matches(ValidatorUtils.COMPOSITE_FORMAT_A, "3!a15d"));
        System.out.println(Pattern.matches(ValidatorUtils.COMPOSITE_FORMAT_A, "4!a2!a2!c[3!c]"));
        System.out.println(Pattern.matches(ValidatorUtils.COMPOSITE_FORMAT_B, "4!c/35x"));
        System.out.println(Pattern.matches(ValidatorUtils.COMPOSITE_FORMAT_B, "4!c[/35x]"));
    }

    @Test
    void testSplitFormat() {
        //String format = "1!n/1!n";
        //String format = "8!n4!n";
        //String format = "4!c[/35x]";
        String format = "4!a2!a2!c[3!c]";
        List<String> formats = ValidatorUtils.splitFormat(format);
        for (String s : formats) {
            System.out.println(s);
        }
    }

    @Test
    void testGenerateMT784() {
        MT798 mt798 = generateMT784();
        SwiftTagListBlock block = mt798.getSwiftMessage().getBlock4().getSubBlockAfterFirst("77E", false);
        List<Tag> tags = block.getTags();
        for (Tag tag : tags) {
            log.info("{}:{}", tag.getName(), tag.getValue());
        }
    }

    MT798 generateMT784() {
        MT798 mt798 = new MT798();
        mt798.addField(new Field20("FGH96372"));
        mt798.addField(new Field12("784"));
        mt798.addField(new Field77E());
        mt798.addField(new Field27A("1/2"));
        //mt798.addField(new Field29F("+BILBO BAGGINS\r+PH 43 1 45688981"));
        //mt798.addField(new Field29F("+BILBO BAGGINS\n+PH 43 1 45688981"));
        mt798.addField(new Field29F("+BILBO BAGGINS\r\n+PH 43 1 45688981"));
        System.out.println();
        System.out.println(mt798.message());
        System.out.println();
        return mt798;
    }

    @SneakyThrows
    MT798 readFileAsMT798(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream = resource.getInputStream();
        String content = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
        return MT798.parse(content);
    }
}
