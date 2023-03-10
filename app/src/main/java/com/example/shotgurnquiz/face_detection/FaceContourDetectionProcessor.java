package com.example.shotgurnquiz.face_detection;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.example.shotgurnquiz.Activities.PlayQuizActivity;
import com.example.shotgurnquiz.camerax.BaseImageAnalyzer;
import com.example.shotgurnquiz.camerax.GraphicOverlay;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.Slider;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;


import java.io.IOException;
import java.util.List;

public class FaceContourDetectionProcessor extends BaseImageAnalyzer<List<Face>> {

    private Context context;
    private FaceDetectorOptions realTimeOpts;
    private FaceDetector detector;
    private final static String TAG = "FaceDetectorProcessor";

    public FaceContourDetectionProcessor(Context context, GraphicOverlay view){

        this.context = context;

        graphicOverlay = view;

        realTimeOpts = new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build();

        detector = FaceDetection.getClient(realTimeOpts);
    }


    @Override
    protected Task<List<Face>> detectInImage(InputImage image) {
        return detector.process(image);
    }

    @Override
    public void stop() {
        detector.close();
    }

    @Override
    protected void onSuccess(List<Face> results, GraphicOverlay graphicOverlay, Rect rect) {
        graphicOverlay.clear();

        if(results.size() != 0) {
            Face mostProminentFace = null;

            for (Face face : results) {
                if (mostProminentFace == null || face.getBoundingBox().width() > mostProminentFace.getBoundingBox().width()) {
                    mostProminentFace = face;
                }
            }

            boolean isSmiling = mostProminentFace.getSmilingProbability() > 0.30f;

            int color = isSmiling ? Color.parseColor("#1bde62") : Color.parseColor("#f28500");

            ((PlayQuizActivity)context).answer = isSmiling;

            FaceContourGraphic faceGraphic = new FaceContourGraphic(graphicOverlay, mostProminentFace, rect, color);
            graphicOverlay.add(faceGraphic);
        }

        graphicOverlay.postInvalidate();
    }

    @Override
    protected void onFailure(Exception e) {
        Log.w(TAG, "Face Detector failed" + e);
    }
}
