package alexander.dmtaiwan.com.proportionalrecipes.Models;

import android.content.Context;

import java.util.List;

/**
 * Created by lenovo on 3/25/2016.
 */
public class MainInteractorImpl implements MainInteractor{

    private MainInteractorListener listener;
    private Context context;

    public MainInteractorImpl(MainInteractorListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void fetchData() {

    }

    public interface MainInteractorListener {
        void onResult(List<Recipe> recipes);
    }
}
