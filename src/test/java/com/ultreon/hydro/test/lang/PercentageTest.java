package com.ultreon.hydro.test.lang;

import com.ultreon.commons.lang.Percentage;
import org.junit.jupiter.api.Test;

public class PercentageTest {
    @Test
    void fullPercent() {
        assert new Percentage(100.).value() == 1.;
    }

    @Test
    void halfPercent() {
        assert new Percentage(50.).value() == .5;
    }

    @Test
    void quarterPercent() {
        assert new Percentage(25.).value() == .25;
    }
}
