package com.rytuo.drawing.figures;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CircleTest {

    @Test
    void testCircle() {
        Point center = new Point(7131723, 7672364);
        int radius = 647454;
        Circle circle = new Circle(center, radius);
        assertThat(circle.center()).isEqualTo(center);
        assertThat(circle.radius()).isEqualTo(radius);
    }
}
