package alexander.dmtaiwan.com.proportionalrecipes.Models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 3/26/2016.
 */
public class RecipeInteractorImpl implements RecipeInteractor{

    private RecipeInteractorListener listener;
    private Context context;

    public RecipeInteractorImpl(RecipeInteractorListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void fetchData() {
        List<Recipe> recipeList = new ArrayList<Recipe>();
        for (int i = 0; i < 5; i++) {
            String name = "Recipe " + String.valueOf(i);
            Recipe recipe = new Recipe(name, null);
            recipeList.add(recipe);
        }
        listener.onResult(null);
    }

    public interface RecipeInteractorListener {
        void onResult(List<Ingredient> ingredients);
    }
}
