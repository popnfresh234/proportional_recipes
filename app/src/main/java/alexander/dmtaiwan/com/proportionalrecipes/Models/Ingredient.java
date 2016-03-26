package alexander.dmtaiwan.com.proportionalrecipes.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 3/25/2016.
 */
public class Ingredient implements Parcelable {

    private String name;
    private float count;

    private String unit;
    private float proportionalCount;

    public Ingredient(String name, float count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getProportionalCount() {
        return proportionalCount;
    }

    public void setProportionalCount(float proportionalCount) {
        this.proportionalCount = proportionalCount;
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
        count = in.readFloat();
        unit = in.readString();
        proportionalCount = in.readFloat();
;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(count);
        dest.writeString(unit);
        dest.writeFloat(proportionalCount);
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
