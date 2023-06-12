package com.rytuo.drawing.awt;

import com.rytuo.drawing.ADrawingApiTest;
import com.rytuo.drawing.DrawingApi;

public class AwtDrawingApiTest extends ADrawingApiTest {
    @Override
    protected DrawingApi initApi() {
        return new AwtDrawingApi();
    }
}
