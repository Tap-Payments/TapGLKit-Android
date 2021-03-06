package gotap.com.tapglkitandroid.gl.Shaders;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.os.Build;

import gotap.com.tapglkitandroid.gl.R;
import gotap.com.tapglkitandroid.gl.Views.TapRender;


/**
 * Created by Morgot on 06.02.17.
 */

public class TapLoadingShader extends BaseShader{




    private final String kUniformOutterCircleColorKey = "outterCircleColor";
    private final String kUniformInnerCircleColorKey = "innerCircleColor";
    private final String kUniformClearColorKey = "clearColor";
    private final String kUniformUsesCustomColorsKey = "usesCustomColors";
    private final String kUniformTimeKey = "time";


    private int outterCircleColorUniform;
    private int innerCircleColorUniform;
    private int clearColorUniform;
    private int usesCustomColorsUniform;
    private int timeUniform;

    private float outterColorComponents[] = {1.0f, 1.0f, 1.0f, 1.0f};
    private float innerColorComponents [] = {1.0f, 1.0f, 1.0f, 1.0f};

    public TapLoadingShader(Context context, TapRender.TapRenderListener listener) {
        super(context,listener);
    }

    @Override
    int vertexShaderRaw() {
        return R.raw.vertex_shader;
    }

    @Override
    int fragmentShaderRaw() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ? R.raw.fragment_shader : R.raw.fragment_shader_android_5_and_above;
    }

    void obtainAttributesAndUniformsForShader(){
        outterCircleColorUniform = GLES20.glGetUniformLocation(programId,kUniformOutterCircleColorKey);
        innerCircleColorUniform = GLES20.glGetUniformLocation(programId,kUniformInnerCircleColorKey);
        clearColorUniform = GLES20.glGetUniformLocation(programId,kUniformClearColorKey);
        usesCustomColorsUniform = GLES20.glGetUniformLocation(programId,kUniformUsesCustomColorsKey);
        timeUniform = GLES20.glGetUniformLocation(programId,kUniformTimeKey);
    }

    float timer=0;

    @Override
    void drawElement() {
        GLES20.glUniform1i(usesCustomColorsUniform,0);
        if(listener==null) {
            GLES20.glUniform1f(timeUniform, (timer++) / 60);
        }else{
            GLES20.glUniform1f(timeUniform,listener.getTimer());
            if(listener.useCustomColor()) {
                setColor();
                GLES20.glUniform1i(usesCustomColorsUniform,1);
            }
        }
        GLES20.glUniform4f(outterCircleColorUniform,outterColorComponents[0],outterColorComponents[1],outterColorComponents[2],outterColorComponents[3]);
        GLES20.glUniform4f(innerCircleColorUniform,innerColorComponents[0],innerColorComponents[1],innerColorComponents[2],innerColorComponents[3]);
        GLES20.glUniform4f(clearColorUniform,.0f,.0f,.0f,.0f);

    }

    void setColor(){
        outterColorComponents[0] = Color.red(listener.getColor())/255f;
        outterColorComponents[1] = Color.green(listener.getColor())/255f;
        outterColorComponents[2] = Color.blue(listener.getColor())/255f;
        outterColorComponents[3] = Color.alpha(listener.getColor())/255f;
        innerColorComponents[0] = Color.red(listener.getColor())/255f;
        innerColorComponents[1] = Color.green(listener.getColor())/255f;
        innerColorComponents[2] = Color.blue(listener.getColor())/255f;
        innerColorComponents[3] = Color.alpha(listener.getColor())/255f;
    }

}
