package bhav.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by bhav on 4/13/16 for the Popular Movies Project.
 */
public class DataFetcher extends AsyncTask<Void, Void, String> {

    public static ArrayList<Movie> mMovieList = new ArrayList<Movie>();

    Context mContext;
    SharedPreferences prefs ;
    private String sort;

    public DataFetcher(Context context){
        this.mContext = context;
        prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
    }

    private final static String LOG_TAG = DataFetcher.class.getSimpleName();

    @Override
    protected void onPreExecute() {
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
            Uri.Builder builder = new Uri.Builder()
                    .scheme("https")
                    .authority("api.themoviedb.org")
                    .appendPath("3");
            if(prefs.getString("sort", "Popular").equals("Popular")){
                builder.appendPath("movie").appendPath("popular");
            } else {
                builder.appendPath("discover").appendPath("movie").appendQueryParameter("sort_by","vote_average.desc");
            }
            Uri getUri  = builder.
                    appendQueryParameter("api_key",>>>>>>>>>>ADD YOUR OWN API<<<<<<<<<<<<<<< ).build();
            Log.d(LOG_TAG, "api uri built");
            Log.d(LOG_TAG, String.valueOf(getUri));


            URL url = new URL(getUri.toString());
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            if(inputStream == null) {
                Log.d(LOG_TAG, "null input stream");
                return null;
            }
            ArrayList<ResultObject> resultArray = new ArrayList<ResultObject>();
            resultArray.add(LoganSquare.parse(inputStream,ResultObject.class));
            int i =0;
            for(Movie m: resultArray.get(0).movieArrayList) {
                mMovieList.add(resultArray.get(0).movieArrayList.get(i));
                i+=1;
            }
            Log.d(LOG_TAG, "doInBackground: finishing up");
            for(Movie movie: mMovieList){
                Log.d("FetchData:",movie.title + movie.poster_path + movie.original_language);
            }
            MainActivity.MovieList.addAll(mMovieList);
            return null;
        }catch (MalformedURLException e) {
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
        Log.d(LOG_TAG, "updating ui");
        MainActivity.UpdateUI();
        super.onPostExecute(result);
    }
}
