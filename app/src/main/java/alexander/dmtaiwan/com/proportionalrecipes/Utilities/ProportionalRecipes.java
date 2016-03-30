package alexander.dmtaiwan.com.proportionalrecipes.Utilities;

import android.app.Application;

import java.util.Random;

/**
 * Created by lenovo on 3/30/2016.
 */
public class ProportionalRecipes extends Application {
    public static Random mRandom;

    public static Random getRandom() {
        if (mRandom != null) {
            return mRandom;
        }else {
            mRandom = new Random();
            return mRandom;
        }
    }
}
