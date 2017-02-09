//
//  TapActivityIndicatorViewFragmentShader.fsh
//  TapGLKit/TapActivityIndicatorView
//
//  Created by Dennis Pashkov on 12/27/16.
//  Copyright © 2016 Tap Payments. All rights reserved.
//

#define M_PI 3.1415926535897932384626433832795
#define YES 1

uniform highp vec2  resolution;
uniform highp vec4  clearColor;
uniform highp vec4  outterCircleColor;
uniform highp vec4  innerCircleColor;
uniform       int   usesCustomColors;
uniform highp float time;

const   highp float animationDuration                = 5.0;

const   highp float minimalSizePortion               = 0.05;
const   highp float maximalSizePortion               = 1.0;

const   highp float biggenAnimationPortion           = 0.4;
const   highp float smallenAnimationPortion          = 0.4;
const   highp float staticBigAnimationPortion        = 0.1;

const   highp float numberOfRotationsDuringAnimation = 3.0;

const         int   kAnimationStateGrowing           = 0;
const         int   kAnimationStateStaticBig         = 1;
const         int   kAnimationStateShrinking         = 2;
const         int   kAnimationStateStaticSmall       = 3;

const   lowp  mat4  defaultColors = mat4(

    42.0 / 255.0, 206.0 / 255.0,           0.0, 1.0,
    76.0 / 255.0,  72.0 / 255.0,  71.0 / 255.0, 1.0,
             0.0, 175.0 / 255.0, 240.0 / 255.0, 1.0,
    75.0 / 255.0,  72.0 / 255.0,  71.0 / 255.0, 1.0
);

//MARK: Function Definitions

bool isPointInsideCircle(highp vec2 point, highp vec2 center, highp float biggerRadius, highp float smallerRadius, bool clockwise);
highp float pointAngle(highp vec2 point, highp vec2 center);
highp vec2 possibleAngleRange(bool clockwise);
highp float angleRangeLength();
int currentAnimationState();
highp vec4 animationTimings();
highp float currentTimeOffset();
highp float bringValueTo2PiRange(highp float value);
bool isAngleInAllowedRange(highp float angle, highp vec2 allowedRange);
bool isBetween(highp float angle, highp vec2 range);
highp vec2 pointCoordinates(highp vec2 center, highp float radius, highp float angle);
highp vec4 capCircles(highp vec2 center, highp float biggerRadius, highp float smallerRadius, highp float startAngle, highp float endAngle);
lowp vec4 displayedOutterColor();
lowp vec4 displayedInnerColor();
lowp vec4 defaultCurrentColor();
lowp vec4 interpolateColors(lowp vec4 color1, lowp vec4 color2, highp float progress);
lowp float interpolateValue(lowp float value1, lowp float value2, highp float progress);

//MARK: Main

void main() {



    highp vec2 center = vec2(resolution.x / 2.0, resolution.y / 2.0);
    highp float halfSize = min(resolution.x, resolution.y);
    highp float outterCircleBiggerRadius = halfSize / 2.0;
    highp float outterCircleSmallerRadius = outterCircleBiggerRadius - halfSize / 14.0;
    highp float innerCircleBiggerRadius = halfSize * 25.0 / 77.0;
    highp float innerCircleSmallerRadius = innerCircleBiggerRadius - halfSize * 6.0 / 77.0;

    highp vec2 position = gl_FragCoord.xy;


    if ( isPointInsideCircle(position, center, outterCircleBiggerRadius, outterCircleSmallerRadius, false) ) {

        gl_FragColor = displayedOutterColor();
    }
    else if ( isPointInsideCircle(position, center, innerCircleBiggerRadius, innerCircleSmallerRadius, true) ) {

        gl_FragColor = displayedInnerColor();
    }
    else {

        gl_FragColor = clearColor;
    }
}

//MARK: Geometry calculations.

bool isPointInsideCircle(highp vec2 point, highp vec2 center, highp float biggerRadius, highp float smallerRadius, bool clockwise) {

    highp float distanceFromCenter = distance(center, point);

    if ( distanceFromCenter > biggerRadius || distanceFromCenter < smallerRadius ) {

        return false;
    }

    highp vec2 possibleAngles = possibleAngleRange(clockwise);
    highp float pointAngle = pointAngle(point, center);

    if ( isAngleInAllowedRange(pointAngle, possibleAngles)) {

        return true;
    }

    highp vec4 capCircles = capCircles(center, biggerRadius, smallerRadius, possibleAngles.x, possibleAngles.y);
    highp float capCircleRadius = 0.5 * ( biggerRadius - smallerRadius);

    if ( distance(vec2(capCircles.x, capCircles.y), point) <= capCircleRadius ) {

        return true;
    }

    if ( distance(vec2(capCircles.z, capCircles.w), point) <= capCircleRadius ) {

        return true;
    }

    return false;
}

highp float pointAngle(highp vec2 point, highp vec2 center) {

    highp float distanceFromCenter = distance(center, point);
    highp float acosValue = acos((point.x - center.x) / distanceFromCenter);

    highp float result;

    if ( center.y < point.y ) {

        result = -acosValue;
    }
    else {

        result = acosValue;
    }

    return bringValueTo2PiRange(result);
}

highp vec2 possibleAngleRange(bool clockwise) {

    highp float timeOffset = currentTimeOffset();
    highp float positiveAngle = 2.0 * M_PI * numberOfRotationsDuringAnimation * timeOffset / animationDuration;

    highp float startAngle;
    highp float rangeLength = angleRangeLength();

    if ( clockwise ) {

        startAngle = positiveAngle + M_PI;
    }
    else {

        startAngle = - positiveAngle - rangeLength;
    }

    highp vec2 range = vec2(-M_PI, M_PI);

    startAngle = bringValueTo2PiRange(startAngle);
    highp float endAngle = startAngle + rangeLength;

    return vec2(startAngle, endAngle);
}

highp float angleRangeLength() {

    highp float timeOffset = currentTimeOffset();
    int animationState = currentAnimationState();
    highp vec4 timings = animationTimings();
    highp float percentageResult;

    if ( animationState == kAnimationStateGrowing ) {

        percentageResult = minimalSizePortion + (maximalSizePortion - minimalSizePortion) * timeOffset / timings.x;
    }
    else if ( animationState == kAnimationStateStaticBig ) {

        percentageResult = maximalSizePortion;
    }
    else if ( animationState == kAnimationStateShrinking ) {

        percentageResult = maximalSizePortion - ( maximalSizePortion - minimalSizePortion ) * ( timeOffset - timings.y ) / ( timings.z - timings.y );
    }
    else {

        percentageResult = minimalSizePortion;
    }

    return 2.0 * M_PI * percentageResult;
}

int currentAnimationState() {

    highp float timeOffset = currentTimeOffset();
    highp vec4 timings = animationTimings();

    if ( timeOffset < timings.x ) {

        return kAnimationStateGrowing;
    }
    else if ( timeOffset < timings.y ) {

        return kAnimationStateStaticBig;
    }
    else if ( timeOffset < timings.z ) {

        return kAnimationStateShrinking;
    }
    else {

        return kAnimationStateStaticSmall;
    }
}

highp vec4 animationTimings() {

    highp float biggenAnimationCompletion = biggenAnimationPortion * animationDuration;
    highp float staticBigAnimationCompletion = biggenAnimationCompletion + staticBigAnimationPortion * animationDuration;
    highp float smallenAnimationCompletion = staticBigAnimationCompletion + smallenAnimationPortion * animationDuration;

    return vec4(biggenAnimationCompletion, staticBigAnimationCompletion, smallenAnimationCompletion, animationDuration);
}

highp float currentTimeOffset() {

    return mod(time, animationDuration);
}

highp float bringValueTo2PiRange(highp float value) {

    highp float twoPi = 2.0 * M_PI;

    highp float positiveValue = value;

    if ( positiveValue < 0.0 ) {

        positiveValue +=  twoPi * float(int(positiveValue / twoPi)) * float(-1);
    }

    highp float rangeLength = twoPi;
    highp float resultValue = mod(positiveValue, rangeLength);

    return resultValue;
}

bool isAngleInAllowedRange(highp float angle, highp vec2 allowedRange) {

    return isBetween(angle, allowedRange) || isBetween(angle + 2.0 * M_PI, allowedRange);
}

bool isBetween(highp float angle, highp vec2 range) {

    return (range.x <= angle) && (angle <= range.y);
}

highp vec4 capCircles(highp vec2 center, highp float biggerRadius, highp float smallerRadius, highp float startAngle, highp float endAngle) {

    highp float mediumRadius = 0.5 * (biggerRadius + smallerRadius);

    highp vec2 startCenter = pointCoordinates(center, mediumRadius, startAngle);
    highp vec2 endCenter = pointCoordinates(center, mediumRadius, endAngle);

    return vec4(startCenter, endCenter);
}

highp vec2 pointCoordinates(highp vec2 center, highp float radius, highp float angle) {

    return vec2(center.x + radius * cos(angle), center.y - radius * sin(angle));
}

//MARK: Colors

lowp vec4 displayedOutterColor() {

    if ( usesCustomColors == YES ) {

        return outterCircleColor;
    }

    return defaultCurrentColor();
}

lowp vec4 displayedInnerColor() {

    if ( usesCustomColors == YES ) {

        return innerCircleColor;
    }

    return defaultCurrentColor();
}

lowp vec4 defaultCurrentColor() {

    highp float timeOffset = currentTimeOffset();
    highp vec4 timings = animationTimings();
    int animationState = currentAnimationState();

    if ( animationState == kAnimationStateGrowing ) {

        highp float progress = timeOffset / timings.x;
        return interpolateColors(defaultColors[0], defaultColors[1], progress);
    }
    else if ( animationState == kAnimationStateStaticBig ) {

        highp float progress = (timeOffset - timings.x) / (timings.y - timings.x);
        return interpolateColors(defaultColors[1], defaultColors[2], progress);
    }
    else if ( animationState == kAnimationStateShrinking ) {

        highp float progress = (timeOffset - timings.y) / (timings.z - timings.y);
        return interpolateColors(defaultColors[2], defaultColors[3], progress);
    }
    else {

        highp float progress = (timeOffset - timings.z) / (timings.w - timings.z);
        return interpolateColors(defaultColors[3], defaultColors[0], progress);
    }
}

lowp vec4 interpolateColors(lowp vec4 color1, lowp vec4 color2, highp float progress) {

    return vec4(interpolateValue(color1.x, color2.x, progress), interpolateValue(color1.y, color2.y, progress), interpolateValue(color1.z, color2.z, progress), interpolateValue(color1.w, color2.w, progress));
}

lowp float interpolateValue(lowp float value1, lowp float value2, highp float progress) {

    return value1 + (value2 - value1) * progress;
}


