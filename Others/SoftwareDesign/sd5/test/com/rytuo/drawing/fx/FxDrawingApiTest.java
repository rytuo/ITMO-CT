package com.rytuo.drawing.fx;

import com.rytuo.drawing.ADrawingApiTest;
import com.rytuo.drawing.DrawingApi;

public class FxDrawingApiTest extends ADrawingApiTest {
    @Override
    protected DrawingApi initApi() {
        return new FxDrawingApi();
    }
}
