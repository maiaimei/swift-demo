package cn.maiaimei.example;

import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MTValidationTest {
    @Test
    void test() {
        MT798 mt798 = new MT798();
        mt798.addField(new Field20("D345566"));
        mt798.addField(new Field12("784"));
        mt798.addField(new Field77E());
        mt798.addField(new Field27A("1/2"));
        //mt798.addField(new Field29F("+BILBO BAGGINS\r+PH 43 1 45688981"));
        //mt798.addField(new Field29F("+BILBO BAGGINS\n+PH 43 1 45688981"));
        mt798.addField(new Field29F("+BILBO BAGGINS\r\n+PH 43 1 45688981"));
        //System.out.println(mt798.message());
        SwiftTagListBlock block = mt798.getSubMessage().getBlock4().getSubBlockBeforeFirst("77E", false);
        List<Tag> tags = block.getTags();
        for (Tag tag : tags) {
            log.info("{}:{}", tag.getName(), tag.getValue());
        }
    }

    @Test
    void testExtractOptionalParts() {
        List<String> formats = Arrays.asList(
                "6!n[/6!n]",
                "4!a2!a2!c[3!c]",
                "4!a[/35x]",
                "4!c[/35x]",
                "[/1!a]",
                "[/34x]",
                "[6*35x]"
        );

        String regex = "\\[/?[1-9]{1}[0-9]*!?[acnxzd]]";
        Pattern pattern = Pattern.compile(regex);
        for (String format : formats) {
            Matcher matcher = pattern.matcher(format);
            while (matcher.find()) {
                log.info("{} -> {}", format, matcher.group(0));
            }
        }
    }
}
