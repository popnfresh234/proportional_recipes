package alexander.dmtaiwan.com.proportionalrecipes.Utilities;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Recipe;

/**
 * Created by Alexander on 3/26/2016.
 */
public class Utilities {
    public static final String EXTRA_RECIPES = "com.dmtaiwan.alexander.extra.recipe";
    public static final String EXTRA_RECIPE_POSITION = "com.dmtaiwan.alexander.extra.position";
    public static final String EXTRA_NEW_RECIPE = "com.dmtaiwan.alexander.extra.newrecipe";
    public static final String FILE_NAME_RECIPES = "recipes.json";
    public static final String FILE_NAME_DELETED = "deleted.json";
    public static final String BASE_URL = "https://jsonblob.com/";
    public static final String JSON_ID = "56f8e7e8e4b01190df592763";

    static public boolean doesRecipeFileExist(Context context) {
        File file = context.getFileStreamPath(FILE_NAME_RECIPES);
        return file.exists();
    }

    public static void writeRecipesToFile(String json, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Utilities.FILE_NAME_RECIPES, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readRecipesFromFile(Context context) {
        String json = "";
        try {
            InputStream inputStream = context.openFileInput(Utilities.FILE_NAME_RECIPES);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                json = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    static public boolean doesDeletedRecipesFileExist(Context context) {
        File file = context.getFileStreamPath(FILE_NAME_DELETED);
        return file.exists();
    }

    public static void writeDeletedRecipesToFile(String json, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Utilities.FILE_NAME_DELETED, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readDeletedRecipesFromFile(Context context) {
        String json = "";
        try {
            InputStream inputStream = context.openFileInput(Utilities.FILE_NAME_DELETED);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                json = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static ArrayList<Recipe> recipesFromJson(String json) {
        Type type = new TypeToken<ArrayList<Recipe>>(){}.getType();
        ArrayList<Recipe> recipeList = new Gson().fromJson(json, type);
        return recipeList;
    }

    public static int getRandomInt(Application application) {
        return ((ProportionalRecipes)application).getRandom().nextInt(9999999 - 0+1)+0;
    }

    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


}
