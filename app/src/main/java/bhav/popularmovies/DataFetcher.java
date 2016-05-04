package bhav.popularmovies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by bhav on 4/13/16 for the Popular Movies Project.
 */
public class DataFetcher extends AsyncTask<Void, Void, String> {

    private final static String LOG_TAG = DataFetcher.class.getSimpleName();
    public static ArrayList<Movie> mMovieList = new ArrayList<Movie>();
    Context mContext;
    SharedPreferences prefs;
    ProgressDialog pdia;
    private boolean calledfromSplash;

    public DataFetcher(Context context, boolean calledFromSplash) {
        this.mContext = context;
        this.calledfromSplash = calledFromSplash;
        //prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        if (calledfromSplash) {
            SplashActivity.showSpinner(true);
        } else {
            pdia = new ProgressDialog(mContext);
            pdia.setMessage("Fetching content. Please wait...");
            pdia.show();
        }
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        mMovieList.clear();
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        try {


            Log.d(LOG_TAG, "building uri");
            Uri getUri = UriMaker.makeMeAURI(mContext);
            URL url = new URL(getUri.toString());
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            if (inputStream == null) {
                Log.d(LOG_TAG, "null input stream");
                return null;
            }
            ArrayList<ResultObject> resultArray = new ArrayList<ResultObject>();
            resultArray.add(LoganSquare.parse(inputStream, ResultObject.class));
            for (Movie m : resultArray.get(0).movieArrayList) {
                mMovieList.add(m);
            }
            Log.d(LOG_TAG, "doInBackground: finishing up");
            for (Movie movie : mMovieList) {
                Log.d("FetchData:", movie.title + movie.poster_path + movie.original_language);
            }
            MainActivity.mMovieList.addAll(mMovieList);
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, e.getMessage());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (calledfromSplash) {
            SplashActivity.showSpinner(false);
            Intent intent = new Intent(mContext, MainActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();
        } else {
            pdia.dismiss();
            Log.d(LOG_TAG, "updating ui");
            MainActivity.UpdateUI();
        }
        super.onPostExecute(result);
    }
}
