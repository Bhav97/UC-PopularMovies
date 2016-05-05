package bhav.popularmovies;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

/**
 * Created by bhav on 4/14/16 for the Popular Movies Project.
 */

@JsonObject
public class ResultObject {

    @JsonField(name = "page")
    public int page;
    @JsonField(name = "results")
    public ArrayList<Movie> movieArrayList;
    @JsonField(name = "total_results")
    public int total_results;
    @JsonField(name = "total_pages")
    public int total_pages;

    public ResultObject() {
    }

    public ResultObject(int page, ArrayList<Movie> movieArrayList, int total_results, int total_pages) {
        this.page = page;
        this.movieArrayList = movieArrayList;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }
}
