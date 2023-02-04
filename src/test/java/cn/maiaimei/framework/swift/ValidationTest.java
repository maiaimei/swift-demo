package cn.maiaimei.framework.swift;

import cn.maiaimei.framework.swift.config.SwiftMTConfig;
import cn.maiaimei.framework.swift.validation.ValidationEngine;
import cn.maiaimei.framework.swift.validation.ValidationResult;
import cn.maiaimei.framework.swift.validation.engine.GenericValidationEngine;
import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest(classes = SwiftMTConfig.class)
public class ValidationTest extends BaseTest {
    @Autowired
    ValidationEngine validationEngine;

    @Autowired
    GenericValidationEngine genericValidationEngine;

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

    @SneakyThrows
    @Test
    void testSwiftMessageParser() {
        String message = readFileAsString("MT798_760.txt");
        SwiftParser parser = new SwiftParser(message);
        SwiftMessage swiftMessage = parser.message();
        String type = swiftMessage.getType(); // Message Type?
        SwiftBlock4 block4 = swiftMessage.getBlock4();
        List<Tag> allTags = block4.getTags();
        Map<String, List<SwiftTagListBlock>> sequenceBlockMap = genericValidationEngine.getSequenceBlockMap("760", block4);
        System.out.println();
    }
}
