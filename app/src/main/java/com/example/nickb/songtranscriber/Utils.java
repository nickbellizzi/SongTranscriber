package com.example.nickb.songtranscriber;

import android.os.Handler;


public class Utils {

    public interface DelayCallback{
        void afterDelay();
    }

    public static void delay(long millisecs, final DelayCallback delayCallback){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, millisecs);
    }
}