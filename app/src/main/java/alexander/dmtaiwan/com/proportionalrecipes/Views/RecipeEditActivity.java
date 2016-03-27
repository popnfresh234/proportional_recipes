package alexander.dmtaiwan.com.proportionalrecipes.Views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Ingredient;
import alexander.dmtaiwan.com.proportionalrecipes.Models.Recipe;
import alexander.dmtaiwan.com.proportionalrecipes.R;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.AdapterListener;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.EditIngredientAdapter;
import alexander.dmtaiwan.com.proportionalrecipes.Utilities.Utilities;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 4/4/2015.
 */
public class RecipeEditActivity extends AppCompatActivity implements AdapterListener {
    public static final String GSON_RECIPE = "gson_recipe";
    public static final String RECIPE_ID = "recipe_id";
    public static final String DRUNKENMOKEYTW_ID = "uZq0TrDbkj";
    private DecimalFormat mDecimalFormat = new DecimalFormat("0.#");

    private Context mContext = this;
    private Recipe mRecipe;
    private Boolean mNewIngredient = false;
    private Ingredient mIngredient;
    private RecyclerView mIngredientsRecyclerView;
    private EditIngredientAdapter mIngredientAdapter;


    @Bind(R.id.dialog_button_add_ingredient)
    Button mAddIngredientButton;

    @Bind(R.id.button_save)
    Button mSaveButton;

    @Bind(R.id.edit_text_title)
    EditText mTitleEditText;


    //Ingredient Dialog Views
    private EditText mDialogQuantityEditText;
    private EditText mDialogIngredientNameEditText;
    private Spinner mDialogFractionSpinner;
    private Spinner mDialogUnitSpinner;
    private Button mDialogAddIngredientButton;
    private Button mDialogCancelIngredientButton;
    private Button mDialogRemoveIngredientButton;

    //Direction Dialog Views

    private EditText mDialogDirectionEditText;
    private Button mDialogAddDirectionButton;
    private Button mDialogCancelDirectionButton;
    private Button mDialogRemoveDirectionButton;

    private InputMethodManager mImm;

    //Setup ArrayList of Ingredients and Direction
    private List<Ingredient> mIngredientList = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_recipe_edit);
        ButterKnife.bind(this);


        mIngredientList = new ArrayList<Ingredient>();

        //Get input method manager
        mImm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);



        //Setup Recycler Views
        mIngredientsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_ingredients);
        mIngredientsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mIngredientsRecyclerView.setLayoutManager(llm);
        mIngredientAdapter = new EditIngredientAdapter(mIngredientList, this);
        mIngredientsRecyclerView.setAdapter(mIngredientAdapter);


        mAddIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateIngredientDialog(null, -1);
            }
        });


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //If recipe passed with intent, populate adapter
        if (getIntent().getParcelableExtra(Utilities.EXTRA_RECIPE) != null) {
            Recipe recipe = getIntent().getParcelableExtra(Utilities.EXTRA_RECIPE);
            populateRecipe(recipe);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mTitleEditText.getText().toString().trim().length() > 0) {

        }

    }

    private void populateRecipe(Recipe recipe) {

        String recipeId = getIntent().getStringExtra(RECIPE_ID);


        mRecipe = recipe;
        //Load Ingredients
        List<Ingredient> tempIngredientList = recipe.getIngredientList();
        if (tempIngredientList.size() > 0) {
            for (int i = 0; i < tempIngredientList.size(); i++) {
                mIngredientList.add(tempIngredientList.get(i));
            }
            mIngredientsRecyclerView.setVisibility(View.VISIBLE);
            mIngredientAdapter.notifyDataSetChanged();
        }
        //Set Title
        mTitleEditText.setText(recipe.getName());
    }


    private void CreateIngredientDialog(Ingredient ingredient, final int position) {

        if (ingredient == null) {
            mIngredient = new Ingredient();
            mNewIngredient = true;
        } else {
            mIngredient = ingredient;
        }

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ingredient);

        //Setup EditTexts
        mDialogQuantityEditText = (EditText) dialog.findViewById(R.id.dialog_edit_text_quantity);
        if (!mNewIngredient && mIngredient.getCount() != 0) {
            double quantity = ingredient.getCount();
            String stringQuantity = mDecimalFormat.format(quantity);
            mDialogQuantityEditText.setText(stringQuantity);
        }

        mDialogIngredientNameEditText = (EditText) dialog.findViewById(R.id.dialog_edit_text_ingredient_name);
        if (!mNewIngredient) {
            mDialogIngredientNameEditText.setText(mIngredient.getName());
        }

        //Setup unit spinner
        mDialogUnitSpinner = (Spinner) dialog.findViewById(R.id.dialog_spinner_units);
        ArrayAdapter<CharSequence> unitSpinnerAdapter = ArrayAdapter.createFromResource(mContext, R.array.dialog_spinner_units, R.layout.dialog_spinner_item);
        unitSpinnerAdapter.setDropDownViewResource(R.layout.dialog_spinner_layout);
        mDialogUnitSpinner.setAdapter(unitSpinnerAdapter);
        if (!mNewIngredient && mIngredient.getUnit() != null) {
            String compareValue = mIngredient.getUnit();
            if (!compareValue.equals(null)) {
                int spinnerPosition = unitSpinnerAdapter.getPosition(compareValue);
                mDialogUnitSpinner.setSelection(spinnerPosition);
                spinnerPosition = 0;
            }
        }

        //Add ingredient button
        mDialogAddIngredientButton = (Button) dialog.findViewById(R.id.dialog_button_add_ingredient);
        if (!mNewIngredient) mDialogAddIngredientButton.setText("Update");

        mDialogAddIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewIngredient = false;
                mImm.hideSoftInputFromWindow(mDialogQuantityEditText.getWindowToken(), 0);
                mImm.hideSoftInputFromWindow(mDialogIngredientNameEditText.getWindowToken(), 0);
                if (!mDialogIngredientNameEditText.getText().toString().equals("")) {

                    if (!mDialogQuantityEditText.getText().toString().equals("")) {
                        mIngredient.setCount(Double.valueOf(mDialogQuantityEditText.getText().toString().trim()));
                    }

                    if (mDialogUnitSpinner.getSelectedItemPosition() == 0) {
                        mIngredient.setUnit(null);
                    }
                    if (!mDialogUnitSpinner.getSelectedItem().toString().equals("Units")) {
                        mIngredient.setUnit(mDialogUnitSpinner.getSelectedItem().toString());
                    }
                    if (!mDialogIngredientNameEditText.getText().toString().equals("")) {
                        mIngredient.setName(mDialogIngredientNameEditText.getText().toString().toLowerCase().trim());
                    }

                    dialog.dismiss();

                    if (position == -1) {
                        Log.i("ADDING", "adding");
                        mIngredientList.add(mIngredient);
                    } else {
                        mIngredientList.set(position, mIngredient);
                    }
                    if (mIngredientList.size() > 0) {
                        mIngredientsRecyclerView.setVisibility(View.VISIBLE);

                    }
                    mIngredientAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "Please enter an ingredient", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Remove Ingredient Button
        mDialogRemoveIngredientButton = (Button) dialog.findViewById(R.id.dialog_button_remove);
        if (!mNewIngredient) mDialogRemoveIngredientButton.setVisibility(View.VISIBLE);
        mDialogRemoveIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIngredientList.remove(position);
                mIngredientAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });


        //Setup cancel button
        mDialogCancelIngredientButton = (Button) dialog.findViewById(R.id.dialog_button_cancel);
        mDialogCancelIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onMoveIngredientUpClicked(int position) {
        int newPosition = position - 1;
        if (newPosition < 0) return;
        Ingredient ingredient = mIngredientList.get(position);
        mIngredientList.remove(position);
        mIngredientAdapter.notifyItemChanged(position);
        mIngredientList.add(newPosition, ingredient);
        mIngredientAdapter.notifyItemChanged(newPosition);
        mIngredientAdapter.notifyItemMoved(position, newPosition);

    }

    @Override
    public void onIngredientCardViewClicked(int position) {
        Ingredient ingredient = mIngredientList.get(position);
        CreateIngredientDialog(ingredient, position);
    }
}
