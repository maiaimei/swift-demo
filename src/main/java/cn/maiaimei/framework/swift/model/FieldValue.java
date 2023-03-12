package cn.maiaimei.framework.swift.model;

import com.prowidesoftware.swift.model.field.Field;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

public class FieldValue {

    public static class Builder<T extends Field> {
        private final T field;

        @SneakyThrows
        public Builder(Class<T> clazz) {
            Constructor<T> constructor = clazz.getConstructor();
            this.field = constructor.newInstance();
        }

        public Builder<T> component(int number, String value) {
            field.setComponent(number, value);
            return this;
        }

        public String build() {
            return field.getValue();
        }

        public T field() {
            return field;
        }
    }

    public static <T extends Field> Builder<T> builder(Class<T> clazz) {
        return new Builder<>(clazz);
    }
}
