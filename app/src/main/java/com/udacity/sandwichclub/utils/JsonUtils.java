package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        String mainName = null;
        List<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = null;

        JSONObject jsonSandwich;

        try {
            jsonSandwich = new JSONObject(json);
            JSONObject nameObject = jsonSandwich.getJSONObject("name");
            mainName = nameObject.getString("mainName");

            JSONArray nicknameArray = nameObject.getJSONArray("alsoKnownAs");
            alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < nicknameArray.length(); i++) {
                alsoKnownAs.add(nicknameArray.getString(i));
            }

            placeOfOrigin = jsonSandwich.getString("placeOfOrigin");
            description = jsonSandwich.getString("description");
            image = jsonSandwich.getString("image");

            JSONArray ingredientsArray = jsonSandwich.getJSONArray("ingredients");
            ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description,
                image, ingredients);
    }
}
