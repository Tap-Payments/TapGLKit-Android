package gotap.com.tapglkitandroid.gl.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import gotap.com.tapglkitandroid.R;
import gotap.com.tapglkitandroid.gl.Shaders.TapLoadingShader;

/**
 * Created by Morgot on 07.02.17.
 */

public class TapLoadingView extends TapViewSurface implements TapRender.TapRenderListener {

    private  float timer = 0;


    public boolean forceStop = false;
    public int color = Color.parseColor("#ffffff");
    public boolean useCustomColor = true;

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
        }else{
            color = Color.WHITE;
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

    @Override
    public void start() {
        super.start();
        if(isForceStop()) {
            timer = 0;
            forceStop = false;
        }
    }

    @Override
    public float getTimer() {

        if(isForceStop()&&(((timer>60 && (timer%60)/59==1)) ||timer==-1)){
            timer = -1;
            return 2.25f;
        }

        return (timer++/60);
    }

    @Override
    public boolean useCustomColor() {
        return useCustomColor;
    }
}
