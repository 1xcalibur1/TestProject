package com.example.themoviekeeper.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.themoviekeeper.R;
import com.example.themoviekeeper.networking.Task_GetMyMovieDetails;
import com.example.themoviekeeper.networking.Task_GetMyMovieList;

public class SearchMyMovieActivity extends AppCompatActivity {

    private Boolean newDataAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_my_movie);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.menuItemSearch);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String parsedInputQuery = MovieInputStringParser(newText);
                Task_GetMyMovieList movieList = new Task_GetMyMovieList(SearchMyMovieActivity.this);
                movieList.execute("http://www.omdbapi.com/?s="+parsedInputQuery+"&y=&plot=full&r=json");

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public String MovieInputStringParser(String inputString){

        inputString = inputString.replaceAll("[^a-zA-Z0-9]", " ");
        inputString = inputString.trim();
        inputString = inputString.replaceAll(" +", "+");

        return inputString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){
            newDataAdded = true;
        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        if (newDataAdded){
            setResult(RESULT_OK,intent);
        }
        else{
            setResult(RESULT_CANCELED,intent);
        }
        super.onBackPressed();
    }


}


