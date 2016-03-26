package alexander.dmtaiwan.com.proportionalrecipes.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private RecyclerTextChangedListener mTextChangedListener;
    private Boolean mOnBind;
    private Context mContext;


    public IngredientAdapter(View emptyView, RecyclerClickListener listener, RecyclerTextChangedListener textListener, Context context) {
        this.mEmptyView = emptyView;
        this.mClickListener = listener;
        this.mTextChangedListener = textListener;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ingredient, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.mIngredientTitle.setText(ingredient.getName());
        holder.mIngredientCount.setText(String.valueOf(ingredient.getCount()));
        mOnBind = true;
        if (ingredient.getProportionalCount() != 0.0f) {
            holder.mProportionalCount.setText(String.valueOf(ingredient.getProportionalCount()));
        }
        mOnBind = false;
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null) {
            return mIngredients.size();
        }else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, TextWatcher {

        @Bind(R.id.text_view_ingredient)
        TextView mIngredientTitle;

        @Bind(R.id.text_view_ingredient_count)
        TextView mIngredientCount;

        @Bind(R.id.edit_text_proportional_count)
        EditText mProportionalCount;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mProportionalCount.addTextChangedListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Ingredient ingredient = mIngredients.get(position);
            mClickListener.onRecyclerClick(ingredient);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }


        @Override
        public void afterTextChanged(Editable s) {
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

    public interface RecyclerTextChangedListener{
        void onRecyclerTextChanged(Float enteredValue, int position);
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }
}
