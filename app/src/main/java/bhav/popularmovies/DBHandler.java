package bhav.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by bhav on 5/4/16 for the Popular Movies Project.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final String TAG = DBHandler.class.getSimpleName();

    private static final String DB_NAME = "PopularMoviesDatabase";
    private static final int DB_VERSION = 2;
    private static final String TABLE_NAME = "FavouriteMovies";

    //COLUMNS
    private static final String TITLE = "Title";
    private static final String POPULARITY = "Popularity";
    private static final String VOTE_COUNT = "VoteCount";
    private static final String BACKDROP_URI = "BackdropUri";
    private static final String VIDEO = "Video"; //DB <--- String <--- json
    private static final String GENRE = "Genre"; //DB <--- String <--- json
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String ADULT = "Adult";
    private static final String ID = "Id";
    private static final String OVERVIEW = "Overview";
    private static final String VOTE_AVERAGE = "AverageVotes";
    private static final String POSTER_URI = "PosterUri";
    private static final String RELEASE_DATE = "ReleaseDate";
    private static final String ORIGINAL_LANUAGE = "OriginalLanguage";

    //CREATE TABLE
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ( " + TITLE + " TEXT, " + POPULARITY + " TEXT, "
            + VOTE_COUNT + " TEXT, " + BACKDROP_URI + " TEXT, " + VIDEO + " TEXT, "
            + GENRE + " TEXT, " + ORIGINAL_TITLE + " TEXT, " + ADULT + " TEXT, "
            + ID + " TEXT, " + OVERVIEW + " TEXT, " + VOTE_AVERAGE + " TEXT, "
            + POSTER_URI + " TEXT, " + RELEASE_DATE + " TEXT, " + ORIGINAL_LANUAGE
            + " TEXT )";

    //DROP TABLE
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(TAG, "DBHandler is here!");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
        Log.d(TAG, "Table Created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean CheckIfExists(String MovieId) {
        //CHECK FOR EXISTING COLUMNS
        String REDUNDANCY_CHECK = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + ID + " = " + MovieId;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(REDUNDANCY_CHECK, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        if (db.isOpen()) {
            db.close();
        }
        return true;
    }

    public void addMovie(Movie m) {
        if (CheckIfExists(String.valueOf(m.id))) { //shouldn't happen, but just in case
            return;
        }
        JSONObject genre_json = new JSONObject();
        JSONObject video_json = new JSONObject();
        //genre_json.put(GENRE, new JSONArray(m.genre_ids));
        //video_json.put(VIDEO, new JSONArray(m.video));
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, m.title);
        cv.put(POPULARITY, m.popularity);
        cv.put(VOTE_COUNT, m.vote_count);
        cv.put(BACKDROP_URI, m.backdrop_path);
        //cv.put(VIDEO, video_json.toString());
        //cv.put(GENRE, genre_json.toString());
        cv.put(ORIGINAL_TITLE, m.original_title);
        cv.put(ADULT, m.adult);
        cv.put(ID, m.id);
        cv.put(OVERVIEW, m.overview);
        cv.put(VOTE_AVERAGE, m.vote_average);
        cv.put(POSTER_URI, m.poster_path);
        cv.put(RELEASE_DATE, m.release_date);
        cv.put(ORIGINAL_LANUAGE, m.original_language);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public void removeMovieFromFavourites(Movie m) {
        if (CheckIfExists(String.valueOf(m.id))) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(DB_NAME, ID + "=" + m.id, null);
            Log.d(TAG, m.title + " removed");
            if (db.isOpen()) {
                db.close();
            }
        }
    }

    public ArrayList<Movie> getfavourites() {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        String GET = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(GET, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(cursor.getString(11), cursor.getString(7),
                        cursor.getString(9), cursor.getString(12), cursor.getString(5),
                        cursor.getString(8), cursor.getString(6), cursor.getString(13),
                        cursor.getString(0), cursor.getString(3), cursor.getString(1),
                        cursor.getString(2), cursor.getString(4), cursor.getString(10));
                movieList.add(movie);
            } while (cursor.moveToNext());
            if (db.isOpen()) {
                cursor.close();
                db.close();
            }
        }
        return movieList;
    }
}
