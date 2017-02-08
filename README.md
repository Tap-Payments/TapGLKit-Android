# TapGLKit-Android
Kit with GL views for Android

STR TO USE

1) add
allprojects {
repositories {
...
maven { url 'https://jitpack.io' }
}
}

2) add
dependencies {
compile 'com.github.User:Repo:Tag'
}

3) Add the token to $HOME/.gradle/gradle.properties
authToken=5f1ba55aad24ad32c23d89259dc1a8f2204906c8

4)Then use authToken as the username in your build.gradle:


repositories {
maven {
url "https://jitpack.io"
credentials { username authToken }
}
}



Lib give TapLoadingVIew which extends GLSurfaceView

can  be used via code and xml

#xml
<gotap.com.tapglkitandroid.gl.Views.TapLoadingView
android:layout_width="200dp"
android:layout_height="200dp"
xmlns:android="http://schemas.android.com/apk/res/android"
android:id="@+id/tapLoading"
app:customColor="#ff00ff"
app:useCustomColor="true"
android:layout_alignParentTop="true"
android:layout_centerHorizontal="true" />

#code
have 2 fields
useCustomColor -> boolesn variable hat attach to use color than can be used in next variable
                  if value 'false', use colors that attached in shader

customColor -> int variable for Color, Warning  when use programatically use Color.parse() or
                getReseource.getColor, don't work whith R.color.*


In code have 3 active methods
    start() -> startAnimation
    pause() -> pasuse animation
    stop()  -> stops animation in the end point

    fields :
    useCustomColor
    customColor
                    for both can be chanched in runtime
