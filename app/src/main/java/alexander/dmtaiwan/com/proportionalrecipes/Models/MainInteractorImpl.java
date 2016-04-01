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

    private void validateData(ArrayList<Recipe> localList, ArrayList<Recipe> remoteList) {
        final ArrayList<Recipe> editCheckedRemoteList = new ArrayList<>();
        //Populate new recipe list for upload
        for (Recipe recipe : remoteList) {
            editCheckedRemoteList.add(recipe);
        }

        //Check for edits here
        //Check for matches, if match check for difference in mod, if different edit has been done, replace in array
        for (int i = 0; i < remoteList.size(); i++) {
            Recipe remoteRecipe = remoteList.get(i);
            for (int j = 0; j < localList.size(); j++) {
                Recipe localRecipe = localList.get(j);
                if (localRecipe.getId() == remoteRecipe.getId()) {
                    if (localRecipe.getTime() != remoteRecipe.getTime()) {
                        editCheckedRemoteList.set(i, localRecipe);
                    }
                }
            }
        }


        //Loop through remote list and check for non matching IDs, these must be new
        ArrayList<Recipe> newCheckedRemoteList = new ArrayList<>();
        for (Recipe recipe : editCheckedRemoteList) {
            newCheckedRemoteList.add(recipe);
        }

        for (int i = 0; i < localList.size(); i++) {
            Recipe localRecipe = localList.get(i);
            int match = 0;
            for (int j = 0; j < editCheckedRemoteList.size(); j++) {
                Recipe remoteRecipe = editCheckedRemoteList.get(j);
                if (remoteRecipe.getId() == localRecipe.getId()) {
                    match++;
                }
            }
            if (match == 0) {
                newCheckedRemoteList.add(localRecipe);
            }
        }
        //Loop through remote list and check for non matching IDs, if not matched, remove
        final ArrayList<Recipe> deleteCheckedRemoteList = new ArrayList<>();
        for (Recipe recipe : newCheckedRemoteList) {
            deleteCheckedRemoteList.add(recipe);
        }

        for (int i = 0; i < newCheckedRemoteList.size(); i++) {
            Recipe remoteRecipe = newCheckedRemoteList.get(i);
            int match = 0;
            for (int j = 0; j < localList.size(); j++) {
                Recipe localRecipe = localList.get(j);
                if (remoteRecipe.getId() == localRecipe.getId()) {
                    match++;
                }
            }
            if (match == 0) {
                deleteCheckedRemoteList.remove(i);
            }
        }


        //Upload Data
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService service = retrofit.create(RecipeService.class);
        Call<ArrayList<Recipe>> upload = service.uploadRecipes(Utilities.JSON_ID, deleteCheckedRemoteList);
        upload.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, retrofit2.Response<ArrayList<Recipe>> response) {
                listener.hideLoading();
                listener.makeSnackBar(context.getString(R.string.upload_success));
                listener.onResult(deleteCheckedRemoteList);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                listener.hideLoading();
                listener.makeSnackBar(context.getString(R.string.upload_failure));
                listener.onResult(deleteCheckedRemoteList);
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
