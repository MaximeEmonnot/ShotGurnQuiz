package com.example.shotgurnquiz.camerax;

import android.content.Context;
import android.util.Log;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.camera.lifecycle.ProcessCameraProvider;

import com.example.shotgurnquiz.face_detection.FaceContourDetectionProcessor;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraManager {

    private Context context;
    private PreviewView finderView;
    private LifecycleOwner lifecycleOwner;
    private Preview preview;
    private GraphicOverlay graphicOverlay;
    private ExecutorService cameraExecutor;
    private ProcessCameraProvider cameraProvider;
    private ImageAnalysis imageAnalyzer;
    static final String TAG = "CameraManager";

    public CameraManager(Context context, PreviewView finderView, LifecycleOwner lifecycleOwner, GraphicOverlay graphicOverlay){

        this.context = context;
        this.finderView = finderView;
        this.lifecycleOwner = lifecycleOwner;
        this.graphicOverlay = graphicOverlay;

        cameraExecutor =  Executors.newSingleThreadExecutor();
    }

    public void startCamera() {
            ListenableFuture cameraProviderFuture = ProcessCameraProvider.getInstance(context);
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

                            preview = new Preview.Builder().build();

                            imageAnalyzer = new ImageAnalysis.Builder()
                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                    .build();

                            imageAnalyzer.setAnalyzer(cameraExecutor, new FaceContourDetectionProcessor(context, graphicOverlay));

                            CameraSelector cameraSelector = new CameraSelector.Builder()
                                    .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                                    .build();

                            setCameraConfig(cameraProvider, cameraSelector);

                        }
                    }, ContextCompat.getMainExecutor(context)
        );
    }

        private void setCameraConfig(ProcessCameraProvider cameraProvider, CameraSelector cameraSelector) {
            try {
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalyzer);
                preview.setSurfaceProvider( finderView.getSurfaceProvider() );
            } catch (Exception e) {
                Log.e(TAG, "Use case binding failed", e);
            }
        }

}
