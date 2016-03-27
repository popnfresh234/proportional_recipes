package alexander.dmtaiwan.com.proportionalrecipes.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Recipe;
import alexander.dmtaiwan.com.proportionalrecipes.Presenters.MainPresenter;
import alexander.dmtaiwan.com.proportionalrecipes.Presenters.MainPresenterImpl;
import alexander.dmtaiwan.com.proportionalrecipes.R;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.RecipeAdapter;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.Utilities;
import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MainView, RecipeAdapter.RecyclerClickListener, View.OnClickListener{

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private RecipeAdapter mAdapter;
    private MainPresenter mPresenter;
    private ArrayList<Recipe> mRecipeList;


    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.empty_view)
    TextView mEmptyView;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFab.setOnClickListener(this);


        mAdapter = new RecipeAdapter(mEmptyView, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(mAdapter);

        if (mPresenter == null) {
            mPresenter = new MainPresenterImpl(this, this);
        }
        mPresenter.fetchData();
    }

    @Override
    public void onDataReturned(ArrayList<Recipe> recipeList) {
        mAdapter.updateData(recipeList);
        mRecipeList = recipeList;
    }

    @Override
    public void onRecyclerClick(int position) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putParcelableArrayListExtra(Utilities.EXTRA_RECIPES, mRecipeList);
        intent.putExtra(Utilities.EXTRA_RECIPE_POSITION, position);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, RecipeEditActivity.class);
//        intent.putExtra(Utilities.EXTRA_RECIPES, mRecipeList.get(0));
        startActivity(intent);
    }
}
