package dev.shermende.unlimit.util;

import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@UtilityClass
public class FileUtil {
    private static final String RESOURCE = "resource:";

    public InputStream openInputStream(
        String name
    ) throws IOException {
        if (!name.startsWith(RESOURCE)) return new FileInputStream(name);
        return Optional.ofNullable(ClassLoader.getSystemResourceAsStream(name.substring(RESOURCE.length())))
            .orElseThrow(() -> new FileNotFoundException(name));
    }

}