package bhav.popularmovies;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


/**
 * Created by bhav on 4/13/16 for the Popular Movies Project.
 */

@JsonObject
public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @JsonField(name = "vote_count")
    public String vote_count;
    @JsonField(name = "backdrop_path")
    public String backdrop_path;
    @JsonField(name = "popularity")
    public String popularity;
    @JsonField(name = "video")
    public String video;
    @JsonField(name = "genre_ids")
    public String genre_ids;
    @JsonField(name = "original_title")
    public String original_title;
    @JsonField(name = "adult")
    public String adult;
    @JsonField(name = "id")
    public String id;
    @JsonField(name = "title")
    public String title;
    @JsonField(name = "overview")
    public String overview;
    @JsonField(name = "vote_average")
    public String vote_average;
    @JsonField(name = "poster_path")
    public String poster_path;
    @JsonField(name = "release_date")
    public String release_date;
    @JsonField(name = "original_language")
    public String original_language;

    public Movie() {
    }

    public Movie(String poster_path, String adult, String overview, String release_date,
                 String genre_ids, String id, String original_title, String original_language,
                 String title, String backdrop_path, String popularity, String vote_count, String video,
                 String vote_average) {
        this.poster_path = poster_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.id = id;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.vote_average = vote_average;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        overview = in.readString();
        vote_average = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        original_language = in.readString();
        backdrop_path = in.readString();
    }

    public static String getName(Movie movie) {
        return movie.title;
    }

    public static String getOverview(Movie movie) {
        return movie.overview;
    }

    public static String getRating(Movie movie) {
        return movie.vote_average;
    }

    public static Uri getPosterUri(Movie movie) {
        Uri posterUri = null;
        if (movie.poster_path != null) {
            posterUri = new Uri.Builder()
                    .scheme("https")
                    .authority("image.tmdb.org")
                    .appendPath("t")
                    .appendPath("p")
                    .appendPath("w185")
                    .appendPath(movie.poster_path.replace("/", ""))
                    .build();
        }
        return posterUri;
    }

    public static Uri getBackdropUri(Movie movie) {
        Uri bgUri = null;
        if (movie.backdrop_path != null) {
            bgUri = new Uri.Builder()
                    .scheme("https")
                    .authority("image.tmdb.org").appendPath("t")
                    .appendPath("p")
                    .appendPath("w342")
                    .appendPath(movie.backdrop_path.replace("/", ""))
                    .build();
        }
        return bgUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(vote_average);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeString(original_language);
        dest.writeString(backdrop_path);

    }
}
