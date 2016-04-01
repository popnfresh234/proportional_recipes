package alexander.dmtaiwan.com.proportionalrecipes.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    public Recipe() {
    }

    public Recipe(String name, ArrayList<Ingredient> ingredientList) {
        this.name = name;
        this.ingredientList = ingredientList;
    }

    private String name;
    private ArrayList<Ingredient> ingredientList;
    private int id;
    private long time;
    private ArrayList<Recipe> deletedRecipes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ArrayList<Recipe> getDeletedRecipes() {
        return deletedRecipes;
    }

    public void setDeletedRecipes(ArrayList<Recipe> deletedRecipes) {
        this.deletedRecipes = deletedRecipes;
    }






    protected Recipe(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0x01) {
            ingredientList = new ArrayList<Ingredient>();
            in.readList(ingredientList, Ingredient.class.getClassLoader());
        } else {
            ingredientList = null;
        }
        id = in.readInt();
        time = in.readLong();
        if (in.readByte() == 0x01) {
            deletedRecipes = new ArrayList<Recipe>();
            in.readList(deletedRecipes, Recipe.class.getClassLoader());
        } else {
            deletedRecipes = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (ingredientList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredientList);
        }
        dest.writeInt(id);
        dest.writeLong(time);
        if (deletedRecipes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(deletedRecipes);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}