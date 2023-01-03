package cn.maiaimei.example.converter;

import cn.maiaimei.example.annotation.SwiftMTTag;
import cn.maiaimei.example.model.MT7xxMessage;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StringToMT7xxMessageConverter implements Converter<String, MT7xxMessage> {
    @Override
    public MT7xxMessage convert(String source) {
        MT7xxMessage mt7xxMessage = new MT7xxMessage();
        MT798 mt798 = new MT798(source);
        SwiftBlock4 block4 = mt798.getSubMessage().getBlock4();
        List<Field> fields = getFields();
        for (Field field : fields) {
            SwiftMTTag swiftMTTag = field.getAnnotation(SwiftMTTag.class);
            if (swiftMTTag == null) {
                continue;
            }
            String tag = swiftMTTag.value();
            int idx = swiftMTTag.index();
            String tagValue = getTagValue(tag, idx, mt798);
            if (tagValue == null) {
                continue;
            }
            tagValue = tagValue.trim().replaceAll("\n", "");
            if (!StringUtils.hasText(tagValue)) {
                continue;
            }
            field.setAccessible(Boolean.TRUE);
            try {
                Object fieldValue = field.get(mt7xxMessage);
                if (fieldValue == null) {
                    field.set(mt7xxMessage, tagValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(Boolean.FALSE);
        }
        return mt7xxMessage;
    }

    private String getTagValue(String tag, int idx, MT798 mt798) {
        if ("20".equals(tag) && idx == 0) {
            // transactionReferenceNumber
            return mt798.getField20().getValue();
        }
        if ("12".equals(tag) && idx == 0) {
            // subMessageType
            return mt798.getField12().getValue();
        }
        SwiftBlock4 block4 = mt798.getSubMessage().getBlock4();
        if (idx == 0) {
            return block4.getTagValue(tag);
        }
        Tag[] tags = block4.getTagsByName(tag);
        if (tags.length - 1 < idx) {
            return null;
        }
        return tags[idx].getValue();
    }
    
    /**
     * get all fields including super class
     *
     * @return field list
     */
    private List<Field> getFields() {
        List<Field> fields = new ArrayList<>();
        Class<?> clazz = MT7xxMessage.class;
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
