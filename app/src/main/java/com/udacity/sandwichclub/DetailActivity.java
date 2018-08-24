package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mPlaceOfOriginTextView;
    TextView mAlsoKnownAsTextView;
    TextView mIngredientsTextView;
    TextView mDescriptionTextView;

    TextView mPlaceOfOriginTextViewLabel;
    TextView mAlsoKnownAsTextViewLabel;
    TextView mIngredientsTextViewLabel;
    TextView mDescriptionTextViewLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mPlaceOfOriginTextView = (TextView) findViewById(R.id.origin_tv);
        mAlsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
        mIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);

        mPlaceOfOriginTextViewLabel = (TextView) findViewById(R.id.tv_place_of_origin_label);
        mAlsoKnownAsTextViewLabel = (TextView) findViewById(R.id.tv_also_known_as_label);
        mIngredientsTextViewLabel = (TextView) findViewById(R.id.tv_ingredient_label);
        mDescriptionTextViewLabel = (TextView) findViewById(R.id.tv_descrition_label);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();

        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String origin = sandwich.getPlaceOfOrigin();

        if (origin != null && !origin.equals("")) {
            showLabelAndContent(mPlaceOfOriginTextViewLabel, mPlaceOfOriginTextView);
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            hideLabelAndContent(mPlaceOfOriginTextViewLabel, mPlaceOfOriginTextView);
        }

        List<String> nicknames = sandwich.getAlsoKnownAs();
        if (nicknames != null && !nicknames.isEmpty()) {
            showLabelAndContent(mAlsoKnownAsTextViewLabel, mAlsoKnownAsTextView);
            Iterator<String> nicknameIterator = nicknames.iterator();
            while (nicknameIterator.hasNext()) {
                mAlsoKnownAsTextView.append(nicknameIterator.next());
                if (nicknameIterator.hasNext()) {
                    mAlsoKnownAsTextView.append("\n");
                }
            }
        } else {
            hideLabelAndContent(mAlsoKnownAsTextViewLabel, mAlsoKnownAsTextView);
        }

        List<String> ingredients = sandwich.getIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            showLabelAndContent(mIngredientsTextViewLabel, mIngredientsTextView);
            Iterator<String> ingredientIterator = sandwich.getIngredients().iterator();
            while (ingredientIterator.hasNext()) {
                mIngredientsTextView.append(ingredientIterator.next());
                if (ingredientIterator.hasNext()) {
                    mIngredientsTextView.append("\n");
                }
            }
        } else {
            hideLabelAndContent(mIngredientsTextViewLabel, mIngredientsTextView);
        }

        String description = sandwich.getDescription();
        if (description != null && !description.equals("")) {
            showLabelAndContent(mDescriptionTextViewLabel, mDescriptionTextView);
            mDescriptionTextView.setText(sandwich.getDescription());
        } else {
            hideLabelAndContent(mDescriptionTextViewLabel, mDescriptionTextView);
        }
    }

    private void hideLabelAndContent(TextView label, TextView content) {
        label.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
    }

    private void showLabelAndContent(TextView label, TextView content) {
        label.setVisibility(View.VISIBLE);
        content.setVisibility(View.VISIBLE);
    }

}
