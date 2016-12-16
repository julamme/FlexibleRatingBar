package juhanilammi.com.flexibleratingbardemo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import juhanilammi.com.flexibleratingbar.FlexibleRatingBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlexibleRatingBar bar = (FlexibleRatingBar) findViewById(R.id.flexible);
        FlexibleRatingBar bar2 = (FlexibleRatingBar) findViewById(R.id.flexible_2);
        //With default drawables
        bar.setNumberOfPoints(7);
        bar.initializePoints(this);
        bar.setRating(2.7f, FlexibleRatingBar.MODE_HALVES);

        //With own drawables
        bar2.setNumberOfPoints(5);
        bar2.initializePoints(this);
        bar2.setPointDrawables(
                ContextCompat.getDrawable(this, R.drawable.ic_hourglass_full_black_36dp),
                null,
                ContextCompat.getDrawable(this, R.drawable.ic_hourglass_empty_black_36dp));
        bar2.setRating(4, FlexibleRatingBar.MODE_FULL);
    }
}
