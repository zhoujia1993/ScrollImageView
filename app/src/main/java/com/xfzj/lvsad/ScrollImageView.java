package com.xfzj.lvsad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class ScrollImageView extends android.support.v7.widget.AppCompatImageView {
    /**
     * ImageView的初始高度
     */
    private static final float IMAGE_VIEW_HEIGHT = 100f;
    /**
     * 原始的图片资源
     */
    private Bitmap originBitmap;

    public ScrollImageView(Context context) {
        super(context);
    }

    public ScrollImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageResource(int resId) {
        originBitmap = BitmapFactory.decodeResource(getContext().getResources(), resId);
    }

    public void setBitmap(Bitmap bitmap) {
        originBitmap = bitmap;
    }

    public void initImageSize(int width, int height) {
        if (originBitmap == null) {
            throw new NullPointerException("请先调用setImageResource(int resId)或setBitmap(Bitmap bitmap)方法");
        }
        int originWidth = originBitmap.getWidth();
        int originHeight = originBitmap.getHeight();
        float scaleWidth = (float) width / originWidth;
        float scaleHeight = (float) height / originHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        originBitmap = Bitmap.createBitmap(originBitmap, 0, 0, originWidth, originHeight, matrix, true);
        setImageBitmap(originBitmap);
    }

    /**
     * 动态裁减图片
     * @param startY 要裁减图片的开始Y值
     */
    public void scrollImage(int startY) {
        int height = Utils.dp2px(getContext(), IMAGE_VIEW_HEIGHT);
        //滑动到图片底部
        if (startY + height >= originBitmap.getHeight()) {
            startY = originBitmap.getHeight() - height;
        }
        Bitmap bitmap = Bitmap.createBitmap(originBitmap, 0, startY, originBitmap.getWidth(), height);
        setImageBitmap(bitmap);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Utils.dp2px(getContext(), IMAGE_VIEW_HEIGHT), MeasureSpec.EXACTLY));
    }
}
