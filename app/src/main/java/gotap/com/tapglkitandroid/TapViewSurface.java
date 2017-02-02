package gotap.com.tapglkitandroid;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Morgot on 24.01.17.
 */

public class TapViewSurface extends GLSurfaceView {

    private final TapRender mRenderer;
    static public TapViewSurface shared;
    public TapViewSurface(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new TapRender();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        shared = this;
    }
}
