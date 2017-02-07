package gotap.com.tapglkitandroid.gl.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gotap.com.tapglkitandroid.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TapLoadingView view = (TapLoadingView)findViewById(R.id.tapLoading);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.start();
            }
        });
        findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.pause();
            }
        });
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.forceStop = !view.forceStop;
            }
        });
        findViewById(R.id.changeColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.useCustomColor = !view.useCustomColor;
            }
        });

    }

}

