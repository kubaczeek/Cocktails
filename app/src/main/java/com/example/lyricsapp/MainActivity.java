package com.example.lyricsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CocktailViewModel cocktailsViewModel;
    public static final int NEW_COCKTAIL_ACTIVITY_REQUEST_CODE = 1;
    public static final int COCKTAIL_DETAILS_ACTIVITY_REQUEST_CODE = 2;
    public static final int WEATHER_ACTIVITY_REQUEST_CODE = 3;
    final CocktailAdapter adapter = new CocktailAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cocktailsViewModel = ViewModelProviders.of(this).get(CocktailViewModel.class);
        cocktailsViewModel.findAll().observe(this, adapter::setBooks);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_COCKTAIL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Cocktail cocktail = new Cocktail(data.getStringExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_TITLE),
                    data.getStringExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_INGREDIENTS),
                    data.getStringExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_RECEIPE),
                    data.getStringExtra(AddCocktailActivity.EXTRA_ADD_COCKTAIL_YTLINK));
            cocktailsViewModel.insert(cocktail);
        } else if (requestCode == COCKTAIL_DETAILS_ACTIVITY_REQUEST_CODE) {
            //empty
        } else {
//            Snackbar.make(findViewById(R.id.main_layout),
//                            getString(R.string.empty_not_saved),
//                            Snackbar.LENGTH_LONG)
//                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_cocktail);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_cocktail));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_cocktail) {
            Intent intent = new Intent(MainActivity.this, AddCocktailActivity.class);
            startActivityForResult(intent, NEW_COCKTAIL_ACTIVITY_REQUEST_CODE);
            return true;
        } else if (id == R.id.weather) {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivityForResult(intent, WEATHER_ACTIVITY_REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Cocktail holder
    private class CocktailHolder extends RecyclerView.ViewHolder {
        private final TextView cocktailTitleTextView;
        private final TextView cocktailIngredientsTextView;
        private Cocktail cocktail;
        View cocktailItem = itemView.findViewById(R.id.cocktail_item);

        public CocktailHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.cocktails_list_item, parent, false));

            cocktailTitleTextView = itemView.findViewById(R.id.cocktail_title);
            cocktailIngredientsTextView = itemView.findViewById(R.id.cocktail_ingredients);
            cocktailItem.setOnLongClickListener(v -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Test");
                alert.setCancelable(false);
                alert.setMessage("Do You want to delete " + cocktail.getTitle() + "?");
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Cocktail " + cocktail.getTitle() + " has not been removed", Toast.LENGTH_LONG).show();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cocktailsViewModel.delete(cocktail);
                        Toast.makeText(MainActivity.this, "Cocktail " + cocktail.getTitle() + " has been removed", Toast.LENGTH_LONG).show();
                    }
                });
                alert.show();
                return true;
            });
        }


        public void bind(Cocktail cocktail) {
            cocktailTitleTextView.setText(cocktail.getTitle());
            cocktailIngredientsTextView.setText(cocktail.getIngredients());
            this.cocktail = cocktail;
            cocktailItem.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, CocktailDetailsActivity.class);
                intent.putExtra("EXTRA_COCKTAIL_DETAILS", cocktail);
                startActivityForResult(intent, COCKTAIL_DETAILS_ACTIVITY_REQUEST_CODE);
            });
        }
    }

    //cocktail adapter
    private class CocktailAdapter extends RecyclerView.Adapter<CocktailHolder> implements Filterable {
        private List<Cocktail> cocktails;
        private List<Cocktail> cocktailList;


        @NonNull
        @Override
        public CocktailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CocktailHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(CocktailHolder holder, int position) {
            if (cocktails != null) {
                Cocktail cocktail = cocktails.get(position);
                holder.bind(cocktail);
            } else {

                Log.d("MainActivity", "No cocktails");
            }
        }

        public int getItemCount() {
            if (cocktails != null) {
                return cocktails.size();
            } else {
                return 0;
            }
        }

        void setBooks(List<Cocktail> cocktails) {
            this.cocktails = cocktails;
            cocktailList = new ArrayList<>(cocktails);
            notifyDataSetChanged();
        }


        @Override
        public Filter getFilter() {
            return exampleFilter;
        }

        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Cocktail> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(cocktailList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Cocktail cocktail : cocktailList) {
                        if (cocktail.getTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(cocktail);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                cocktails.clear();
                cocktails.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }
}