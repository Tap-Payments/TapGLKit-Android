package gotap.com.tapglkitandroid.gl.Views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import gotap.com.tapglkitandroid.gl.R;
import gotap.com.tapglkitandroid.gl.Shaders.TapLoadingShader;


/**
 * Created by Morgot on 07.02.17.
 */

public class TapLoadingView extends TapViewSurface implements TapRender.TapRenderListener {

    private  float timer = 0;


    private boolean forceStop = false;
    public int color = Color.parseColor("#ffffff");
    public boolean useCustomColor = true;

    private boolean isStarted;
    private float percent;

    @Override
    protected TapRender.TapRenderListener listener() {
        return this;
    }

    public TapLoadingView(Context context) {
        super(context);

    }

    @Override
    protected Class getShader() {
        return TapLoadingShader.class;
    }

    public TapLoadingView(Context context, AttributeSet attrs) {
        super(context,attrs);
        TypedArray a = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.TapLoadingView);
        useCustomColor = a.getBoolean(R.styleable.TapLoadingView_useCustomColor,false);
        color = a.getColor(R.styleable.TapLoadingView_customColor,-1);
        if(color!=-1) {
            useCustomColor = true;
        }
        a.recycle();
    }

    @Override
    public float width() {
        return this.getMeasuredWidth();
    }

    @Override
    public float height() {
        return this.getMeasuredHeight();
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public boolean isForceStop() {
        return forceStop;
    }

    public void setForceStop(boolean forceStop) {
        this.forceStop = forceStop;
        isStarted = !forceStop;
        percent = 1;
    }

    public void setPercent(float percent) {
        if (!isStarted) {
            this.percent = percent;
            super.start();
        }
    }

    @Override
    public void start() {
        timer = -1;
        super.start();
        if(isForceStop()) {
            forceStop = false;
        }
        isStarted = true;
    }

    public void startFromCurrent() {
        super.start();
        if(isForceStop()) {
            forceStop = false;
        }
        isStarted = true;
    }

    @Override
    public float getTimer() {
        if(isForceStop()&&(((timer>60 && (timer%60)/59==1)) ||timer==-1)){
            timer = -1;
            return 2.5f;
        }

        if (isStarted) {
            return timer++/60;
        } else {
            float value = 4.5f - 2.0f * percent;
            timer = value * 60;
            return value;
        }
    }

    @Override
    public boolean useCustomColor() {
        return useCustomColor;
    }
}
