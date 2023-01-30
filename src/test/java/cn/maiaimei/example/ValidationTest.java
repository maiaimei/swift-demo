package cn.maiaimei.example;

import cn.maiaimei.example.validation.ValidationProcessor;
import cn.maiaimei.example.validation.model.ValidationResult;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
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
import java.util.Set;

@Slf4j
@SpringBootTest
public class ValidationTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ValidationProcessor validationProcessor;

    @Test
    void testValidateMT798() {
        MT798 mt798 = readFileAsMT798("MT798.txt");
        ValidationResult result = validationProcessor.validate(mt798);
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
    void testParseMT798() {
//        MT798 mt798 = generateMT798();
        MT798 mt798 = readFileAsMT798("MT798.txt");
        SwiftTagListBlock block = mt798.getSwiftMessage().getBlock4().getSubBlockAfterFirst("77E", false);

//        List<Tag> tags = block.getTags();
//        for (Tag tag : tags) {
//            log.info("{}:{}", tag.getName(), tag.getValue());
//        }

//        Field52A field52A = (Field52A) block.getFieldByName("52A");
//        log.info("Field52A validatorPattern:{}, componentsSize: {}", field52A.validatorPattern(), field52A.componentsSize());
//        log.info("{} {}", field52A.getDCMark(), field52A.getComponent1());
//        log.info("{} {}", field52A.getAccount(), field52A.getComponent2());
//        log.info("{} {}", field52A.getComponent3AsBIC(), field52A.getComponent3());

//        Field59 field59 = (Field59) block.getFieldByName("59");
//        log.info("Field59 validatorPattern:{}, componentsSize: {}", field59.validatorPattern(), field59.componentsSize());
//        log.info("{} {}", field59.getAccount(), field59.getComponent1());
//        log.info("{} {}", field59.getNameAndAddressLine1(), field59.getComponent2());
//        log.info("{} {}", field59.getNameAndAddressLine2(), field59.getComponent3());
//        log.info("{} {}", field59.getNameAndAddressLine3(), field59.getComponent4());
//        log.info("{} {}", field59.getNameAndAddressLine4(), field59.getComponent5());

        // List<String> lines = SwiftParseUtils.getLines(value);

        Set<String> fieldNames = block.getTagMap().keySet();
        for (String name : fieldNames) {
            Field field = block.getFieldByName(name);
            log.info("Field{} validatorPattern:{}, componentsSize: {}", name, field.validatorPattern(), field.componentsSize());
            log.info("field value is: {}", field.getValue());
            List<String> componentLabels = field.getComponentLabels();
            for (int i = 0; i < field.componentsSize(); i++) {
                log.info("{} value is: {}", componentLabels.get(i), field.getComponent(i + 1));
            }
        }
    }

    MT798 generateMT798() {
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
