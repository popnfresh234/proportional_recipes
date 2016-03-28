package alexander.dmtaiwan.com.proportionalrecipes.Utilities;

import java.util.ArrayList;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Recipe;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Alexander on 3/28/2016.
 */
public interface RecipeService {
    @GET("api/jsonBlob/{blobId}")
    Call<ArrayList<Recipe>> downloadRecipes(@Path("blobId") String blobId);

    @PUT("api/jsonBlob/{blobId}")
    Call <ArrayList<Recipe>> uploadRecipes(@Path("blobId") String blobId , @Body ArrayList<Recipe> recipes);
}
