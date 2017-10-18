//package gotap.com.tapglkitandroid.gl.Views;
//
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//
//import gotap.com.tapglkitandroid.gl.R;
//
//public class MainActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        final TapLoadingView view = (TapLoadingView)findViewById(R.id.tapLoading);
//
//        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                view.start();
//            }
//        });
//        findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                view.pause();
//            }
//        });
//        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                view.setForceStop(true);
//            }
//        });
//        findViewById(R.id.changeColor).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                view.useCustomColor = !view.useCustomColor;
//            }
//        });
//
//        final EditText percentInput = (EditText) findViewById(R.id.percentInput);
//
//        findViewById(R.id.percentButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                float percent = Float.parseFloat(percentInput.getText().toString())/100;
//                view.setPercent(percent);
//            }
//        });
//    }
//
//}

