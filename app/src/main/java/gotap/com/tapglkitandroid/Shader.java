package gotap.com.tapglkitandroid;

/**
 * Created by Morgot on 24.01.17.
 */

public class Shader {

    public static String QuadGradientViewFragmentShader =
            "uniform highp vec2 resolution;" +
            "uniform highp vec4 topLeftColor;" +
            "uniform highp vec4 topRightColor;" +
            "uniform highp vec4 bottomLeftColor;" +
            "uniform highp vec4 bottomRightColor;" +
            " " +
            "//MARK: Function Definitions" +
            " " +
            "highp vec4 interpolateColors(highp vec4 color1, highp vec4 color2, highp float progress);" +
            "highp float interpolateValue(highp float value1, highp float value2, highp float progress);" +
            " " +
            "//MARK: Main" +
            " " +
            "void main() {" +
            "    " +
            "    highp vec2 position = gl_FragCoord.xy;" +
            "    " +
            "    highp vec2 progress = vec2(position.x / resolution.x, position.y / resolution.y);" +
            "    " +
            "    highp vec4 topBorderColor = interpolateColors(topLeftColor, topRightColor, progress.x);" +
            "    highp vec4 bottomBorderColor = interpolateColors(bottomLeftColor, bottomRightColor, progress.x);" +
            "    highp vec4 horizontalColor = interpolateColors(bottomBorderColor, topBorderColor, progress.y);" +
            "    " +
            "    highp vec4 leftBorderColor = interpolateColors(bottomLeftColor, topLeftColor, progress.y);" +
            "    highp vec4 rightBorderColor = interpolateColors(bottomRightColor, topRightColor, progress.y);" +
            "    highp vec4 verticalColor = interpolateColors(leftBorderColor, rightBorderColor, progress.x);" +
            "    " +
            "    gl_FragColor = 0.5 * ( horizontalColor + verticalColor );" +
            "}" +
            " " +
            "//MARK: Functions" +
            " " +
            "highp vec4 interpolateColors(highp vec4 color1, highp vec4 color2, highp float progress) {" +
            "    " +
            "    return vec4(interpolateValue(color1.x, color2.x, progress), interpolateValue(color1.y, color2.y, progress), interpolateValue(color1.z, color2.z, progress), interpolateValue(color1.w, color2.w, progress));" +
            "}" +
            " " +
            "highp float interpolateValue(highp float value1, highp float value2, highp float progress) {" +
            "    " +
            "    return value1 + (value2 - value1) * progress;" +
            "}";


    public static String QuadGradientViewVertexShader = "attribute highp vec2 position;" +
            " " +
            "void main(void) {" +
            "    " +
            "    gl_Position = vec4(position, 0.0, 1.0);" +
            "}";


    public  static String TapActivityIndicatorViewFragmentShader = "//\n" +
            "//  TapActivityIndicatorViewFragmentShader.fsh\n" +
            "//  TapGLKit/TapActivityIndicatorView\n" +
            "//\n" +
            "//  Created by Dennis Pashkov on 12/27/16.\n" +
            "//  Copyright © 2016 Tap Payments. All rights reserved.\n" +
            "//\n" +
            "#ifdef GL_ES_VERSION_2_0\n" +
            "    \"#version 100\\n\"\n" +
            "#else\n" +
            "    \"#version 120\\n\"\n" +
            "#endif\n" +
            "    ,\n" +
            "    // GLES2 precision specifiers\n" +
            "#ifdef GL_ES_VERSION_2_0\n" +
            "    // Define default float precision for fragment shaders:\n" +
            "    (type == GL_FRAGMENT_SHADER) ?\n" +
            "    \"#ifdef GL_FRAGMENT_PRECISION_HIGH\\n\"\n" +
            "    \"precision highp float;           \\n\"\n" +
            "    \"#else                            \\n\"\n" +
            "    \"precision mediump float;         \\n\"\n" +
            "    \"#endif                           \\n\"\n" +
            "    : \"\"\n" +
            "    // Note: OpenGL ES automatically defines this:\n" +
            "    // #define GL_ES\n" +
            "#else\n" +
            "    // Ignore GLES 2 precision specifiers:\n" +
            "    \"#define lowp   \\n\"\n" +
            "    \"#define mediump\\n\"\n" +
            "    \"#define highp  \\n\"\n" +
            "#endif"+
            "\n" +
            "#define M_PI 3.1415926535897932384626433832795\n" +
            "#define YES 1\n" +
            "\n" +
            "uniform highp vec2  resolution;\n" +
            "uniform highp vec4  clearColor;\n" +
            "uniform highp vec4  outterCircleColor;\n" +
            "uniform highp vec4  innerCircleColor;\n" +
            "uniform       int   usesCustomColors;\n" +
            "uniform highp float time;\n" +
            "\n" +
            "const   highp float animationDuration                = 5.0;\n" +
            "\n" +
            "const   highp float minimalSizePortion               = 0.05;\n" +
            "const   highp float maximalSizePortion               = 1.0;\n" +
            "\n" +
            "const   highp float biggenAnimationPortion           = 0.4;\n" +
            "const   highp float smallenAnimationPortion          = 0.4;\n" +
            "const   highp float staticBigAnimationPortion        = 0.1;\n" +
            "\n" +
            "const   highp float numberOfRotationsDuringAnimation = 3.0;\n" +
            "\n" +
            "const         int   kAnimationStateGrowing           = 0;\n" +
            "const         int   kAnimationStateStaticBig         = 1;\n" +
            "const         int   kAnimationStateShrinking         = 2;\n" +
            "const         int   kAnimationStateStaticSmall       = 3;\n" +
            "\n" +
            "const   lowp  mat4  defaultColors = mat4(\n" +
            "\n" +
            "    42.0 / 255.0, 206.0 / 255.0,           0.0, 1.0,\n" +
            "    76.0 / 255.0,  72.0 / 255.0,  71.0 / 255.0, 1.0,\n" +
            "             0.0, 175.0 / 255.0, 240.0 / 255.0, 1.0,\n" +
            "    75.0 / 255.0,  72.0 / 255.0,  71.0 / 255.0, 1.0\n" +
            ");\n" +
            "\n" +
            "//MARK: Function Definitions\n" +
            "\n" +
            "bool isPointInsideCircle(highp vec2 point, highp vec2 center, highp float biggerRadius, highp float smallerRadius, bool clockwise);\n" +
            "highp float pointAngle(highp vec2 point, highp vec2 center);\n" +
            "highp vec2 possibleAngleRange(bool clockwise);\n" +
            "highp float angleRangeLength();\n" +
            "int currentAnimationState();\n" +
            "highp vec4 animationTimings();\n" +
            "highp float currentTimeOffset();\n" +
            "highp float bringValueTo2PiRange(highp float value);\n" +
            "bool isAngleInAllowedRange(highp float angle, highp vec2 allowedRange);\n" +
            "bool isBetween(highp float angle, highp vec2 range);\n" +
            "highp vec2 pointCoordinates(highp vec2 center, highp float radius, highp float angle);\n" +
            "highp vec4 capCircles(highp vec2 center, highp float biggerRadius, highp float smallerRadius, highp float startAngle, highp float endAngle);\n" +
            "lowp vec4 displayedOutterColor();\n" +
            "lowp vec4 displayedInnerColor();\n" +
            "lowp vec4 defaultCurrentColor();\n" +
            "lowp vec4 interpolateColors(lowp vec4 color1, lowp vec4 color2, highp float progress);\n" +
            "lowp float interpolateValue(lowp float value1, lowp float value2, highp float progress);\n" +
            "\n" +
            "//MARK: Main\n" +
            "\n" +
            "void main() {\n" +
            "    \n" +
            "    highp vec2 center = vec2(resolution.x / 2.0, resolution.y / 2.0);\n" +
            "    highp float halfSize = min(resolution.x, resolution.y);\n" +
            "    highp float outterCircleBiggerRadius = halfSize / 2.0;\n" +
            "    highp float outterCircleSmallerRadius = outterCircleBiggerRadius - halfSize / 14.0;\n" +
            "    highp float innerCircleBiggerRadius = halfSize * 25.0 / 77.0;\n" +
            "    highp float innerCircleSmallerRadius = innerCircleBiggerRadius - halfSize * 6.0 / 77.0;\n" +
            "    \n" +
            "    highp vec2 position = gl_FragCoord.xy;\n" +
            "    \n" +
            "    if ( isPointInsideCircle(position, center, outterCircleBiggerRadius, outterCircleSmallerRadius, false) ) {\n" +
            "        \n" +
            "        gl_FragColor = displayedOutterColor();\n" +
            "    }\n" +
            "    else if ( isPointInsideCircle(position, center, innerCircleBiggerRadius, innerCircleSmallerRadius, true) ) {\n" +
            "        \n" +
            "        gl_FragColor = displayedInnerColor();\n" +
            "    }\n" +
            "    else {\n" +
            "        \n" +
            "        gl_FragColor = clearColor;\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "//MARK: Geometry calculations.\n" +
            "\n" +
            "bool isPointInsideCircle(highp vec2 point, highp vec2 center, highp float biggerRadius, highp float smallerRadius, bool clockwise) {\n" +
            "    \n" +
            "    highp float distanceFromCenter = distance(center, point);\n" +
            "    \n" +
            "    if ( distanceFromCenter > biggerRadius || distanceFromCenter < smallerRadius ) {\n" +
            "        \n" +
            "        return false;\n" +
            "    }\n" +
            "    \n" +
            "    highp vec2 possibleAngles = possibleAngleRange(clockwise);\n" +
            "    highp float pointAngle = pointAngle(point, center);\n" +
            "    \n" +
            "    if ( isAngleInAllowedRange(pointAngle, possibleAngles)) {\n" +
            "        \n" +
            "        return true;\n" +
            "    }\n" +
            "    \n" +
            "    highp vec4 capCircles = capCircles(center, biggerRadius, smallerRadius, possibleAngles.x, possibleAngles.y);\n" +
            "    highp float capCircleRadius = 0.5 * ( biggerRadius - smallerRadius);\n" +
            "    \n" +
            "    if ( distance(vec2(capCircles.x, capCircles.y), point) <= capCircleRadius ) {\n" +
            "        \n" +
            "        return true;\n" +
            "    }\n" +
            "    \n" +
            "    if ( distance(vec2(capCircles.z, capCircles.w), point) <= capCircleRadius ) {\n" +
            "        \n" +
            "        return true;\n" +
            "    }\n" +
            "    \n" +
            "    return false;\n" +
            "}\n" +
            "\n" +
            "highp float pointAngle(highp vec2 point, highp vec2 center) {\n" +
            "    \n" +
            "    highp float distanceFromCenter = distance(center, point);\n" +
            "    highp float acosValue = acos((point.x - center.x) / distanceFromCenter);\n" +
            "    \n" +
            "    highp float result;\n" +
            "    \n" +
            "    if ( center.y < point.y ) {\n" +
            "        \n" +
            "        result = -acosValue;\n" +
            "    }\n" +
            "    else {\n" +
            "        \n" +
            "        result = acosValue;\n" +
            "    }\n" +
            "    \n" +
            "    return bringValueTo2PiRange(result);\n" +
            "}\n" +
            "\n" +
            "highp vec2 possibleAngleRange(bool clockwise) {\n" +
            "    \n" +
            "    highp float timeOffset = currentTimeOffset();\n" +
            "    highp float positiveAngle = 2.0 * M_PI * numberOfRotationsDuringAnimation * timeOffset / animationDuration;\n" +
            "    \n" +
            "    highp float startAngle;\n" +
            "    highp float rangeLength = angleRangeLength();\n" +
            "    \n" +
            "    if ( clockwise ) {\n" +
            "        \n" +
            "        startAngle = positiveAngle + M_PI;\n" +
            "    }\n" +
            "    else {\n" +
            "        \n" +
            "        startAngle = - positiveAngle - rangeLength;\n" +
            "    }\n" +
            "    \n" +
            "    highp vec2 range = vec2(-M_PI, M_PI);\n" +
            "    \n" +
            "    startAngle = bringValueTo2PiRange(startAngle);\n" +
            "    highp float endAngle = startAngle + rangeLength;\n" +
            "    \n" +
            "    return vec2(startAngle, endAngle);\n" +
            "}\n" +
            "\n" +
            "highp float angleRangeLength() {\n" +
            "    \n" +
            "    highp float timeOffset = currentTimeOffset();\n" +
            "    int animationState = currentAnimationState();\n" +
            "    highp vec4 timings = animationTimings();\n" +
            "    highp float percentageResult;\n" +
            "    \n" +
            "    if ( animationState == kAnimationStateGrowing ) {\n" +
            "        \n" +
            "        percentageResult = minimalSizePortion + (maximalSizePortion - minimalSizePortion) * timeOffset / timings.x;\n" +
            "    }\n" +
            "    else if ( animationState == kAnimationStateStaticBig ) {\n" +
            "        \n" +
            "        percentageResult = maximalSizePortion;\n" +
            "    }\n" +
            "    else if ( animationState == kAnimationStateShrinking ) {\n" +
            "        \n" +
            "        percentageResult = maximalSizePortion - ( maximalSizePortion - minimalSizePortion ) * ( timeOffset - timings.y ) / ( timings.z - timings.y );\n" +
            "    }\n" +
            "    else {\n" +
            "        \n" +
            "        percentageResult = minimalSizePortion;\n" +
            "    }\n" +
            "    \n" +
            "    return 2.0 * M_PI * percentageResult;\n" +
            "}\n" +
            "\n" +
            "int currentAnimationState() {\n" +
            "    \n" +
            "    highp float timeOffset = currentTimeOffset();\n" +
            "    highp vec4 timings = animationTimings();\n" +
            "    \n" +
            "    if ( timeOffset < timings.x ) {\n" +
            "        \n" +
            "        return kAnimationStateGrowing;\n" +
            "    }\n" +
            "    else if ( timeOffset < timings.y ) {\n" +
            "        \n" +
            "        return kAnimationStateStaticBig;\n" +
            "    }\n" +
            "    else if ( timeOffset < timings.z ) {\n" +
            "        \n" +
            "        return kAnimationStateShrinking;\n" +
            "    }\n" +
            "    else {\n" +
            "        \n" +
            "        return kAnimationStateStaticSmall;\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "highp vec4 animationTimings() {\n" +
            "    \n" +
            "    highp float biggenAnimationCompletion = biggenAnimationPortion * animationDuration;\n" +
            "    highp float staticBigAnimationCompletion = biggenAnimationCompletion + staticBigAnimationPortion * animationDuration;\n" +
            "    highp float smallenAnimationCompletion = staticBigAnimationCompletion + smallenAnimationPortion * animationDuration;\n" +
            "    \n" +
            "    return vec4(biggenAnimationCompletion, staticBigAnimationCompletion, smallenAnimationCompletion, animationDuration);\n" +
            "}\n" +
            "\n" +
            "highp float currentTimeOffset() {\n" +
            "    \n" +
            "    return mod(time, animationDuration);\n" +
            "}\n" +
            "\n" +
            "highp float bringValueTo2PiRange(highp float value) {\n" +
            "    \n" +
            "    highp float twoPi = 2.0 * M_PI;\n" +
            "    \n" +
            "    highp float positiveValue = value;\n" +
            "\n" +
            "    if ( positiveValue < 0.0 ) {\n" +
            "\n" +
            "        positiveValue +=  twoPi * float(int(positiveValue / twoPi)) * float(-1);\n" +
            "    }\n" +
            "\n" +
            "    highp float rangeLength = twoPi;\n" +
            "    highp float resultValue = mod(positiveValue, rangeLength);\n" +
            "\n" +
            "    return resultValue;\n" +
            "}\n" +
            "\n" +
            "bool isAngleInAllowedRange(highp float angle, highp vec2 allowedRange) {\n" +
            "\n" +
            "    return isBetween(angle, allowedRange) || isBetween(angle + 2.0 * M_PI, allowedRange);\n" +
            "}\n" +
            "\n" +
            "bool isBetween(highp float angle, highp vec2 range) {\n" +
            "\n" +
            "    return (range.x <= angle) && (angle <= range.y);\n" +
            "}\n" +
            "\n" +
            "highp vec4 capCircles(highp vec2 center, highp float biggerRadius, highp float smallerRadius, highp float startAngle, highp float endAngle) {\n" +
            "\n" +
            "    highp float mediumRadius = 0.5 * (biggerRadius + smallerRadius);\n" +
            "\n" +
            "    highp vec2 startCenter = pointCoordinates(center, mediumRadius, startAngle);\n" +
            "    highp vec2 endCenter = pointCoordinates(center, mediumRadius, endAngle);\n" +
            "\n" +
            "    return vec4(startCenter, endCenter);\n" +
            "}\n" +
            "\n" +
            "highp vec2 pointCoordinates(highp vec2 center, highp float radius, highp float angle) {\n" +
            "\n" +
            "    return vec2(center.x + radius * cos(angle), center.y - radius * sin(angle));\n" +
            "}\n" +
            "\n" +
            "//MARK: Colors\n" +
            "\n" +
            "lowp vec4 displayedOutterColor() {\n" +
            "\n" +
            "    if ( usesCustomColors == YES ) {\n" +
            "\n" +
            "        return outterCircleColor;\n" +
            "    }\n" +
            "\n" +
            "    return defaultCurrentColor();\n" +
            "}\n" +
            "\n" +
            "lowp vec4 displayedInnerColor() {\n" +
            "\n" +
            "    if ( usesCustomColors == YES ) {\n" +
            "\n" +
            "        return innerCircleColor;\n" +
            "    }\n" +
            "\n" +
            "    return defaultCurrentColor();\n" +
            "}\n" +
            "\n" +
            "lowp vec4 defaultCurrentColor() {\n" +
            "\n" +
            "    highp float timeOffset = currentTimeOffset();\n" +
            "    highp vec4 timings = animationTimings();\n" +
            "    int animationState = currentAnimationState();\n" +
            "\n" +
            "    if ( animationState == kAnimationStateGrowing ) {\n" +
            "\n" +
            "        highp float progress = timeOffset / timings.x;\n" +
            "        return interpolateColors(defaultColors[0], defaultColors[1], progress);\n" +
            "    }\n" +
            "    else if ( animationState == kAnimationStateStaticBig ) {\n" +
            "\n" +
            "        highp float progress = (timeOffset - timings.x) / (timings.y - timings.x);\n" +
            "        return interpolateColors(defaultColors[1], defaultColors[2], progress);\n" +
            "    }\n" +
            "    else if ( animationState == kAnimationStateShrinking ) {\n" +
            "\n" +
            "        highp float progress = (timeOffset - timings.y) / (timings.z - timings.y);\n" +
            "        return interpolateColors(defaultColors[2], defaultColors[3], progress);\n" +
            "    }\n" +
            "    else {\n" +
            "\n" +
            "        highp float progress = (timeOffset - timings.z) / (timings.w - timings.z);\n" +
            "        return interpolateColors(defaultColors[3], defaultColors[0], progress);\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "lowp vec4 interpolateColors(lowp vec4 color1, lowp vec4 color2, highp float progress) {\n" +
            "\n" +
            "    return vec4(interpolateValue(color1.x, color2.x, progress), interpolateValue(color1.y, color2.y, progress), interpolateValue(color1.z, color2.z, progress), interpolateValue(color1.w, color2.w, progress));\n" +
            "}\n" +
            "\n" +
            "lowp float interpolateValue(lowp float value1, lowp float value2, highp float progress) {\n" +
            "\n" +
            "    return value1 + (value2 - value1) * progress;\n" +
            "}\n" +
            "\n" +
            "\n";

    public static String TapActivityIndicatorViewVertexShader = "attribute highp vec2 position;\n" +
            "\n" +
            "void main(void) {\n" +
            "    \n" +
            "    gl_Position = vec4(position, 0.0, 1.0);\n" +
            "}";


    public static String vertexShaderCode =
            "attribute vec4 position;" +
                    "void main() {" +
                    "  gl_Position = position;" +
                    "}";

    public static String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 color;" +
                    "void main() {" +
                    "  gl_FragColor = color;" +
                    "}";


}
