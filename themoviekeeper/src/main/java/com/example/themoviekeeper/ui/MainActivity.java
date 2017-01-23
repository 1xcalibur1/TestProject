package com.example.themoviekeeper.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.themoviekeeper.R;
import com.example.themoviekeeper.db.MyMovie;
import com.example.themoviekeeper.db.MyMovieHelper;

import java.util.ArrayList;

import static android.support.v7.recyclerview.R.attr.layoutManager;
import static com.example.themoviekeeper.db.DBConstants.CALL_EDITMYMOVIEACTIVITY_MANUAL;
import static com.example.themoviekeeper.db.DBConstants.CALL_SEARCHMYMOVIEACTIVITY;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter_Main rva_main;
    private RecyclerView rv_main;
    private ArrayList<MyMovie> myMovieArrayList;
    private AlertDialog dialog;
    private MyMovieHelper movieHelper;
    private LinearLayoutManager layoutManager;
    private ArrayList<MyMovie> mSearchResults;
    private ItemTouchHelper itemTouchHelper;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingButton);
        rv_main = (RecyclerView)findViewById(R.id.rv_main);

        movieHelper = new MyMovieHelper(MainActivity.this);
        refreshRV();

        itemTouch();

        sharedPreferencesHelpFirst();

        floatingActionButtonListener();

        checkAppPermissions();

    }

    private void checkAppPermissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestAppPermissions();

        }

    }

    private void requestAppPermissions() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Permissions");
                    alertDialog.setMessage("This app MUST have storage write permissions. Please accept it.");
                    alertDialog.setIcon(R.drawable.ic_check_circle_black_48dp);
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            requestAppPermissions();

                        }
                    });

                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();

                        }
                    });

                    alertDialog.setCancelable(false);
                    alertDialog.show();

                }
        }

    }

    private void floatingActionButtonListener() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_mymovie,null);
                Button buttonDialogInternet = (Button) mView.findViewById(R.id.buttonDialogInternet);
                Button buttonDialogManual = (Button)mView.findViewById(R.id.buttonDialogManual);

                buttonDialogInternet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this,SearchMyMovieActivity.class);

                        dialog.cancel();
                        startActivity(intent);
                        startActivityForResult(intent,CALL_SEARCHMYMOVIEACTIVITY);

                    }
                });

                buttonDialogManual.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this,EditMyMovieActivity.class);
                        intent.putExtra("FromMainActivity",true);
                        intent.putExtra("Add",true);
                        dialog.cancel();

                        startActivityForResult(intent,CALL_EDITMYMOVIEACTIVITY_MANUAL);

                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.setCancelable(true);
                dialog.show();

            }
        });
    }

    private void sharedPreferencesHelpFirst() {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        if(pref.getBoolean("isFirstTime",true)){

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_first,null);

            Button buttonYes = (Button) mView.findViewById(R.id.buttonDialogYes_first);
            Button buttonNo = (Button)mView.findViewById(R.id.buttonDialogNo_first);
            final CheckBox checkBox = (CheckBox)mView.findViewById(R.id.checkBoxDialog_first);
            TextView tvDialogFirst = (TextView)mView.findViewById(R.id.tvDialogContent_first);

            PackageManager pm = getPackageManager();
            final boolean isYoutubeInstalled = isPackageInstalled("com.google.android.youtube", pm);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkBox.isChecked()){

                        SharedPreferences.Editor edit = pref.edit();
                        edit.putBoolean("isFirstTime",false);
                        edit.commit();
                    }
                    else{
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putBoolean("isFirstTime",true);
                        edit.commit();
                    }

                }
            });

            buttonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isYoutubeInstalled){
                        Toast.makeText(MainActivity.this,"Please install YouTube App First",Toast.LENGTH_LONG).show();
                    }
                    else{

                        Intent intent = new Intent(MainActivity.this,PlayTutorialActivity.class);
                        startActivity(intent);
                    }

                }
            });

            buttonNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.cancel();

                }
            });

            mBuilder.setView(mView);
            dialog = mBuilder.create();
            dialog.setCancelable(false);
            dialog.show();


        }
    }

    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    public void itemTouch(){

        mSearchResults = new ArrayList<MyMovie>();
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition();
                final MyMovie movie = ((RecyclerViewAdapter_Main) rva_main).getItem(position);
                final int datasetPostion = myMovieArrayList.indexOf(movie);
                final int searchResultPostion = mSearchResults.indexOf(movie);
                myMovieArrayList.remove(movie);
                if (searchResultPostion != -1)
                    mSearchResults.remove(movie);
                rva_main.notifyItemRemoved(position);

                Snackbar.make(getWindow().findViewById(android.R.id.content), "Item Deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                myMovieArrayList.add(datasetPostion, movie);
                                if (searchResultPostion != -1)
                                    mSearchResults.add(searchResultPostion, movie);
                                rva_main.notifyItemInserted(position);
                                rv_main.scrollToPosition(position);
                            }
                        }).setCallback(new Snackbar.Callback() {

                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if (event != DISMISS_EVENT_ACTION)
                            movieHelper.deleteMyMovie(String.valueOf(movie.getId()));
                    }
                }).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(rv_main);

    }

    public void refreshRV(){

        myMovieArrayList = movieHelper.getAllMyMovies();

        rva_main = new RecyclerViewAdapter_Main(myMovieArrayList,this);
        layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        rv_main.setLayoutManager(layoutManager);
        rv_main.addItemDecoration(itemDecoration);
        rv_main.setAdapter(rva_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){
            refreshRV();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_about:

                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("About");
                alertDialog.setMessage("TheMovieKeeper was developed by Gil Falkovitch.");
                alertDialog.setIcon(R.drawable.ic_info_black_24dp);
                alertDialog.setButton(alertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();
                return true;

            case R.id.action_deleteall:

                if (rva_main.getItemCount() ==0) {

                    Snackbar.make(findViewById(R.id.floatingButton), "Nothing to delete", Snackbar.LENGTH_LONG)
                            .setAction("ActionType", null).show();
                    return true;
                }
                movieHelper.deleteAllMovies();
                myMovieArrayList.clear();
                rva_main.notifyDataSetChanged();
                return true;

            case R.id.action_help:

                Intent intent = new Intent(MainActivity.this,PlayTutorialActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }
}
