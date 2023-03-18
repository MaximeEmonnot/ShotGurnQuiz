package com.example.shotgurnquiz.Camerax;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GraphicOverlay extends View {
    private Object lock;
    private List<Graphic> graphics;
    public Float mScale;
    public Float mOffsetX;
    public Float mOffsetY;

    public GraphicOverlay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        lock = new Object();
        graphics = new ArrayList<Graphic>();
    }

    public void clear() {
        synchronized(lock) { graphics.clear(); }
        postInvalidate();
    }

    public void add(Graphic graphic) {
        synchronized(lock) { graphics.add(graphic); }
    }

    public void remove(Graphic graphic) {
        synchronized(lock) { graphics.remove(graphic); }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized(lock) {
            for (Graphic graphic : graphics) {
                graphic.draw(canvas);
            }
        }
    }

    public abstract static class Graphic{

        private GraphicOverlay overlay;

        public Graphic(GraphicOverlay overlay){
            this.overlay = overlay;
        }

        public abstract void draw(Canvas canvas);

        // for land scape
        private boolean isLandScapeMode(){
            return overlay.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        }

        private float whenLandScapeModeWidth(float width, float height){
            return isLandScapeMode() ? width : height;
        }

        private float whenLandScapeModeHeight(float width, float height){
            return isLandScapeMode() ? height : width;
        }

        public RectF calculateRect(Float height, Float width, Rect boundingBoxT){

            float scaleX = (float)overlay.getWidth() / whenLandScapeModeWidth(width, height);
            float scaleY = (float)overlay.getHeight() / whenLandScapeModeHeight(width, height);
            float scale = Math.max(scaleX, scaleY);
            overlay.mScale = scale;

            // Calculate offset (we need to center the overlay on the target)
            float offsetX = ((float)overlay.getWidth() - (float)Math.ceil(whenLandScapeModeWidth(width, height) * scale)) / 2.0f;
            float offsetY = ((float)overlay.getHeight() - (float)Math.ceil(whenLandScapeModeHeight(width, height) * scale)) / 2.0f;

            overlay.mOffsetX = offsetX;
            overlay.mOffsetY = offsetY;

            float left = boundingBoxT.right * scale + offsetX;
            float top = boundingBoxT.top * scale + offsetY;
            float right = boundingBoxT.left * scale + offsetX;
            float bottom = boundingBoxT.bottom * scale + offsetY;

            RectF mappedBox = new RectF(left, top, right, bottom);

            // for front mode
            final float centerX = (float)overlay.getWidth() / 2;
            mappedBox.left = centerX + (centerX - mappedBox.left);
            mappedBox.right = centerX - (mappedBox.right - centerX);

            return mappedBox;
        }
    }
}
