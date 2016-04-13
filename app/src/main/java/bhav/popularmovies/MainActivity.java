package bhav.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataFetcher dataFetcher = new DataFetcher();
    private RecyclerView recyclerView;
    private final static int COLUMN_COUNT = 3;
    ArrayList<Movie> MovieList = new ArrayList<Movie>();
    private MovieAdapter movieAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_posters);
        MovieList = dataFetcher.mMovieList;
        MovieList.add(new Movie("Fight Club", getString(R.string.sample_desc)
                , "8.8/10", "nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "true", "2010","120 mins"));
        MovieList.add(new Movie("Fight Club", getString(R.string.sample_desc)
                , "8.8/10", "nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "true", "2010","120 mins"));
        MovieList.add(new Movie("Fight Club", getString(R.string.sample_desc)
                , "8.8/10", "nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "true", "2010","120 mins"));

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
        if(id==R.id.action_refresh) {
            FetchData();
        }


        return super.onOptionsItemSelected(item);
    }

    public void FetchData() {
        dataFetcher.execute();
        movieAdapter.notifyDataSetChanged();
    }

}
