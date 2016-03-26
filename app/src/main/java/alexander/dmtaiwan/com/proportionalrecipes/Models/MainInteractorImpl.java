package alexander.dmtaiwan.com.proportionalrecipes.Models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

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
        List<Recipe> recipeList = new ArrayList<Recipe>();
        for (int i = 0; i < 5; i++) {
            String name = "Recipe " + String.valueOf(i);
            ArrayList<Ingredient> ingredientList = new ArrayList<>();
            for (int j = 1; j < 10; j++) {
                String ingredientName = "Ingredient " + String.valueOf(j);
                Ingredient ingredient = new Ingredient(ingredientName, j + 10);
                ingredientList.add(ingredient);
            }
            Recipe recipe = new Recipe(name, ingredientList);
            recipeList.add(recipe);
        }
        listener.onResult(recipeList);
    }

    public interface MainInteractorListener {
        void onResult(List<Recipe> recipes);
    }
}
