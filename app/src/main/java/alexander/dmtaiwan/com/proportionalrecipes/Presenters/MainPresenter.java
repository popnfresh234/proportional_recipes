package alexander.dmtaiwan.com.proportionalrecipes.Presenters;

import android.content.Context;

import java.util.ArrayList;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Recipe;

/**
 * Created by lenovo on 3/25/2016.
 */
public interface MainPresenter {
    public void fetchData();

    public void uploadData(ArrayList<Recipe> recipeList, Context context);

    public void downloadData(ArrayList<Recipe> recipeList, Context context);
}
