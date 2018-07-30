package com.demon.com.materialdesign.widget.loveflower;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * 贝塞尔公式
 */
public class LoveFlowerEvaluator implements TypeEvaluator<PointF> {

    private PointF p1, p2;

    public LoveFlowerEvaluator(PointF p1, PointF p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public PointF evaluate(float t, PointF p0, PointF p3) {
        PointF pointF = new PointF();
        pointF.x = p0.x * (1 - t) * (1 - t) * (1 - t)
                + 3 * p1.x * t * (1 - t) * (1 - t)
                + 3 * p2.x * t * t * (1 - t)
                + p3.x * t * t * t;
        pointF.y = p0.y * (1 - t) * (1 - t) * (1 - t)
                + 3 * p1.y * t * (1 - t) * (1 - t)
                + 3 * p2.y * t * t * (1 - t)
                + p3.y * t * t * t;

        return pointF;
    }
}
