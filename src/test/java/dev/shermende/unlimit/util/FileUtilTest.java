package dev.shermende.unlimit.util;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileUtilTest {

    @Test
    @SneakyThrows
    void openInputStream() {
        Assertions.assertTrue(FileUtil.openInputStream("resource:source/data.csv").available() > 1);
    }

}