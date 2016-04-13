package bhav.popularmovies;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bhav on 4/13/16 for the Popular Movies Project.
 */

public class Movie implements Parcelable{

    public String mov_name;
    public String mov_desc;
    public String mov_rating;
    public String mov_poster_path;
    public String mov_release_year;
    public String mov_runtime;
    //public String[] mov_reveiw;
    //public String[] mov_trailor;
    public String mov_star;

    public Movie(String name , String desc , String rating , String poster_path , String star ,
                 String release_year , String runtime ) {
        this.mov_name = name;
        this.mov_desc = desc;
        this.mov_rating = rating;
        this.mov_poster_path = poster_path;
        this.mov_star = star;
        this.mov_release_year = release_year;
        this.mov_runtime = runtime;
    }

    public static String getName(Movie movie) {
        return movie.mov_name;
    }

    public static String getDesc(Movie movie) {
        return movie.mov_desc;
    }
    public static String getRating(Movie movie) {
        return movie.mov_rating;
    }
    public static Uri getPosterUri(Movie movie) {
        Uri posterUri = new Uri.Builder()
                .scheme("https")
                .authority("image.tmdb.org")//t/p/w185/")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendPath(movie.mov_poster_path)
                .build();
        return posterUri;
    }
    public static boolean getStar(Movie movie) {
        if(movie.mov_star.equals("true")) {
            return true;
        } else return false;
    }

    public static void setStar(Movie movie, boolean state) {
        if(state) {
            movie.mov_star = String.valueOf(true);
        } else {
            movie.mov_star = String.valueOf(false);
        }
    }


    protected Movie(Parcel in) {
        mov_name = in.readString();
        mov_desc = in.readString();
        mov_rating = in.readString();
        mov_poster_path = in.readString();
        //mov_reveiw = in.createStringArray();
        //mov_trailor = in.createStringArray();
        mov_star = in.readString();
        mov_release_year = in.readString();
        mov_runtime = in.readString();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mov_name);
        dest.writeString(mov_desc);
        dest.writeString(mov_rating);
        dest.writeString(mov_poster_path);
        dest.writeString(mov_star);
        dest.writeString(mov_release_year);
        dest.writeString(mov_runtime);

    }
}
