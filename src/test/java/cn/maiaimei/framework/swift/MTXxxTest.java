package cn.maiaimei.framework.swift;

import cn.maiaimei.framework.swift.validation.ValidatorUtils;
import com.prowidesoftware.swift.internal.SequenceStyle;
import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.*;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.util.*;
import java.util.stream.Collectors;

public class MTXxxTest extends BaseTest {

    @SneakyThrows
    @Test
    void testMT940() {
        String mt940 = "{1:F01AAAABB99BSMK3513951576}" +
                "{2:O9400934081223BBBBAA33XXXX03592332770812230834N}" +
                "{4:\n" +
                ":20:0112230000000890\n" +
                ":25:SAKG800030155USD\n" +
                ":28C:255/1\n" +
                ":60F:C011223USD175768,92\n" +
                ":61:0112201223CD110,92NDIVNONREF//08 IL053309\n" +
                "/GB/2542049/SHS/312,\n" +
                ":62F:C011021USD175879,84\n" +
                ":20:NONREF\n" +
                ":25:4001400010\n" +
                ":28C:58/1\n" +
                ":60F:C140327EUR6308,75\n" +
                ":61:1403270327C3519,76NTRF50RS201403240008//2014032100037666\n" +
                "ABC DO BRASIL LTDA\n" +
                ":86:INVOICE NR. 6000012801 \n" +
                "ORDPRTY : ABC DO BRASIL LTDA RUA LIBERO BADARO,293-SAO \n" +
                "PAULO BRAZIL }";
        // 方法一
        SwiftParser parser = new SwiftParser(mt940);
        SwiftMessage swiftMessage = parser.message();
        System.out.println("getSender:" + swiftMessage.getSender());
        System.out.println("getReceiver:" + swiftMessage.getReceiver());
        System.out.println("getType:" + swiftMessage.getType());
        System.out.println("getMtId:" + swiftMessage.getMtId());
        System.out.println("getSignature:" + swiftMessage.getSignature());
        System.out.println("*******************************************");
        SwiftBlock1 swiftBlock1 = swiftMessage.getBlock1();
        System.out.println("getApplicationId:" + swiftBlock1.getApplicationId());
        System.out.println("getServiceId:" + swiftBlock1.getServiceId());
        System.out.println("getLogicalTerminal:" + swiftBlock1.getLogicalTerminal());
        System.out.println("getSequenceNumber:" + swiftBlock1.getSequenceNumber());
        System.out.println("*******************************************");
        SwiftBlock2 swiftBlock2 = swiftMessage.getBlock2();
        System.out.println("getBlockValue:" + swiftBlock2.getBlockValue());
        System.out.println("*******************************************");
        SwiftBlock4 swiftBlock4 = swiftMessage.getBlock4();
        Field[] fields = swiftBlock4.getFieldsByName(Field61.NAME);
        for (Field field : fields) {
            System.out.println("getTagValue:" + field.getValue());
            System.out.println("AMOUNT:" + field.getComponent(5));
        }
        System.out.println("*******************************************");
        // 方法二
        MT940 mt = MT940.parse(mt940);
        for (Field61 tx : mt.getField61()) {
            System.out.println("Amount: " + tx.getComponent(Field61.AMOUNT));
            System.out.println("Transaction Type: " + tx.getComponent(Field61.TRANSACTION_TYPE));
            System.out.println("Reference Acc Owner: " + tx.getComponent(Field61.REFERENCE_FOR_THE_ACCOUNT_OWNER));
        }
    }

    /**
     * Base class for SWIFT blocks {@link SwiftTagListBlock}
     * Mark detecting sequences strategy used. Internal use {@link SequenceStyle}
     * Type.GENERATED_16RS
     * Type.GENERATED_FIXED_WITH_OPTIONAL_TAIL
     * Type.GENERATED_SLICE
     * Type.SPLIT_BY_15
     */
    @Test
    void testSequences() {
        MT798 mt798 = readFileAsMT798("MT798_760.txt");
        SwiftTagListBlock block = mt798.getSubMessage().getBlock4();
        Map<String, SwiftTagListBlock> map = SwiftMessageUtils.splitByField15(block);
        for (Map.Entry<String, SwiftTagListBlock> entry : map.entrySet()) {
            System.out.println("Sequence " + entry.getKey());
            for (Tag tag : entry.getValue().getTags()) {
                System.out.println(":" + tag.getName() + ":" + tag.getValue());
            }
        }
    }

    @Test
    void testGetMTXxxClassNameByReflections() {
        Reflections reflections = new Reflections("com.prowidesoftware.swift.model.mt");
        Set<Class<? extends AbstractMT>> classNames = reflections.getSubTypesOf(AbstractMT.class);
        Comparator<Class<? extends AbstractMT>> comparing = Comparator.comparing(mt -> {
            String name = mt.getSimpleName();
            return name.substring(2);
        }, Comparator.naturalOrder());
        LinkedHashSet<Class<? extends AbstractMT>> sortedClassNames = classNames.stream()
                .sorted(comparing)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        for (Class<? extends AbstractMT> className : sortedClassNames) {
            String subMessageType = className.getSimpleName().replaceAll("MT", "");
            System.out.println("CLASS_NAME_MAP.put(\"" + subMessageType + "\", \"" + className.getName() + "\");");
        }
    }

    @Test
    void testGetFieldsByReflections() throws InstantiationException, IllegalAccessException {
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
        List<String> validatorPatterns = new ArrayList<>();
        for (Class<? extends Field> fieldClass : sortedSubFields) {
            Field field = fieldClass.newInstance();
            String name = field.getName();
            String validatorPattern = field.validatorPattern();
            boolean b1 = ValidatorUtils.isMatchFixedLengthCharacter(validatorPattern);
            boolean b2 = ValidatorUtils.isMatchFixedLengthCharacterStartsWithSlash(validatorPattern);
            boolean b3 = ValidatorUtils.isMatchVariableLengthCharacter(validatorPattern);
            boolean b4 = ValidatorUtils.isMatchVariableLengthCharacterStartsWithSlash(validatorPattern);
            boolean b5 = ValidatorUtils.isMatchMultilineSwiftSet(validatorPattern);
            boolean b = b1 || b2 || b3 || b4 || b5;
            if (!b && !validatorPatterns.contains(validatorPattern)) {
                validatorPatterns.add(validatorPattern);
                System.out.println(name + " " + validatorPattern);
            }
        }
        System.out.println();
    }
}
