package gotap.com.tapglkitandroid;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Morgot on 24.01.17.
 */

public class TapLoading {


    private final int mProgram;





    static float coords[] = {   // in counterclockwise order:
            -1.0f, -1.0f,
            -1.0f,  1.0f,
            1.0f, -1.0f,
            1.0f,  1.0f
    };
    // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    private int mPositionHandle;
    private int mResolutionHandle;
    private int mColorHandle;
    private int outterCircleColorUniform;
    private int innerCircleColorUniform;
    private int clearColorUniform;
    private int usesCustomColorsUniform;
    private int timeUniform;


    private String kAttributePositionKey = "position";
    private String kUniformResolutionKey = "resolution";
    private String kUniformOutterCircleColorKey = "outterCircleColor";
    private String kUniformInnerCircleColorKey = "innerCircleColor";
    private String kUniformClearColorKey = "clearColor";
    private String kUniformUsesCustomColorsKey = "usesCustomColors";
    private String kUniformTimeKey = "time";

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    private float outterColorComponents[] = {1.0f, 1.0f, 1.0f, 1.0f};
    private float innerColorComponents [] = {1.0f, 1.0f, 1.0f, 1.0f};


    public TapLoading(){

        // Create an OpenGL ES 2.0 context
        int vertexShader = TapRender.loadShader(GLES20.GL_VERTEX_SHADER,
                Shader.TapActivityIndicatorViewVertexShader);
        int fragmentShader = TapRender.loadShader(GLES20.GL_FRAGMENT_SHADER,
                Shader.TapActivityIndicatorViewFragmentShader);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);




        GLES20.glLinkProgram(mProgram);
        // creates OpenGL ES program executables
        obtainAttributesAndUniforms();
        configureOpenGLES();

        // Set the Renderer for drawing on the GLSurfaceView
    }

    void obtainAttributesAndUniforms(){
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, kAttributePositionKey);
        mResolutionHandle = GLES20.glGetAttribLocation(mProgram, kUniformResolutionKey);
        outterCircleColorUniform = GLES20.glGetUniformLocation(mProgram,kUniformOutterCircleColorKey);
        innerCircleColorUniform = GLES20.glGetUniformLocation(mProgram,kUniformInnerCircleColorKey);
        clearColorUniform = GLES20.glGetUniformLocation(mProgram,kUniformClearColorKey);
        usesCustomColorsUniform = GLES20.glGetUniformLocation(mProgram,kUniformUsesCustomColorsKey);
        timeUniform = GLES20.glGetUniformLocation(mProgram,kUniformTimeKey);
    }
    FloatBuffer buffer;
    void configureOpenGLES(){


        ByteBuffer byteBuf = ByteBuffer.allocateDirect(coords.length * 4); //4 bytes per float
        byteBuf.order(ByteOrder.nativeOrder());
        buffer = byteBuf.asFloatBuffer();
        buffer.put(coords);
        buffer.position(0);

        GLES20.glUseProgram(mProgram);

        // Prepare the triangle coordinate data




        // get handle to vertex shader's vPosition member
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);



        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, 2,
                GLES20.GL_FLOAT, true,
                coords.length * 4, buffer);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glUniform2f(mResolutionHandle,TapViewSurface.shared.getWidth(),TapViewSurface.shared.getHeight());
        GLES20.glUniform4f(outterCircleColorUniform,outterColorComponents[0],outterColorComponents[1],outterColorComponents[2],outterColorComponents[3]);
        GLES20.glUniform4f(innerCircleColorUniform,innerColorComponents[0],innerColorComponents[1],innerColorComponents[2],innerColorComponents[3]);
        GLES20.glUniform4f(clearColorUniform,.0f,.0f,.0f,.0f);
        GLES20.glUniform1i(usesCustomColorsUniform,0);


    }

    public void draw(int time) {
        GLES20.glUseProgram(mProgram);
        GLES20.glUniform1f(timeUniform,2.25f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
    }


}
