package alexander.dmtaiwan.com.proportionalrecipes.Utilities;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Ingredient;
import alexander.dmtaiwan.com.proportionalrecipes.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 4/2/2015.
 */
public class EditIngredientAdapter extends RecyclerView.Adapter<EditIngredientAdapter.ViewHolder> {
    private static final String TAG = "Adapter";
    private List<Ingredient> mIngredients;
    private AdapterListener mListener;
    private DecimalFormat mDecimalFormat = new DecimalFormat("0.#");


    public EditIngredientAdapter(List<Ingredient> ingredients, AdapterListener listener) {
        this.mIngredients = ingredients;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_edit_ingredient, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Ingredient ingredient = mIngredients.get(position);

        //Listeners which interface with RecipeActivity
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMoveIngredientUpClicked(position);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onIngredientCardViewClicked(position);
            }
        });

        //Clear out the view
        holder.quantity.setText("");
        holder.unit.setText("");
        holder.name.setText("");

        //Load up ingredient values if not a new ingredient item
        if(ingredient.getCount()!=0) {
            double quantity = ingredient.getCount();
            String stringQuantity = mDecimalFormat.format(quantity);
            holder.quantity.setText(stringQuantity + " ");

        }

        if (ingredient.getUnit()!=null) {
            holder.unit.setText(ingredient.getUnit()+" ");

        }

        holder.name.setText(ingredient.getName());
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null) {
            return mIngredients.size();
        } else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_view_ingredient_list_edit_quantity)
        TextView quantity;

        @Bind(R.id.text_view_ingredient_list_edit_unit)
        TextView unit;

        @Bind(R.id.text_view_ingredient_list_edit_name)
        TextView name;

        @Bind(R.id.button_move_up)
        Button button;

        @Bind(R.id.card_view)
        CardView cardView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
