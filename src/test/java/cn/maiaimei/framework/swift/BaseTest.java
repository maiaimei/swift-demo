package cn.maiaimei.framework.swift;

import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.InputStreamReader;

public class BaseTest {
    protected MT798 readFileAsMT798(String path) {
        return MT798.parse(readFileAsString(path));
    }

    @SneakyThrows
    protected String readFileAsString(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream = resource.getInputStream();
        return FileCopyUtils.copyToString(new InputStreamReader(inputStream));
    }
}
