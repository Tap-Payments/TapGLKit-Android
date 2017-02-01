package gotap.com.tapglkitandroid;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Morgot on 24.01.17.
 */

public class TapRender implements GLSurfaceView.Renderer {

    TapLoading loading;

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        loading.draw();

    }

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
            loading = new TapLoading();
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
