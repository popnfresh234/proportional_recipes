package alexander.dmtaiwan.com.proportionalrecipes.Utilities;

import java.util.Comparator;

import alexander.dmtaiwan.com.proportionalrecipes.Models.Recipe;

/**
 * Created by Alexander on 3/29/2016.
 */
public class RecipeComparator implements Comparator <Recipe> {
    @Override
    public int compare(Recipe lhs, Recipe rhs) {
       return lhs.getName().compareTo(rhs.getName());
    }


}

