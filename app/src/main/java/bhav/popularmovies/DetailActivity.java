package bhav.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailActivity extends AppCompatActivity {

    private final static String LOG_TAG = DetailActivity.class.getSimpleName();
    boolean isMovieFavourite;
    private Movie movie;
    private FloatingActionButton mFavouriteFab;
    private DBHandler mDBHandler;
    private ImageView mBackdropView, mPosterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mFavouriteFab = (FloatingActionButton) findViewById(R.id.fab);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("movie")) {
            movie = (Movie) intent.getParcelableExtra("movie");
        } else finish();


        mDBHandler = new DBHandler(this);
        isMovieFavourite = mDBHandler.CheckIfExists(movie.id);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPosterView = (ImageView) findViewById(R.id.mov_poster);
        mBackdropView = (ImageView) findViewById(R.id.image_stretch_detail);

        Log.d("dd",String.valueOf(Movie.getPosterUri(movie)));
        Uri posterUri = Movie.getPosterUri(movie);
        Uri backdropUri = Movie.getBackdropUri(movie);

        Log.d(LOG_TAG, movie.title + movie.original_language + movie.release_date +
                movie.vote_average + movie.overview+ movie.poster_path);

        if (posterUri != null) { //prevent crash if there was no poster
            Glide.with(getApplicationContext())
                    .load(posterUri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mPosterView);
        } else { //never empty
            Glide.with(getApplicationContext())
                    .load(R.drawable.img_placeholder)
                    .into(mPosterView);
        }

        if (backdropUri != null) { //prevevnt crash if backdrop wasn't available
            Glide.with(getApplicationContext())
                    .load(backdropUri)
                    .into(mBackdropView);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(Movie.getName(movie));
        }


        mFavouriteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMovieFavourite) {
                    //// FIXME: 5/5/16 something's not working
//                    MainActivity.mDBHandler.removeMovieFromFavourites(movie);
//                    isMovieFavourite = false;
                    Snackbar.make(v, "Removed from Favourites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                } else {
                    //// FIXME: 5/5/16 same as above
//                    MainActivity.mDBHandler.addMovie(movie);
//                    isMovieFavourite = true;
                    Snackbar.make(v, "Added from Favourites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                }

            }
        });
        TextView vote_average = (TextView) findViewById(R.id.mov_rating);
        TextView release_date = (TextView) findViewById(R.id.mov_release_year);
        TextView runtime = (TextView) findViewById(R.id.mov_runtime);
        TextView overview = (TextView) findViewById(R.id.mov_overview);
        String release_part[] = movie.release_date.split("-");
        runtime.setText("");
        vote_average.setText(String.valueOf(movie.vote_average+"/10"));
        release_date.setText(release_part[0]);
        overview.setText(movie.overview);

        //Color from Poster ---> Statusbar&actionbar
        //// TODO: 4/14/16 fix coloring method below
        /*Glide
                .with(getApplicationContext())
                .load(posterUri)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(100,100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        int color = Integer.parseInt(ColorFetcher.getAverageColor(resource), 16)+0xFF000000;
                        posterView.setImageBitmap(resource);
                    }
                });
        int color = Integer.parseInt(ColorFetcher.AbColor);
        collapsingToolbarLayout.setBackgroundColor(color);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        collapsingToolbarLayout.setContentScrimColor(color);
        getWindow().setStatusBarColor(color);
        toolbar.setBackground(new ColorDrawable(color)); // Possibly runOnUiThread()*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
