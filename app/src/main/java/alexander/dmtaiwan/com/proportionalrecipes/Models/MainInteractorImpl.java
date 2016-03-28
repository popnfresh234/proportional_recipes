package alexander.dmtaiwan.com.proportionalrecipes.Models;

import android.content.Context;

import java.util.ArrayList;

import alexander.dmtaiwan.com.proportionalrecipes.Utilities.Utilities;

/**
 * Created by lenovo on 3/25/2016.
 */
public class MainInteractorImpl implements MainInteractor{

    private MainInteractorListener listener;
    private Context context;

    public MainInteractorImpl(MainInteractorListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void fetchData() {
//        Dummy Data
//        ArrayList<Recipe> downloadRecipes = new ArrayList<Recipe>();
//        for (int i = 0; i < 5; i++) {
//            String name = "Recipe " + String.valueOf(i);
//            ArrayList<Ingredient> ingredientList = new ArrayList<>();
//            for (int j = 1; j < 10; j++) {
//                String ingredientName = "Ingredient " + String.valueOf(j);
//                Ingredient ingredient = new Ingredient(ingredientName, j + 10);
//                ingredientList.add(ingredient);
//            }
//            Recipe recipe = new Recipe(name, ingredientList);
//            downloadRecipes.add(recipe);
//
//        }
//        Log.i("TEST", String.valueOf(downloadRecipes.size()));
//                listener.onResult(downloadRecipes);

        if (Utilities.doesFileExist(context)) {
            String jsonRecipeList = Utilities.readFromFile(context);
            ArrayList<Recipe> recipeList = Utilities.recipesFromJson(jsonRecipeList);
            listener.onResult(recipeList);
        }
        else {
            listener.onResult(new ArrayList<Recipe>());
        }

    }

    public interface MainInteractorListener {
        void onResult(ArrayList<Recipe> recipes);
    }
}
