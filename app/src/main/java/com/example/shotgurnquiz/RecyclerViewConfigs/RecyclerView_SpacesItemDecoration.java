package com.example.shotgurnquiz.RecyclerViewConfigs;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerView_SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int horizontalCardSpace;
    private int verticalCardSpace;
    private final int linearLayoutOrientation;

    public RecyclerView_SpacesItemDecoration(int horizontalCardSpace, int verticalCardSpace, int linearLayoutOrientation) {
        this.horizontalCardSpace = horizontalCardSpace;
        this.verticalCardSpace = verticalCardSpace;
        this.linearLayoutOrientation = linearLayoutOrientation;
    }

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
}
