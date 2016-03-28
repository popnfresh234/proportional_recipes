package alexander.dmtaiwan.com.proportionalrecipes.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Ingredient;
import alexander.dmtaiwan.com.proportionalrecipes.Models.Recipe;
import alexander.dmtaiwan.com.proportionalrecipes.R;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.IngredientAdapter;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.Utilities;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 3/26/2016.
 */
public class RecipeActivity extends AppCompatActivity implements IngredientAdapter.RecyclerClickListener,IngredientAdapter.RecyclerTextChangedListener{

    private String LOG_TAG = RecipeActivity.class.getSimpleName();

    private IngredientAdapter mAdapter;
    private ArrayList<Recipe> mRecipeList;
    private int mRecipePosition;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.empty_view)
    TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        mAdapter = new IngredientAdapter(mEmptyView, this, this, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(mAdapter);

        if (getIntent() != null) {
            mRecipeList = getIntent().getParcelableArrayListExtra(Utilities.EXTRA_RECIPES);
            mRecipePosition = getIntent().getIntExtra(Utilities.EXTRA_RECIPE_POSITION, 0);
            Recipe recipe = mRecipeList.get(mRecipePosition);
            List<Ingredient> ingredientList = recipe.getIngredientList();
            mAdapter.updateData(ingredientList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Intent intent = new Intent(this, RecipeEditActivity.class);
            intent.putExtra(Utilities.EXTRA_RECIPES, mRecipeList);
            intent.putExtra(Utilities.EXTRA_RECIPE_POSITION, mRecipePosition);
            intent.putExtra(Utilities.EXTRA_NEW_RECIPE, false);
            startActivity(intent);
            finish();
        }

        if (id == R.id.action_delete) {
            mRecipeList.remove(mRecipePosition);
            String jsonString = new Gson().toJson(mRecipeList);
            Utilities.writeToFile(jsonString, this);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecyclerClick(Ingredient ingredient) {

    }

    @Override
    public void onRecyclerTextChanged(double enteredValue, int position) {
        List<Ingredient> ingredientList = mAdapter.getIngredients();
        Ingredient testIngredient = ingredientList.get(position);
        double ratio = enteredValue / testIngredient.getCount();
        for (int i = 0; i < ingredientList.size(); i++) {
            Ingredient ingredient = ingredientList.get(i);
            if (i != position) {
                ingredient.setProportionalCount(ingredient.getCount() * ratio);
                ingredientList.set(i, ingredient);
            }else {
                ingredient.setProportionalCount(enteredValue);
                ingredientList.set(i, ingredient);
            }
        }
        mAdapter.updateData(ingredientList);
    }
}
