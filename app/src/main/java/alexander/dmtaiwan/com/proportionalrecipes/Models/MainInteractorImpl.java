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
public class MainInteractorImpl implements MainInteractor {

    private MainInteractorListener listener;
    private Context context;

    public MainInteractorImpl(MainInteractorListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void fetchData() {

        if (Utilities.doesRecipeFileExist(context)) {
            String jsonRecipeList = Utilities.readRecipesFromFile(context);
            ArrayList<Recipe> recipeList = Utilities.recipesFromJson(jsonRecipeList);
            listener.onResult(recipeList);
        } else {
            listener.onResult(new ArrayList<Recipe>());
        }

    }

    @Override
    public void uploadData(final ArrayList<Recipe> recipeList, final Context context) {
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
                validateData(recipeList, response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                listener.makeSnackBar(context.getString(R.string.download_failure));
            }
        });
    }

    private void validateData(ArrayList<Recipe> localList, final ArrayList<Recipe> remoteList) {

        //Step 1 Remove any deleted recipes from remoteList;
        ArrayList<Recipe> editableRemote = new ArrayList<>();
        for (Recipe remoteRecipe : remoteList) {
            editableRemote.add(remoteRecipe);
        }

        if (Utilities.doesDeletedRecipesFileExist(context)) {
            ArrayList<Recipe> deletedRecipes = Utilities.recipesFromJson(Utilities.readDeletedRecipesFromFile(context));
            for (Recipe deletedRecipe : deletedRecipes) {
                for (Recipe remoteRecipe : editableRemote) {
                    if (deletedRecipe.getId() == remoteRecipe.getId()) {
                        int pos = remoteList.indexOf(remoteRecipe);
                        remoteList.remove(pos);
                    }
                }
            }
            //zero out deleted Recipes
            ArrayList<Recipe> emptyArray = new ArrayList<>();
            String json = new Gson().toJson(emptyArray);
            Utilities.writeDeletedRecipesToFile(json, context);
        }

        //Step 2 Replace any edited recipes in remoteList;
        for (Recipe remoteRecipe : remoteList) {
            for (Recipe localRecipe : localList) {
                if (localRecipe.getId() == remoteRecipe.getId()) {
                    if (localRecipe.getTime() > remoteRecipe.getTime()) {
                        int pos = remoteList.indexOf(remoteRecipe);
                        remoteList.set(pos, localRecipe);
                    }
                }
            }
        }


        //Step 3 Add any new recipes to remoteList;
        ArrayList<Recipe> editableRemoteList = new ArrayList<>();
        for (Recipe remoteRecipe : remoteList) {
            editableRemoteList.add(remoteRecipe);
        }

        for (Recipe localRecipe : localList) {
            int match = 0;
            for (Recipe remoteRecipe : editableRemoteList) {
                if (localRecipe.getId() == remoteRecipe.getId()) {
                    match++;
                }
            }
            if (match == 0) {
                remoteList.add(localRecipe);
            }
        }

        ArrayList<Recipe> referenceLocalList = new ArrayList<>();
        for (Recipe localRecipe : localList) {
            referenceLocalList.add(localRecipe);
        }


        //Upload Data
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService service = retrofit.create(RecipeService.class);
        Call<ArrayList<Recipe>> upload = service.uploadRecipes(Utilities.JSON_ID, remoteList);
        upload.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, retrofit2.Response<ArrayList<Recipe>> response) {
                listener.hideLoading();
                listener.makeSnackBar(context.getString(R.string.upload_success));
                listener.onResult(remoteList);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                listener.hideLoading();
                listener.makeSnackBar(context.getString(R.string.upload_failure));
                listener.onResult(remoteList);
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
                Utilities.writeRecipesToFile(json, context);
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
