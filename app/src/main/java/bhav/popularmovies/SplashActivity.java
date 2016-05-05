package bhav.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SplashActivity extends AppCompatActivity {
    //    private TextView mAppTag;
    public static SimpleMaterialSpinner mSimpleMaterialSpinner;
    private DataFetcher mDataFetcher;

    public static void showSpinner(boolean spinnervisible) {
        if (spinnervisible) {
            mSimpleMaterialSpinner.setVisibility(View.VISIBLE);
        } else {
            mSimpleMaterialSpinner.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        //if user exits, stop AsyncTask
        if (mDataFetcher.getStatus() != AsyncTask.Status.FINISHED) {
            mDataFetcher.cancel(true);
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSimpleMaterialSpinner = (SimpleMaterialSpinner) findViewById(R.id.view);
        if (mSimpleMaterialSpinner != null) {
            mSimpleMaterialSpinner.setVisibility(View.INVISIBLE);
        }

        mDataFetcher = new DataFetcher(this, true);
        mDataFetcher.execute();

//        mAppTag = (TextView)findViewById(R.id.app_tag);
//        Typeface Poiret = Typeface.createFromAsset(this.getAssets(), "PoiretOne-Regular.ttf");
//        mAppTag.setTypeface(Poiret);
//        mAppTag.setText(R.string.app_name);
    }
}
