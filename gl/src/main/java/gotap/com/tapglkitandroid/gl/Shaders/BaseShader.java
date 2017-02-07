package gotap.com.tapglkitandroid.gl.Shaders;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import gotap.com.tapglkitandroid.gl.Views.TapRender;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glUniform2f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by Morgot on 07.02.17.
 */

public abstract class BaseShader {

    protected int programId;
    private FloatBuffer vertexData;

    private String kAttributePositionKey = "position";
    private String kUniformResolutionKey = "resolution";

    private int mPositionHandle;
    private int mResolutionHandle;

    abstract void obtainAttributesAndUniformsForShader();
    abstract void drawElement();
    abstract int vertexShaderRaw();
    abstract int fragmentShaderRaw();

    protected TapRender.TapRenderListener listener;

    public BaseShader(Context context,TapRender.TapRenderListener listener){

        // Create an OpenGL ES 2.0 context
        int vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER,vertexShaderRaw());
        int fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, fragmentShaderRaw());
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        glUseProgram(programId);
        obtainAttributesAndUniforms();
        this.listener = listener;
    }

    void obtainAttributesAndUniforms(){

        float[] vertices = {   // in counterclockwise order:
                -1.0f, -1.0f,
                -1.0f,  1.0f,
                1.0f, -1.0f,
                1.0f,  1.0f
        };
        vertexData = ByteBuffer
                .allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(vertices);
        mPositionHandle = GLES20.glGetAttribLocation(programId, kAttributePositionKey);
        mResolutionHandle = GLES20.glGetUniformLocation(programId, kUniformResolutionKey);
        vertexData.position(0);
        obtainAttributesAndUniformsForShader();
        glVertexAttribPointer(mPositionHandle, 2, GL_FLOAT,
                false, 0, vertexData);
        glEnableVertexAttribArray(mPositionHandle);
    }

    public void draw(){
        glClear(GL_COLOR_BUFFER_BIT);
        if(listener!=null){
            glUniform2f(mResolutionHandle, listener.width(), listener.height());
        }else {
            glUniform2f(mResolutionHandle, 200f, 200f);
        }
        drawElement();
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }



}
