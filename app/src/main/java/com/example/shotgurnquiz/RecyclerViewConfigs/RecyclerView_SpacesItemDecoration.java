package com.example.shotgurnquiz.RecyclerViewConfigs;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// ItemDecoration pour les recyclerView permettant d'ajouter des espaces entre les items
public class RecyclerView_SpacesItemDecoration extends RecyclerView.ItemDecoration {

    // Constructeur qui récupère la taille de la marge horizontale et verticale. Récupère aussi orientation du recyclerView
    public RecyclerView_SpacesItemDecoration(int horizontalCardSpace, int verticalCardSpace, int linearLayoutOrientation) {
        this.horizontalCardSpace = horizontalCardSpace;
        this.verticalCardSpace = verticalCardSpace;
        this.linearLayoutOrientation = linearLayoutOrientation;
    }

    // Ajoute les marges autour des items en fonction de l'orientation du recyclerView
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(linearLayoutOrientation == LinearLayoutManager.VERTICAL) {
            outRect.bottom = verticalCardSpace;
            outRect.right = horizontalCardSpace;
            outRect.left = horizontalCardSpace;

            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = verticalCardSpace;
            }
        }else {
            outRect.right = verticalCardSpace;
            outRect.top = horizontalCardSpace;
            outRect.bottom = horizontalCardSpace;

            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left = verticalCardSpace;
            }
        }
    }

    // Differentes variables de la class
    private final int horizontalCardSpace;
    private final int verticalCardSpace;
    private final int linearLayoutOrientation;
}
