package gotap.com.tapglkitandroid.gl;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by Morgot on 24.01.17.
 */

public abstract class TapViewSurface extends GLSurfaceView  {

    protected abstract Class getShader();
    protected abstract TapRender.TapRenderListener listener();
    protected Context context;



    public void start(){
        onResume();
    }

    public void pause(){
        onPause();
    }

    public void init(Context context){
        this.context = context;
        setEGLContextClientVersion(2);

        setZOrderOnTop(true);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.RGBA_8888);
        setRenderer(new TapRender(context, getShader(),listener()));
        onPause();

    }

    public TapViewSurface(Context context) {
        super(context);
        init(context);
    }

    public TapViewSurface(Context context, AttributeSet attrs) {
        super(context,attrs);
        init(context);
    }

}
