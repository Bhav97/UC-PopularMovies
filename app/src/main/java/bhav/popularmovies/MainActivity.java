package bhav.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static int COLUMN_COUNT = 3;
    private final static String MOVIE_ARRAYLIST_BUNDLE_TAG = "movie_list";
    static ArrayList<Movie> mMovieList = new ArrayList<Movie>();
    private static MovieAdapter movieAdapter;
    private SharedPreferences prefs;
    private DataFetcher dataFetcher = new DataFetcher(MainActivity.this, false);
    private RecyclerView mRecyclerView;
    private DBHandler dbHandler;

    public static void UpdateUI() {
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        outState.putParcelableArrayList(MOVIE_ARRAYLIST_BUNDLE_TAG, mMovieList);
        for (int i = 0; i < mMovieList.size(); i++) {
            //remove the blank movie when total movies are not a multiple of columns
            if (mMovieList.get(i) != null) {
                outState.putParcelable(String.valueOf(i), mMovieList.get(i));
            }
        }
        mMovieList.clear();
        Log.d(TAG, "MovieList Saved!");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //RESTORE INSTANCE
        ArrayList<Movie> movies = new ArrayList<Movie>();
        if (savedInstanceState == null) {
            Log.d(TAG, "No instance to restore");
            FetchData();
        } else {
            for (int i = 0; i < savedInstanceState.size(); i++) {
                //same as what I did in saveinstance, just paranoid
                if ((Movie) savedInstanceState.get(String.valueOf(i)) != null) {
                    movies.add(i, (Movie) savedInstanceState.get(String.valueOf(i)));
                }
            }
//            mMovieList = savedInstanceState.getParcelableArrayList(MOVIE_ARRAYLIST_BUNDLE_TAG);
//            assert mMovieList != null;
//            Log.d(TAG, "List restored!" + String.valueOf(mMovieList.size()));
            if (mMovieList.size() == 0) {
                mMovieList.addAll(movies);
            }
            UpdateUI();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_posters);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
        dbHandler = new DBHandler(this);

        //END


        if (mMovieList == null) {
            FetchData();
            Log.d(TAG, "onCreate: null");
        }
//        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
//        if (prefs.getBoolean("first_run", true)) {
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean("first_run", false);
//            editor.apply();
//            FetchData();
//        }


        movieAdapter = new MovieAdapter(MainActivity.this, mMovieList);
        mRecyclerView.setAdapter(movieAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, COLUMN_COUNT);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settings);
        } else if (id == R.id.action_fav) {
            if (mMovieList != null) {
                mMovieList.clear();
            }
            mMovieList.addAll(dbHandler.getfavourites());
            UpdateUI();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (dataFetcher.getStatus() != AsyncTask.Status.FINISHED) {
            dataFetcher.pdia.dismiss();
            dataFetcher.cancel(true);
        }
        if (mMovieList != null) {
            mMovieList.clear();
            Log.d(TAG, "movielist cleared");
        }
        super.onDestroy();
    }

    public void FetchData() {
        if (mMovieList != null) {
            mMovieList.clear();
        }
        dataFetcher.execute();

    }

    @Override
    protected void onResume() {
        if (mMovieList.size() == 0 && dataFetcher.getStatus() == AsyncTask.Status.FINISHED) { //fill empty list
            FetchData();
        }
        super.onResume();
    }

}
