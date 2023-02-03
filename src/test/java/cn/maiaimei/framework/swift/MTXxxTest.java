package cn.maiaimei.framework.swift;

import com.prowidesoftware.swift.internal.SequenceStyle;
import com.prowidesoftware.swift.model.SwiftMessageUtils;
import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.mt.mt1xx.MT101;
import com.prowidesoftware.swift.model.mt.mt4xx.MT400;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class MTXxxTest {
    @Test
    void testMTXxx() {
        MT101 mt101 = new MT101();
        MT400 mt400 = new MT400();
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
    void testGetSequences() {
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

    @SneakyThrows
    MT798 readFileAsMT798(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream = resource.getInputStream();
        String content = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
        return MT798.parse(content);
    }
}
