package com.example.lyricsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CocktailDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_COCKTAIL_DETAILS = "COCKTAIL_DETAILS";
    public static final int EDIT_COCKTAIL_ACTIVITY_REQUEST_CODE =3;
    private Cocktail cocktail;
    private int textSize=18;
    private TextView titleTextView;
    private TextView ingredientsTextView;
    private TextView receipeTextView;
    private CocktailViewModel cocktailViewModel;
    YouTubePlayerView youTubePlayerView;
    private Cocktail editedCocktail;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cocktailViewModel = ViewModelProviders.of(this).get(CocktailViewModel.class);
        cocktail = (Cocktail)getIntent().getSerializableExtra("EXTRA_COCKTAIL_DETAILS");
        setContentView(R.layout.activity_cocktail_details);
        titleTextView=findViewById(R.id.cocktail_title);
        ingredientsTextView =findViewById(R.id.cocktail_ingredients);
        receipeTextView =findViewById(R.id.cocktail_receipe);
        titleTextView.setText(getResources().getString(R.string.cocktail_title)+" "+ cocktail.getTitle());
        ingredientsTextView.setText(getResources().getString(R.string.cocktail_receipe)+" "+ cocktail.getIngredients());
        receipeTextView.setText(cocktail.getReceipe());
        String ytlink = cocktail.getYtlink();
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        if(ytlink.equals(""))
            youTubePlayerView.setVisibility(View.GONE);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = getYouTubeId(ytlink);
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_cocktail_details_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_cocktail) {
            editedCocktail = cocktail;
            Intent intent=new Intent(CocktailDetailsActivity.this, AddCocktailActivity.class);
            intent.putExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_TITLE, cocktail.getTitle());
            intent.putExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_INGREDIENTS, cocktail.getIngredients());
            intent.putExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_RECEIPE, cocktail.getReceipe());
            intent.putExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_YTLINK, cocktail.getYtlink());
            startActivityForResult(intent, EDIT_COCKTAIL_ACTIVITY_REQUEST_CODE);
            return true;
        }else if(id==R.id.downscale_text){
            if(titleTextView.getTextSize()>40)
                textSize--;
            titleTextView.setTextSize(textSize);
            ingredientsTextView.setTextSize(textSize);
            receipeTextView.setTextSize(textSize);
        }else if(id==R.id.upscale_text){
            if(titleTextView.getTextSize()<70)
                textSize++;
            titleTextView.setTextSize(textSize);
            ingredientsTextView.setTextSize(textSize);
            receipeTextView.setTextSize(textSize);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == EDIT_COCKTAIL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                editedCocktail.setTitle(data.getStringExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_TITLE));
                editedCocktail.setIngredients(data.getStringExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_INGREDIENTS));
                editedCocktail.setYtlink(data.getStringExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_YTLINK));
                editedCocktail.setReceipe(data.getStringExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_RECEIPE));
                cocktailViewModel.update(editedCocktail);
                titleTextView.setText(getResources().getString(R.string.cocktail_title)+" "+ editedCocktail.getTitle());
                ingredientsTextView.setText(getResources().getString(R.string.cocktail_receipe)+" "+ editedCocktail.getIngredients());
                receipeTextView.setText(editedCocktail.getReceipe());
                String ytlink = editedCocktail.getYtlink();
                if(ytlink.equals(""))
                    youTubePlayerView.setVisibility(View.GONE);
                else
                    youTubePlayerView.setVisibility(View.VISIBLE);
                editedCocktail = null;
            } else{
               Snackbar.make(findViewById(R.id.cocktail_details_layout),
                                getString(R.string.empty_edit_not_saved),
                                Snackbar.LENGTH_LONG)
                        .show();
            }
        }


    private String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }
}