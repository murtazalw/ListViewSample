package com.example.murtaza.listviewsample;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Murtaza on 24-May-2016.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    ListView listView;
    ArrayList<String> list;
    private GestureDetector gestureDetector;
    private Context context;

    public OnSwipeTouchListener(Context context, ListView listView, ArrayList<String> list) {
        gestureDetector = new GestureDetector(context, new GestureListener());
        this.context = context;
        this.listView = listView;
        this.list = list;
    }

    public OnSwipeTouchListener() {
        super();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void onSwipeRight(int pos) {

    }

    public void onSwipeLeft(int pos) {

    }

    public void swipe(int pos, Animation a){

    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        private int getPostion(MotionEvent e1) {
            return listView.pointToPosition((int) e1.getX(), (int) e1.getY());
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                onSwipeRight(getPostion(e1));
                else
                onSwipeLeft(getPostion(e1));
                return true;
            }
            return false;
        }

    }
}
