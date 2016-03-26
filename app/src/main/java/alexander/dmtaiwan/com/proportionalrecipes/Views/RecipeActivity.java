package alexander.dmtaiwan.com.proportionalrecipes.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

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
public class RecipeActivity extends AppCompatActivity implements RecipeView, IngredientAdapter.RecyclerClickListener{

    private String LOG_TAG = RecipeActivity.class.getSimpleName();

    private IngredientAdapter mAdapter;


    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.empty_view)
    TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);


        mAdapter = new IngredientAdapter(mEmptyView, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(mAdapter);

        if (getIntent() != null) {
            Recipe recipe = getIntent().getParcelableExtra(Utilities.EXTRA_RECIPE);
            List<Ingredient> ingredientList = recipe.getIngredientList();
            mAdapter.updateData(ingredientList);
        }

    }

    @Override
    public void onDataReturned(List<Ingredient> ingredientList) {
        mAdapter.updateData(ingredientList);
    }

    @Override
    public void onRecyclerClick(Ingredient ingredient) {

    }
}
