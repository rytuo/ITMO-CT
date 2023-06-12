package com.rytuo.drawing.figures;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LineTest {

    @Test
    void testLine() {
        Point from = new Point(8438742, 5775);
        Point to = new Point(823823, 8438782);
        Line line = new Line(from, to);
        assertThat(line.from()).isEqualTo(from);
        assertThat(line.to()).isEqualTo(to);
    }
}
