package bhav.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final static int COLUMN_COUNT = 3;
    static ArrayList<Movie> MovieList = new ArrayList<Movie>();
    private static MovieAdapter movieAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_posters);
        FetchData();

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
        }


        return super.onOptionsItemSelected(item);
    }

    public void FetchData() {
        MovieList.clear();
        DataFetcher dataFetcher = new DataFetcher(MainActivity.this);
        dataFetcher.execute();

    }

    public static void UpdateUI() {
        Log.d("f", "UpdateUI: ");
        movieAdapter.notifyDataSetChanged();
    }

}
