package bhav.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static int COLUMN_COUNT = 3;
    static ArrayList<Movie> MovieList = new ArrayList<Movie>();
    private static MovieAdapter movieAdapter;
    SharedPreferences prefs;
    private RecyclerView recyclerView;

    public static void UpdateUI() {

        movieAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_posters);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstrun", false);
            editor.apply();
            FetchData();
        }
        if (savedInstanceState == null) {
            FetchData();
        }
        movieAdapter = new MovieAdapter(MainActivity.this, MovieList);
        recyclerView.setAdapter(movieAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,COLUMN_COUNT);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_settings) {
            Intent settings = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(settings);
        } else if (id == R.id.action_faves) {
            //Intent faves = new Intent(MainActivity.this,FaveActivity.class);
            //faves.putExtra("list",);
            //startActivity(faves);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("doIneedtofetchdata", true);
        editor.apply();
        super.onDestroy();
    }

    public void FetchData() {
        MovieList.clear();
        DataFetcher dataFetcher = new DataFetcher(MainActivity.this);
        dataFetcher.execute();

    }

    @Override
    protected void onResume() {
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        if (prefs.getBoolean("doIneedtofetchdata", false)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("doIneedtofetchdata", false);
            editor.apply();
            FetchData();
        }
        super.onResume();
    }

}
