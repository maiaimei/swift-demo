package cn.maiaimei.framework.swift.model;

import java.util.ArrayList;
import java.util.List;

public class MultilineCharacter {

    public static class Builder {

        private final List<String> lines;

        public Builder() {
            this.lines = new ArrayList<>();
        }

        public Builder append(String value) {
            this.lines.add(value);
            return this;
        }

        public String build() {
            return String.join("\r\n", this.lines);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
