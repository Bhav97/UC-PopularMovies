package bhav.popularmovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

public class DetailActivity extends AppCompatActivity {

    private Movie movie;
    private final static String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("movie")) {
            movie = (Movie) intent.getParcelableExtra("movie");
        } else finish();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ImageView posterView = (ImageView) findViewById(R.id.mov_poster);
        Log.d("dd",String.valueOf(Movie.getPosterUri(movie)));
        Uri posterUri = Movie.getPosterUri(movie);

        Log.d(LOG_TAG,movie.title + movie.original_language + movie.mov_star + movie.release_date +
                movie.vote_average + movie.overview+ movie.poster_path);
        Glide.with(getApplicationContext())
                .load(posterUri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(posterView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Movie.getStar(movie)) {
                    Snackbar.make(view, "Removed from favourites", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    Movie.setStar(movie , false);
                } else {
                    Snackbar.make(view, "Added to favourites", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    Movie.setStar(movie, true);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Movie.getName(movie));
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        TextView vote_average = (TextView) findViewById(R.id.mov_rating);
        TextView release_date = (TextView) findViewById(R.id.mov_release_year);
        TextView runtim = (TextView) findViewById(R.id.mov_runtime);
        TextView overview = (TextView) findViewById(R.id.mov_overview);
        String release_part[] = movie.release_date.split("-");
        runtim.setText("");
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
}
