package alexander.dmtaiwan.com.proportionalrecipes.Views;

import java.util.ArrayList;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Recipe;

/**
 * Created by lenovo on 3/25/2016.
 */
public interface MainView {
    public void onDataReturned(ArrayList<Recipe> recipeList);
}
