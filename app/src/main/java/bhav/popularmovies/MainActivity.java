package bhav.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static int COLUMN_COUNT = 3;
    static ArrayList<Movie> mMovieList = new ArrayList<Movie>();
    static DBHandler mDBHandler;
    private static MovieAdapter movieAdapter;
    private RecyclerView mRecyclerView;

    public static void UpdateUI() {
        movieAdapter.notifyDataSetChanged();
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        for (int i = 0; i < mMovieList.size(); i++) {
//            //remove the blank movie when total number of movies are not a multiple of columns
//            // I don't know why I have to do this
//            //// FIXME: 5/4/16 up
//            if (mMovieList.get(i) != null) {
//                outState.putParcelable(String.valueOf(i), mMovieList.get(i));
//            }
//        }
//        Log.d(TAG, "MovieList saved to instance bundle!" + String.valueOf(outState.size()));
//        mMovieList.clear();
//        super.onSaveInstanceState(outState);
//    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        ArrayList<Movie> movies = new ArrayList<Movie>();
//        for (int i = 0; i < savedInstanceState.size(); i++) {
//            //same as what I did in {@link onSaveInstanceState } , just paranoid
//            if ((Movie) savedInstanceState.get(String.valueOf(i)) != null) {
//                movies.add(i, (Movie) savedInstanceState.get(String.valueOf(i)));
//            }
//        }
//        if(mMovieList != movies) {
//            UpdateUI();
//        }
//        Log.d(TAG, String.valueOf(mMovieList.size()));
//        super.onRestoreInstanceState(savedInstanceState);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_posters);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
        mDBHandler = new DBHandler(this);

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
            mMovieList.addAll(mDBHandler.getfavourites());
            UpdateUI();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onSaveInstanceState(new Bundle());
        super.onStop();
    }

    @Override
    protected void onResume() {
        UpdateUI();
        super.onResume();
    }

}
