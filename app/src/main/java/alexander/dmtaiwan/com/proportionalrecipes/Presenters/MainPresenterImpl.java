package alexander.dmtaiwan.com.proportionalrecipes.Presenters;

import android.content.Context;

import java.util.ArrayList;

import alexander.dmtaiwan.com.proportionalrecipes.Models.MainInteractor;
import alexander.dmtaiwan.com.proportionalrecipes.Models.MainInteractorImpl;
import alexander.dmtaiwan.com.proportionalrecipes.Models.Recipe;
import alexander.dmtaiwan.com.proportionalrecipes.Views.MainView;

/**
 * Created by lenovo on 3/25/2016.
 */
public class MainPresenterImpl implements MainPresenter, MainInteractorImpl.MainInteractorListener {

    private MainView mainView;
    private MainInteractor mainInteractor;
    private Context context;

    public MainPresenterImpl(MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
        this.mainInteractor = new MainInteractorImpl(this, context);
    }

    @Override
    public void fetchData() {
        mainInteractor.fetchData();
    }

    @Override
    public void uploadData(ArrayList<Recipe> recipeList, Context context) {
        mainInteractor.uploadData(recipeList, context);
    }

    @Override
    public void downloadData(ArrayList<Recipe> recipeList, Context context) {
        mainInteractor.downloadData(recipeList, context);
    }

    @Override
    public void onResult(ArrayList<Recipe> recipes) {
        mainView.onDataReturned(recipes);
    }

    @Override
    public void onNetworkTransferComplete(ArrayList<Recipe> recipes) {
    }

    @Override
    public void showLoading() {
        mainView.showLoading();
    }

    @Override
    public void hideLoading() {
        mainView.hideLoading();
    }

    @Override
    public void makeSnackBar(String message) {
        mainView.makeSnackbar(message);
    }
}
