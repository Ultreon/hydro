package com.ultreon.hydro.test.util;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class FileUtilsTest {
    @Test
    void extension1() {
        assert Objects.equals(getExtension("test.png"), ".png");
    }
    @Test
    void extension2() {
        assert Objects.equals(getExtension("file-without-ext"), null);
    }

    @Nullable
    private String getExtension(String name) {
        String[] split = name.split("\\.", 2);
        if (split.length <= 1) {
            return null;
        }

        return "." + split[1];
    }
}
