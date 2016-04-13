package bhav.popularmovies;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bhav on 4/13/16 for the Popular Movies Project.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private final static String LOG_TAG = MovieAdapter.class.getSimpleName();

    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private Context context;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myGrid = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_grid_item,
                parent, false);
        return new MovieHolder(myGrid);
    }

    @Override
    public void onBindViewHolder(final MovieHolder holder, final int position) {
        Uri posterUri = Movie.getPosterUri(movies.get(position));

        Log.d(LOG_TAG,String.valueOf(posterUri));
        Glide.with(context.getApplicationContext())
                .load(posterUri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(context , DetailActivity.class);
                details.putExtra("movie",movies.get(position));
                ActivityOptions transitionActivityOptions = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context, holder.imageView, "image");
                context.startActivity(details, transitionActivityOptions.toBundle());

            }
        });
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public MovieHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.grid_item_thumb);
        }
    }
}
