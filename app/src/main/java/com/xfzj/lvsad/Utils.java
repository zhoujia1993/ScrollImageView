package com.xfzj.lvsad;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
    public static int dp2px(Context context, float dp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) (dm.density * dp);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
