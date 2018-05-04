package gotap.com.tapglkitandroid.gl.Views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import gotap.com.tapglkitandroid.gl.R;
import gotap.com.tapglkitandroid.gl.Shaders.TapLoadingShader;


/**
 * Created by Morgot on 07.02.17.
 */

public class TapLoadingView extends TapViewSurface implements TapRender.TapRenderListener {
    public interface FullProgressListener{
        void onFullProgress();
    }
    private FullProgressListener fullProgressListener;

    private float timer = 0;


    private boolean forceStop = false;
    public int color = Color.parseColor("#ffffff");
    public boolean useCustomColor = true;

    private boolean isStarted;
    private float percent;
    private boolean shouldUsePercent;

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
        shouldUsePercent = false;
    }

    public void setForceStop(boolean forceStop, FullProgressListener fullProgressListener) {
        this.fullProgressListener = fullProgressListener;
        setForceStop(forceStop);
    }

    public void setPercent(float percent) {
        shouldUsePercent = true;
        if (!isStarted) {
            this.percent = percent;
            super.start();
        }
    }

    @Override
    public void start() {
        timer = -1;
        if(isForceStop()) {
            forceStop = false;
        }
        super.start();
        isStarted = true;
        shouldUsePercent = false;
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
        if (isForceStop() && !shouldUsePercent) {
            boolean timerCondition = timer / 60 % 2.5f == 0 && timer / 60 % 5.0f != 0;
            if (timerCondition || timer == -1) {
                if (timerCondition && fullProgressListener != null) {
                    fullProgressListener.onFullProgress();
                    fullProgressListener = null;
                }
//                Log.d("LoadingView", "getTimer 1st condition: timer = -1 return = 2.5f");
                timer = -1;
                return 2.5f;
            } else {
//                Log.d("LoadingView", "getTimer 1st condition: timer = " + timer + ", return = " + timer/60);
                return timer++/60;
            }
        }

        if (isStarted) {
//            Log.d("LoadingView", "getTimer 2nd condition: timer = " + timer + ", return = " + timer/60);
            return timer++/60;
        } else {
            float value = 4.5f - 2.0f * percent;
            timer = (int) value * 60;
//            Log.d("LoadingView", "getTimer 3rd condition: timer = " + timer + ", percent = " + percent + ", return = " + value);
            return value;
        }
    }

    @Override
    public boolean useCustomColor() {
        return useCustomColor;
    }

}
