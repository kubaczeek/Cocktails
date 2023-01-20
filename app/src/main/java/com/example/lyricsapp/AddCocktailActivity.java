package com.example.lyricsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddCocktailActivity extends AppCompatActivity {

    public static final String EXTRA_ADD_COCKTAIL_TITLE = "ADD_COCKTAIL_TITLE";
    public static final String EXTRA_ADD_COCKTAIL_INGREDIENTS = "ADD_COCKTAIL_INGREDIENTS";
    public static final String EXTRA_ADD_COCKTAIL_RECEIPE = "ADD_COCKTAIL_LYRICS";
    public static final String EXTRA_ADD_COCKTAIL_YTLINK = "ADD_COCKTAIL_YTLINK";

    private EditText addTitleEditText;
    private EditText addIngredientsEditText;
    private EditText addReceipeEditText;
    private EditText addYtLinkEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cocktail);
        addTitleEditText = findViewById(R.id.add_cocktail_title);
        addIngredientsEditText = findViewById(R.id.add_cocktail_ingredients);
        addReceipeEditText = findViewById(R.id.add_cocktail_receipe);
        addYtLinkEditText = findViewById(R.id.add_cocktail_yt_link);
        if (getIntent().hasExtra(EXTRA_ADD_COCKTAIL_TITLE)) {
            addTitleEditText.setText(getIntent().getStringExtra(EXTRA_ADD_COCKTAIL_TITLE));
            addIngredientsEditText.setText(getIntent().getStringExtra(EXTRA_ADD_COCKTAIL_INGREDIENTS));
            addReceipeEditText.setText(getIntent().getStringExtra(EXTRA_ADD_COCKTAIL_RECEIPE));
            addYtLinkEditText.setText(getIntent().getStringExtra(EXTRA_ADD_COCKTAIL_YTLINK));
        }
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(e -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(addTitleEditText.getText())
                    || TextUtils.isEmpty(addIngredientsEditText.getText())|| TextUtils.isEmpty(addReceipeEditText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String title = addTitleEditText.getText().toString();
                replyIntent.putExtra(EXTRA_ADD_COCKTAIL_TITLE, title);
                String ingredients = addIngredientsEditText.getText().toString();
                replyIntent.putExtra(EXTRA_ADD_COCKTAIL_INGREDIENTS, ingredients);
                String receipe = addReceipeEditText.getText().toString();
                replyIntent.putExtra(EXTRA_ADD_COCKTAIL_RECEIPE,receipe);
                String ytlink = addYtLinkEditText.getText().toString();
                replyIntent.putExtra(EXTRA_ADD_COCKTAIL_YTLINK, ytlink);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}