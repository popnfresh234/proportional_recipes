package alexander.dmtaiwan.com.proportionalrecipes.Models;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by lenovo on 3/25/2016.
 */
public interface MainInteractor {
    public void fetchData();

    public void uploadData(ArrayList<Recipe> recipeList, Context context);

    public void downloadData(ArrayList<Recipe> recipeList, Context context);
}
