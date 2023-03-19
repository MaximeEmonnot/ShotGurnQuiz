package com.example.shotgurnquiz.Camerax;

import android.graphics.Rect;
import android.media.Image;

import androidx.annotation.NonNull;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;

// Classe abstraite definissant un analyzer d'image
public abstract class BaseImageAnalyzer<T> implements ImageAnalysis.Analyzer {


    // Methode permettant l'analyse d'une image
    @ExperimentalGetImage
    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {

        // Récupération de l'image de l'imageProxy
        Image mediaImage = imageProxy.getImage();

        // Utilisation de la methode detectInImage et redirection vers les methodes onSuccess et onFailure en cas de réussite ou d'échec
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

    // Differentes variables de la class
    protected GraphicOverlay graphicOverlay;
}
