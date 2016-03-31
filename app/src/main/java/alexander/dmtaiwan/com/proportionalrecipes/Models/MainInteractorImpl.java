package alexander.dmtaiwan.com.proportionalrecipes.Models;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

import alexander.dmtaiwan.com.proportionalrecipes.R;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.RecipeService;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    @Override
    public void uploadData(ArrayList<Recipe> recipeList, final Context context) {
        listener.showLoading();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService service = retrofit.create(RecipeService.class);
        Call<ArrayList<Recipe>> upload = service.uploadRecipes(Utilities.JSON_ID, recipeList);
        upload.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, retrofit2.Response<ArrayList<Recipe>> response) {
                listener.hideLoading();
                listener.makeSnackBar(context.getString(R.string.upload_success));
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                listener.hideLoading();
                listener.makeSnackBar(context.getString(R.string.upload_failure));
            }
        });
    }

    @Override
    public void downloadData(ArrayList<Recipe> recipeList, final Context context) {
        listener.showLoading();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService service = retrofit.create(RecipeService.class);
        Call<ArrayList<Recipe>> download = service.downloadRecipes(Utilities.JSON_ID);
        download.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, retrofit2.Response<ArrayList<Recipe>> response) {
                listener.hideLoading();
                listener.makeSnackBar(context.getString(R.string.download_success));

                ArrayList<Recipe> recipeList = response.body();
                String json = new Gson().toJson(recipeList);
                Utilities.writeToFile(json,context);
                listener.onResult(recipeList);

            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                listener.makeSnackBar(context.getString(R.string.download_failure));
            }
        });
    }

    public interface MainInteractorListener {
        void onResult(ArrayList<Recipe> recipes);

        void onNetworkTransferComplete(ArrayList<Recipe> recipes);

        void showLoading();

        void hideLoading();

        void makeSnackBar(String message);
    }
}
