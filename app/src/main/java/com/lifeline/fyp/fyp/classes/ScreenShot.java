package com.lifeline.fyp.fyp.classes;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Mujtaba_khalid on 4/14/2018.
 */

public class ScreenShot {
 public static Bitmap takeScreenshot(View v){
     v.setDrawingCacheEnabled(true);
     v.buildDrawingCache(true);

     Bitmap bitmap = Bitmap.createBitmap( v.getDrawingCache() );
     v.setDrawingCacheEnabled( false );
     return bitmap;
 }

 public static Bitmap takeScreenshotOfRootView(View v){
     return  takeScreenshot( v.getRootView() );

 }
}
