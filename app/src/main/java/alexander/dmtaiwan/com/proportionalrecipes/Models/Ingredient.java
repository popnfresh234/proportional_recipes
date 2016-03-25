package alexander.dmtaiwan.com.proportionalrecipes.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 3/25/2016.
 */
public class Ingredient implements Parcelable {

    private String ingredient;
    private float count;

    public Ingredient(String ingredient, float count) {
        this.ingredient = ingredient;
        this.count = count;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    protected Ingredient(Parcel in) {
        ingredient = in.readString();
        count = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredient);
        dest.writeFloat(count);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
