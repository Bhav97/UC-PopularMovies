package bhav.popularmovies;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by bhav on 4/13/16 for the Popular Movies Project.
 */
public class ColorFetcher {
    public static String AbColor = "";

    public static String getAverageColor(Bitmap bitmap) {
        String Abscolor = null;
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                bitmap, 1, 1, false);
        int pixel = resizedBitmap.getPixel(1, 1);
        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);
        Abscolor = String.valueOf(redValue) + String.valueOf(blueValue) + String.valueOf(greenValue);
        Log.d("cf", Abscolor);
        AbColor = Abscolor;
        return Abscolor;
    }
}
