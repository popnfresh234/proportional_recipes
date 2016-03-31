package alexander.dmtaiwan.com.proportionalrecipes.Views;

import android.content.Context;
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

import com.google.gson.Gson;

import java.util.ArrayList;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Recipe;
import alexander.dmtaiwan.com.proportionalrecipes.Presenters.MainPresenter;
import alexander.dmtaiwan.com.proportionalrecipes.Presenters.MainPresenterImpl;
import alexander.dmtaiwan.com.proportionalrecipes.R;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.RecipeAdapter;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.RecipeService;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.Utilities;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements MainView, RecipeAdapter.RecyclerClickListener, View.OnClickListener {

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private RecipeAdapter mAdapter;
    private MainPresenter mPresenter;
    private ArrayList<Recipe> mRecipeList;
    private Context mContext;
    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient mClient;


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
        mContext = this;
        mClient = new OkHttpClient();
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService service = retrofit.create(RecipeService.class);
        if (id == R.id.action_upload) {
            Call<ArrayList<Recipe>> upload = service.uploadRecipes(Utilities.JSON_ID, mRecipeList);
            upload.enqueue(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Call<ArrayList<Recipe>> call, retrofit2.Response<ArrayList<Recipe>> response) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(mCoordinatorLayout, getString(R.string.upload_success), Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(mCoordinatorLayout, getString(R.string.upload_failure), Snackbar.LENGTH_SHORT).show();
                }
            });

        }

        if (id == R.id.action_download) {
            Call<ArrayList<Recipe>> download = service.downloadRecipes(Utilities.JSON_ID);
            download.enqueue(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Call<ArrayList<Recipe>> call, retrofit2.Response<ArrayList<Recipe>> response) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(mCoordinatorLayout, getString(R.string.download_success), Snackbar.LENGTH_SHORT).show();
                    ArrayList<Recipe> recipeList = response.body();
                    String json = new Gson().toJson(recipeList);
                    Utilities.writeToFile(json,mContext);
                    mPresenter.fetchData();

                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(mCoordinatorLayout, getString(R.string.download_failure), Snackbar.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataReturned(ArrayList<Recipe> recipeList) {
        Log.i(LOG_TAG, String.valueOf(recipeList.size()));
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
        intent.putParcelableArrayListExtra(Utilities.EXTRA_RECIPES, mRecipeList);
        intent.putExtra(Utilities.EXTRA_NEW_RECIPE, true);
        startActivity(intent);
    }
}
