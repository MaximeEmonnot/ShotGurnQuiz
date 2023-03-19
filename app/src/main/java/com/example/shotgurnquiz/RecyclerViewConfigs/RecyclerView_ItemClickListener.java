package com.example.shotgurnquiz.RecyclerViewConfigs;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// OnItemTouchListener personnalisé pour récupérer l'événement lors d'un clique sur un item du recyclerView
public class RecyclerView_ItemClickListener implements RecyclerView.OnItemTouchListener{

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public RecyclerView_ItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    // Lors de l'interception d'un appui sur le recyclerView
    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        // lors le doigt s'enfonce on récupère les coordonnées
        if (childView != null && mListener != null && e.getAction() == MotionEvent.ACTION_DOWN) {
            previousX = e.getX();
            previousY = e.getY();
            return false;
        }
        // lorsque le doigt relache on vérifie si le doigt n'a pas bougé depuis enfoncement pour ne pas confondre avec un scroll du recyclerView
        if(childView != null && mListener != null && e.getAction() == MotionEvent.ACTION_UP && e.getX() == previousX && e.getY() == previousY){
            // On notifie d'un clique avec la position de l'item cliqué
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}

    // Differentes variables de la class
    private OnItemClickListener mListener;
    private float previousX;
    private float previousY;
}
