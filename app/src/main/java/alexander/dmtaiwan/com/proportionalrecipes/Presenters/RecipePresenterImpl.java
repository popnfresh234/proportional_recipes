package alexander.dmtaiwan.com.proportionalrecipes.Presenters;

import android.content.Context;

import java.util.List;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Ingredient;
import alexander.dmtaiwan.com.proportionalrecipes.Models.RecipeInteractor;
import alexander.dmtaiwan.com.proportionalrecipes.Models.RecipeInteractorImpl;
import alexander.dmtaiwan.com.proportionalrecipes.Views.RecipeView;

/**
 * Created by Alexander on 3/26/2016.
 */
public class RecipePresenterImpl implements RecipePresenter, RecipeInteractorImpl.RecipeInteractorListener{

    private RecipeView recipeView;
    private RecipeInteractor recipeInteractor;
    private Context context;

    public RecipePresenterImpl(RecipeView recipeView, Context context) {
        this.recipeView= recipeView;
        this.context = context;
        this.recipeInteractor = new RecipeInteractorImpl(this, context);
    }

    @Override
    public void fetchData() {
        recipeInteractor.fetchData();
    }

    @Override
    public void onResult(List<Ingredient> ingredients) {
        recipeView.onDataReturned(ingredients);
    }
}
