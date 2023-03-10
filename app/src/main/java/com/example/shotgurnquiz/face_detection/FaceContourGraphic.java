package com.example.shotgurnquiz.face_detection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.shotgurnquiz.camerax.GraphicOverlay;
import com.google.mlkit.vision.face.Face;

public class FaceContourGraphic extends GraphicOverlay.Graphic{
    private Face face;
    private Rect imageRect;
    private Paint facePositionPaint;
    private Paint idPaint;
    private Paint boxPaint;
    static final float BOX_STROKE_WIDTH = 5.0f;

    public FaceContourGraphic(GraphicOverlay overlay, Face face, Rect imageRect, int selectedColor) {
        super(overlay);
        this.face = face;
        this.imageRect = imageRect;

        facePositionPaint = new Paint();
        facePositionPaint.setColor(selectedColor);

        idPaint = new Paint();
        idPaint.setColor(selectedColor);

        boxPaint = new Paint();
        boxPaint.setColor(selectedColor);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
    }

    @Override
    public void draw(Canvas canvas) {
        RectF rect = calculateRect((float)imageRect.height(), (float)imageRect.width(), face.getBoundingBox());
        canvas.drawRect(rect, boxPaint);
    }
}
