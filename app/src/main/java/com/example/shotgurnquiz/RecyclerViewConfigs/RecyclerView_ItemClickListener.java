package com.example.shotgurnquiz.RecyclerViewConfigs;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerView_ItemClickListener implements RecyclerView.OnItemTouchListener{

    private OnItemClickListener mListener;

    private float previousX;

    private float previousY;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public RecyclerView_ItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && e.getAction() == MotionEvent.ACTION_DOWN) {
            previousX = e.getX();
            previousY = e.getY();
            return false;
        }
        if(childView != null && mListener != null && e.getAction() == MotionEvent.ACTION_UP && e.getX() == previousX && e.getY() == previousY){
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
}
