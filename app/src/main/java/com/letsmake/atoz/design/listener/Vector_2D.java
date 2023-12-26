package com.letsmake.atoz.design.listener;

import android.graphics.PointF;

public class Vector_2D extends PointF {
    public Vector_2D(float f, float f2) {
        super(f, f2);
    }

    public Vector_2D() {

    }

    private void normalizePoint() {
        float sqrt = (float) Math.sqrt((double) ((this.x * this.x) + (this.y * this.y)));
        this.x /= sqrt;
        this.y /= sqrt;
    }

    public static float getVectorAngle(Vector_2D vector2D, Vector_2D vector2D2) {
        vector2D.normalizePoint();
        vector2D2.normalizePoint();
        return (float) ((Math.atan2((double) vector2D2.y, (double) vector2D2.x) - Math.atan2((double) vector2D.y, (double) vector2D.x)) * 57.29577951308232d);
    }
}
