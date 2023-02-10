package cn.maiaimei.framework.swift.util;

import cn.maiaimei.framework.swift.validation.constants.SwiftMtClassNameUtils;
import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwiftUtils {

    private static final String GET_SEQUENCE_METHOD_NAME = "getSequence";

    @SneakyThrows
    public static SwiftMessage parseToSwiftMessage(String message) {
        SwiftParser parser = new SwiftParser(message);
        return parser.message();
    }

    public static AbstractMT parseToAbstractMT(String message, String messageType) {
        SwiftMessage swiftMessage = parseToSwiftMessage(message);
        return new AbstractMT(swiftMessage) {
            @Override
            public String getMessageType() {
                return messageType;
            }
        };
    }

    public static AbstractMT parseToAbstractMT(SwiftMessage swiftMessage, String messageType) {
        return new AbstractMT(swiftMessage) {
            @Override
            public String getMessageType() {
                return messageType;
            }
        };
    }

    public static SwiftBlock4 getBlock4(String message) {
        SwiftMessage swiftMessage = parseToSwiftMessage(message);
        return swiftMessage.getBlock4();
    }

    @SneakyThrows
    public static Map<String, List<SwiftTagListBlock>> getSequenceMap(String messageType, SwiftTagListBlock block4) {
        Map<String, List<SwiftTagListBlock>> sequenceMap = new HashMap<>();
        String className = SwiftMtClassNameUtils.MAP.get(messageType);
        if (StringUtils.isBlank(className)) {
            throw new ClassNotFoundException("Can't found class for MT" + messageType);
        }
        Class<?> clazz = Class.forName(className);
        Object instance = clazz.newInstance();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            String declaredMethodName = declaredMethod.getName();
            if (!declaredMethodName.startsWith(GET_SEQUENCE_METHOD_NAME)) {
                continue;
            }
            Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
            if (parameterTypes.length == 1
                    && SwiftTagListBlock.class.getSimpleName().equals(parameterTypes[0].getSimpleName())) {
                Object result = declaredMethod.invoke(instance, block4);
                List<SwiftTagListBlock> blockList;
                if (result instanceof List) {
                    blockList = (List<SwiftTagListBlock>) result;
                } else {
                    blockList = Collections.singletonList((SwiftTagListBlock) result);
                }
                if (CollectionUtils.isEmpty(blockList)) {
                    blockList = Collections.emptyList();
                }
                sequenceMap.put(declaredMethodName.replaceAll(GET_SEQUENCE_METHOD_NAME, StringUtils.EMPTY), blockList);
            }
        }
        return sequenceMap;
    }

    private SwiftUtils() {
        throw new UnsupportedOperationException();
    }
}
