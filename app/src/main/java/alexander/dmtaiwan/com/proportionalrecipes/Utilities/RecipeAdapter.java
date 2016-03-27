package alexander.dmtaiwan.com.proportionalrecipes.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Recipe;
import alexander.dmtaiwan.com.proportionalrecipes.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 3/25/2016.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> mRecipes;
    private View mEmptyView;
    private RecyclerClickListener mClickListener;

    public RecipeAdapter(View emptyView, RecyclerClickListener listener) {
        this.mEmptyView = emptyView;
        this.mClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.mRecipeTitle.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if (mRecipes != null) {
            return mRecipes.size();
        }else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.text_view_recipe_title)
        TextView mRecipeTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickListener.onRecyclerClick(position);
        }
    }

    public void updateData(List<Recipe> recipes) {
        if(recipes!= null) {
            mRecipes = recipes;
            notifyDataSetChanged();
            mEmptyView.setVisibility(mRecipes.size() == 0 ? View.VISIBLE : View.GONE);
        }
    }

    public interface RecyclerClickListener {
        void onRecyclerClick(int position);
    }
}
