package dev.shermende.unlimit.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class LogUtil {

    public String sanitize(
        Object o
    ) {
        return StringUtils.substring(String.valueOf(o), 0, 128);
    }

}
