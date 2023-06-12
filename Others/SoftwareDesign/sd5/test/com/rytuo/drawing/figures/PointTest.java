package com.rytuo.drawing.figures;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {

    @Test
    void testPoint() {
        int x = 174676471;
        int y = 831873817;
        Point point = new Point(x, y);
        assertThat(point.x()).isEqualTo(x);
        assertThat(point.y()).isEqualTo(y);
    }
}
