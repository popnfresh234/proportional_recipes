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
    public void onResult(ArrayList<Recipe> recipes) {
        mainView.onDataReturned(recipes);
    }
}
