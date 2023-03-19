package com.example.shotgurnquiz.Camerax;

import android.content.Context;
import android.util.Log;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.camera.lifecycle.ProcessCameraProvider;

import com.example.shotgurnquiz.Face_detection.FaceContourDetectionProcessor;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraManager {

    public CameraManager(Context context, PreviewView finderView, LifecycleOwner lifecycleOwner, GraphicOverlay graphicOverlay){

        this.context = context;
        this.finderView = finderView;
        this.lifecycleOwner = lifecycleOwner;
        this.graphicOverlay = graphicOverlay;

        // Initialisation d'un thread pour le cameraExecutor
        cameraExecutor =  Executors.newSingleThreadExecutor();
    }

    // Methode permettant le lancement de la camera
    public void startCamera() {
            ListenableFuture cameraProviderFuture = ProcessCameraProvider.getInstance(context);
            // Ajout d'un listener pour récupérer le ProcessCameraProvider lorsque celui-ci est prêt
            cameraProviderFuture.addListener(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                cameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                            } catch (ExecutionException e) {
                                Log.e(TAG, "Get camera provider future execution exception", e);
                            } catch (InterruptedException e) {
                                Log.e(TAG, "Get camera provider future interrupted exception", e);
                            }

                            // Création du preview
                            preview = new Preview.Builder().build();

                            // Création de imageAnalyzer
                            imageAnalyzer = new ImageAnalysis.Builder()
                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                    .build();

                            // Utilisation de FaceContourDetectionProcessor pour l'analyse des images
                            imageAnalyzer.setAnalyzer(cameraExecutor, new FaceContourDetectionProcessor(context, graphicOverlay));

                            // Sélection de la caméra frontale
                            CameraSelector cameraSelector = new CameraSelector.Builder()
                                    .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                                    .build();

                            // Configuration de la caméra
                            setCameraConfig(cameraProvider, cameraSelector);

                        }
                    }, ContextCompat.getMainExecutor(context)
        );
    }


        // Methode permettant la configuration de la caméra
        private void setCameraConfig(ProcessCameraProvider cameraProvider, CameraSelector cameraSelector) {
            try {
                cameraProvider.unbindAll();
                // attache le processus de la camera au cycle de vie de l'activité, pour mettre en pause la caméra quand l'activité est en pause par exemple
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalyzer);
                // affiche la preview sur la surface du finderView du layout
                preview.setSurfaceProvider( finderView.getSurfaceProvider() );
            } catch (Exception e) {
                Log.e(TAG, "Use case binding failed", e);
            }
        }

    // Differentes variables de la class
    private Context context;
    private PreviewView finderView;
    private LifecycleOwner lifecycleOwner;
    private Preview preview;
    private GraphicOverlay graphicOverlay;
    private ExecutorService cameraExecutor;
    private ProcessCameraProvider cameraProvider;
    private ImageAnalysis imageAnalyzer;
    static final String TAG = "CameraManager";
}
