package alexander.dmtaiwan.com.proportionalrecipes.Utilities;

import android.content.Context;

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
    public static final String FILE_NAME = "recipes.json";

    static public boolean doesFileExist(Context context) {
        File file = context.getFileStreamPath(FILE_NAME);
        return file.exists();
    }

    public static void writeToFile(String json, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Utilities.FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(Context context) {
        String json = "";
        try {
            InputStream inputStream = context.openFileInput(Utilities.FILE_NAME);
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
}
