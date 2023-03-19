package com.example.shotgurnquiz.Face_detection;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.shotgurnquiz.Camerax.GraphicOverlay;
import com.google.mlkit.vision.face.Face;

// Class permettant de dessiner le contour du visage dans le GraphicOverlay
public class FaceContourGraphic extends GraphicOverlay.Graphic{
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

    // Dessine le contour sur la canvas
    @Override
    public void draw(Canvas canvas) {
        RectF rect = calculateRect((float)imageRect.height(), (float)imageRect.width(), face.getBoundingBox());
        canvas.drawRect(rect, boxPaint);
    }

    // Differentes variables de la class
    private Face face;
    private Rect imageRect;
    private Paint facePositionPaint;
    private Paint idPaint;
    private Paint boxPaint;
    static final float BOX_STROKE_WIDTH = 5.0f;
}
