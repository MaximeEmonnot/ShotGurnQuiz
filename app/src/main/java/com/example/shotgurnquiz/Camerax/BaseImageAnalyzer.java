package com.example.shotgurnquiz.Camerax;

import android.graphics.Rect;
import android.media.Image;

import androidx.annotation.NonNull;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;

public abstract class BaseImageAnalyzer<T> implements ImageAnalysis.Analyzer {
    protected GraphicOverlay graphicOverlay;

    @ExperimentalGetImage
    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {

        Image mediaImage = imageProxy.getImage();

        detectInImage(InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees()))
                .addOnSuccessListener(results -> {
                    onSuccess(results, graphicOverlay, mediaImage.getCropRect());
                    imageProxy.close();})
                .addOnFailureListener(exception -> {onFailure(exception);
                    imageProxy.close();});
    }

    protected abstract Task<T> detectInImage(InputImage image);

    public abstract void stop();

    protected abstract void onSuccess(T results, GraphicOverlay graphicOverlay, Rect rect);

    protected abstract void onFailure(Exception e);
}
