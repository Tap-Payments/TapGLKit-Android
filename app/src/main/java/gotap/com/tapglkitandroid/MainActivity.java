package gotap.com.tapglkitandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout main = (RelativeLayout)findViewById(R.id.activity_main);

        main.addView(new TapViewSurface(this));
    }
}
