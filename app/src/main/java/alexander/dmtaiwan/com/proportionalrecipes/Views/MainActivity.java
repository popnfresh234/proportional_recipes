package alexander.dmtaiwan.com.proportionalrecipes.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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


public class MainActivity extends AppCompatActivity implements MainView, RecipeAdapter.RecyclerClickListener, View.OnClickListener {

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

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbar_progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mFab.setOnClickListener(this);


        mAdapter = new RecipeAdapter(mEmptyView, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter == null) {
            mPresenter = new MainPresenterImpl(this, this);
        }
        mPresenter.fetchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        mProgressBar.setVisibility(View.VISIBLE);

        if (id == R.id.action_sync) {
            mPresenter.uploadData(mRecipeList, this);

        }

//        if (id == R.id.action_download) {
//            mPresenter.downloadData(mRecipeList, this);
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataReturned(ArrayList<Recipe> recipeList) {
        Log.i(LOG_TAG, String.valueOf(recipeList.size()));
        mAdapter.updateData(recipeList);
        mRecipeList = recipeList;
    }

    @Override
    public void onNetworkTransferCompleted(ArrayList<Recipe> recipeList) {

    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void makeSnackbar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
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
        intent.putParcelableArrayListExtra(Utilities.EXTRA_RECIPES, mRecipeList);
        intent.putExtra(Utilities.EXTRA_NEW_RECIPE, true);
        startActivity(intent);
    }
}
