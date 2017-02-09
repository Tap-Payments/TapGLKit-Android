package gotap.com.tapglkitandroid.gl.Views;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.lang.reflect.InvocationTargetException;

import javax.microedition.khronos.opengles.GL10;

import gotap.com.tapglkitandroid.gl.Shaders.BaseShader;

/**
 * Created by Morgot on 24.01.17.
 */

public class TapRender implements GLSurfaceView.Renderer {


    public interface TapRenderListener{
        float width();
        float height();
        int getColor();
        boolean isForceStop();
        float getTimer();
        boolean useCustomColor();
    }

    private Context context;
    private TapRenderListener listener;

    BaseShader view;
    Class shader;

    public TapRender(Context context, Class shader,TapRenderListener listener) {
        this.context = context;
        this.shader = shader;
        this.listener = listener;
    }

    public void onDrawFrame(GL10 unused) {
        view.draw();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        try {
            view  = (BaseShader)shader.getConstructors()[0].newInstance(this.context,listener);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {

    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
