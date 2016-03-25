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
            Recipe recipe = new Recipe(name, null);
            recipeList.add(recipe);
        }
        listener.onResult(null);
    }

    public interface MainInteractorListener {
        void onResult(List<Recipe> recipes);
    }
}
