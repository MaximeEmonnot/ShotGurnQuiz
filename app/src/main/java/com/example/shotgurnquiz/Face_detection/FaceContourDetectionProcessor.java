package com.example.shotgurnquiz.Face_detection;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.example.shotgurnquiz.Activities.PlayQuizActivity;
import com.example.shotgurnquiz.Camerax.BaseImageAnalyzer;
import com.example.shotgurnquiz.Camerax.GraphicOverlay;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;


import java.util.List;

public class FaceContourDetectionProcessor extends BaseImageAnalyzer<List<Face>> {

    public FaceContourDetectionProcessor(Context context, GraphicOverlay view){

        this.context = context;

        graphicOverlay = view;

        // Parametrage des options de la detection du visage
        // Mode rapide, sans contours et avec classification pour définir si la personne souris
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


    // Methode appelée lorsque l'analyse de l'image est un succés
    // Elle prend en paramètre la liste des visage détectés, le graphicOverlay et l'emplacement de l'affichage de l'overlay avec rect
    @Override
    protected void onSuccess(List<Face> results, GraphicOverlay graphicOverlay, Rect rect) {
        // Efface l'ancien overlay
        graphicOverlay.clear();

        // Si au moins un visage a ete detecté
        if(results.size() != 0) {

            // On trouve le visage le plus proéminent de l'image
            Face mostProminentFace = null;

            for (Face face : results) {
                if (mostProminentFace == null || face.getBoundingBox().width() > mostProminentFace.getBoundingBox().width()) {
                    mostProminentFace = face;
                }
            }

            // Si le visage a un taux de sourire supérieur à 30 % alors le contour est affiché dans la couleur de la réponse 1 (vert) sinon dans la couleur de la réponse 2 (orange)
            boolean isSmiling = mostProminentFace.getSmilingProbability() > 0.30f;
            int color = isSmiling ? Color.parseColor("#1bde62") : Color.parseColor("#f28500");

            // Changement de la réponse en fonction du sourire dans l'activité PlayQuizActivity
            ((PlayQuizActivity)context).answer = isSmiling;

            // Affichage d'un rectangle entourant le vidage dans le graphicOverlay
            FaceContourGraphic faceGraphic = new FaceContourGraphic(graphicOverlay, mostProminentFace, rect, color);
            graphicOverlay.add(faceGraphic);
        }

        graphicOverlay.postInvalidate();
    }

    // Message d'erreur lors d'un echec
    @Override
    protected void onFailure(Exception e) {
        Log.w(TAG, "Face Detector failed" + e);
    }

    // Differentes variables de la class
    private Context context;
    private FaceDetectorOptions realTimeOpts;
    private FaceDetector detector;
    private final static String TAG = "FaceDetectorProcessor";
}
