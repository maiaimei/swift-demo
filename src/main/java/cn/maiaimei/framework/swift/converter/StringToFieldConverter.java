package cn.maiaimei.framework.swift.converter;

import com.prowidesoftware.swift.model.field.Field;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

@Component
public class StringToFieldConverter {
    @SneakyThrows
    public <T extends Field> T convert(String value, Class<T> clazz) {
        Constructor<T> constructor = clazz.getConstructor(String.class);
        return constructor.newInstance(value);
    }
}
