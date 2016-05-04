package bhav.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

/**
 * Created by bhav on 4/15/16 for the Popular Movies Project.
 */
public class UriMaker {
    static SharedPreferences prefs;

    public static Uri makeMeAURI(Context context) {
        prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3");
        if (prefs.getString("sort", "Most Popular").equals("Most Popular")) {
            builder.appendPath("movie").appendPath("popular");
            Log.d("makeMeAURI: ", prefs.getString("sort", "Most Popular"));
        } else {
            builder.appendPath("movie").appendPath("top_rated");
        }
        return builder.
                appendQueryParameter("api_key", "a3c49b02d993096b9e38cecc350245c3").build();
    }
}
