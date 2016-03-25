package alexander.dmtaiwan.com.proportionalrecipes.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Ingredient;
import alexander.dmtaiwan.com.proportionalrecipes.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 3/25/2016.
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredient> mIngredients;
    private View mEmptyView;
    private RecyclerClickListener mClickListener;

    public IngredientAdapter(View emptyView, RecyclerClickListener listener) {
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
        Ingredient ingredient = mIngredients.get(position);
        holder.mRecipeTitle.setText(ingredient.getName());
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null) {
            return mIngredients.size();
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
            Ingredient ingredient = mIngredients.get(position);
            mClickListener.onRecyclerClick(ingredient);
        }
    }

    public void updateData(List<Ingredient> ingredients) {
        if(ingredients!= null) {
            mIngredients = ingredients;
            notifyDataSetChanged();
            mEmptyView.setVisibility(mIngredients.size() == 0 ? View.VISIBLE : View.GONE);
        }
    }

    public interface RecyclerClickListener {
        void onRecyclerClick(Ingredient ingredient);
    }
}
