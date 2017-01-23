package com.example.themoviekeeper.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.themoviekeeper.R;
import com.example.themoviekeeper.db.MyMovie;
import com.example.themoviekeeper.db.MyMovieCategory;
import com.example.themoviekeeper.db.MyMovieDBHelper;
import com.example.themoviekeeper.db.MyMovieHelper;
import com.example.themoviekeeper.networking.Task_GetMyMovieDetails;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.themoviekeeper.R.id.et_category_desc_edit;
import static com.example.themoviekeeper.R.id.iv_edit;
import static com.example.themoviekeeper.R.id.title;
import static com.example.themoviekeeper.R.id.tv_category_name_edit;
import static com.example.themoviekeeper.db.DBConstants.CATEGORY_ARRAY;
import static com.example.themoviekeeper.db.DBConstants.MOVIE_COLUMN_NAME_year;

public class EditMyMovieActivity extends AppCompatActivity {


    private static final int REQUEST_IMAGE_CAPTURE = 5;
    private static final int REQUEST_PICK_IMAGE = 6;
    private FloatingActionButton fb_edit;
    private Toolbar toolbar;
    private Boolean newDataAdded = false;
    private RecyclerView rv_edit;
    private RecyclerViewAdapter_Edit rva_edit;
    private MyMovieHelper myMovieHelper;
    private ImageView iv_edit;
    private RatingBar ratingBar;
    private float ratingBarRating;
    private String moviestate;
    private ToggleButton tb_isWatched;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_movie);

        toolbar = (Toolbar)findViewById(R.id.MyToolbar);
        fb_edit = (FloatingActionButton)findViewById(R.id.fb_edit);

        rv_edit = (RecyclerView)findViewById(R.id.rv_edit);
        iv_edit = (ImageView)findViewById(R.id.bgheader);
        ratingBar = (RatingBar)findViewById(R.id.rb_editactiviry);
        tb_isWatched = (ToggleButton)findViewById(R.id.tb_edit);

//        Drawable drawable = ratingBar.getProgressDrawable();
//        drawable.setColorFilter(Color.parseColor("#C10E19"), PorterDuff.Mode.SRC_ATOP);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapse_toolbar);

        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        if(iv_edit.getDrawable()==null)
        {
            Picasso.with(this).load(R.drawable.movie_default).into(iv_edit);
        }


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra("FromSearchActivity")){

            String intentExtraWithImdbid = getIntent().getStringExtra("imdbid");

            Task_GetMyMovieDetails task_getMyMovieDetails = new Task_GetMyMovieDetails(this);
            task_getMyMovieDetails.execute("http://www.omdbapi.com/?i="+intentExtraWithImdbid+"&plot=full&r=json");

        }

        if (getIntent().hasExtra("FromMainActivity")){

            if (getIntent().hasExtra("Add")){

                ArrayList<MyMovieCategory> categoryArrayList = new ArrayList<>();
                for (String category : CATEGORY_ARRAY){
                    categoryArrayList.add(new MyMovieCategory(category,""));
                }

                rva_edit = new RecyclerViewAdapter_Edit(this,categoryArrayList);
                rv_edit.setLayoutManager(new LinearLayoutManager(this));
                rv_edit.setAdapter(rva_edit);

            }
            else{

                int databaseID = getIntent().getIntExtra("Id",-1);

                myMovieHelper = new MyMovieHelper(EditMyMovieActivity.this);
                MyMovie myMovie = myMovieHelper.get((databaseID));
                ratingBar.setRating(myMovie.getPersonalRating());
                moviestate = myMovie.getMoviestate();
                tb_isWatched.setChecked(Boolean.parseBoolean(moviestate));

                collapsingToolbarLayout.setTitle(myMovie.getTitle());

                if (myMovie.getPoster() != null && myMovie.getPoster().length()>0) {

                    String imgUrlOrPath = myMovie.getPoster();

                    if (imgUrlOrPath.contains("storage") ||
                            imgUrlOrPath.contains("sdcard")){
                        Picasso.with(EditMyMovieActivity.this).load(new File(imgUrlOrPath)).into(iv_edit);
                    }
                    if (imgUrlOrPath.contains("http://") ||
                            imgUrlOrPath.contains("https://") ){
                        Picasso.with(EditMyMovieActivity.this).load(imgUrlOrPath).into(iv_edit);
                    }

                }
                else{
                    //Picasso.with(this).load(R.drawable.movie_default).into(iv_edit);
                }

                ArrayList<MyMovieCategory> myMovieCategoryArrayList = MyMovieToMyCategoryArrayList(myMovie);
                rva_edit = new RecyclerViewAdapter_Edit(this,myMovieCategoryArrayList);
                rv_edit.setLayoutManager(new LinearLayoutManager(this));
                rv_edit.setAdapter(rva_edit);


            }

        }

        tb_isWatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                newDataAdded=true;
                moviestate = String.valueOf(tb_isWatched.isChecked());
                myMovieHelper = new MyMovieHelper(EditMyMovieActivity.this);
                int databaseid = getIntent().getIntExtra("Id", -1);

                if (databaseid != -1){

                    myMovieHelper.updateMyMovieMovieState(databaseid,moviestate);

                }

            }
        });

        fb_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isTitleFieldPopulated()) {

                    EditText et_tempTitle = (EditText)rv_edit.findViewHolderForAdapterPosition(0).itemView.findViewById(et_category_desc_edit);
                    String tempTitle = et_tempTitle.getText().toString();

                    if (MovieDoesNotExist(tempTitle)) {

                        if (getIntent().hasExtra("Add")) {

                            newDataAdded = true;
                            MyMovie mymovie = buildMyMovieOutOfRV();
                            myMovieHelper = new MyMovieHelper(EditMyMovieActivity.this);
                            mymovie.setMoviestate(moviestate);
                            mymovie.setPersonalRating(ratingBarRating);

                            if (mymovie.getPoster().contains("http://") ||
                                    mymovie.getPoster().contains("https://") ){
                                String localPoster =
                                        getAndSaveConvertedImgFromWebToLocal(mymovie.getPoster());
                                mymovie.setPoster(localPoster);
                            }

                            myMovieHelper.add(mymovie);

                            Toast.makeText(EditMyMovieActivity.this, "Movie added successfully!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }

                    }
                    else {
                        //movie exists
                        //check if same data already exist meaning the user did not input anything new

                        if (checkIfSameDataAlreadyExist()){

                            Toast.makeText(EditMyMovieActivity.this,"Movie already exist!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            newDataAdded = true;
                            int databaseID = getIntent().getIntExtra("Id", -1);
                            myMovieHelper = new MyMovieHelper(EditMyMovieActivity.this);
                            MyMovie mymovie = buildMyMovieOutOfRV();
                            mymovie.setId(databaseID);
                            mymovie.setMoviestate(moviestate);
                            mymovie.setPersonalRating(ratingBarRating);

                            if (mymovie.getPoster().contains("http://") ||
                                    mymovie.getPoster().contains("https://") ){
                                String localPoster =
                                        getAndSaveConvertedImgFromWebToLocal(mymovie.getPoster());
                                mymovie.setPoster(localPoster);
                            }

                            myMovieHelper.updateMyMovie(mymovie);
                            Toast.makeText(EditMyMovieActivity.this, "Movie updated!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }


                    }


                }
                else {
                    Toast.makeText(EditMyMovieActivity.this, "No Title Present", Toast.LENGTH_SHORT).show();
                }
            }


        });



        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                newDataAdded=true;

                myMovieHelper = new MyMovieHelper(EditMyMovieActivity.this);
                int databaseid = getIntent().getIntExtra("Id", -1);

                if (databaseid != -1){

                    myMovieHelper.updateMyMoviePersonalRating(databaseid,rating);

                }
                else{
                    ratingBarRating = rating;
                }

            }
        });


    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = imageReturnedIntent.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                iv_edit.setImageBitmap(imageBitmap);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
                String imageFileName = "JPEG_" + timeStamp + ".jpg";
                OutputStream output;

                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsolutePath() + "/TheMovieKeeper");

                if (!dir.isDirectory()) {
                    dir.mkdir();
                }

                File file = new File(dir, imageFileName);

                try {

                    output = new FileOutputStream(file);
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                    output.flush();
                    output.close();
                    //Toast.makeText(MainActivity.this,"Image Saved",Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(EditMyMovieActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                EditText et_tempPoster = (EditText) rv_edit.findViewHolderForAdapterPosition(13).itemView.findViewById(et_category_desc_edit);
                et_tempPoster.setText(file.getAbsolutePath());

            }

        }
    }



    public String getAndSaveConvertedImgFromWebToLocal(String moviePoster){

        Bitmap bitmap;
        OutputStream output;
        String moviePosterConverted;

        moviePosterConverted = URLUtil.guessFileName(moviePoster,null,null);

        bitmap = ((BitmapDrawable) iv_edit.getDrawable()).getBitmap();
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/TheMovieKeeper");

        if (!dir.isDirectory()){
            dir.mkdir();

        }

        File file = new File(dir,moviePosterConverted);

        try{

            output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,output);
            output.flush();
            output.close();
            //Toast.makeText(MainActivity.this,"Image Saved",Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            Toast.makeText(EditMyMovieActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return file.getAbsolutePath();


    }

    public String getMovieTitle(){

        EditText et_tempTitle = (EditText)rv_edit.findViewHolderForAdapterPosition(0).itemView.findViewById(et_category_desc_edit);
        String tempTitle = et_tempTitle.getText().toString();

        return tempTitle;
    }

    public boolean checkIfSameDataAlreadyExist(){

        int databaseID = getIntent().getIntExtra("Id", -1);
        myMovieHelper = new MyMovieHelper(EditMyMovieActivity.this);
        //came from search

        if (databaseID == -1){

            EditText et_tempTitle = (EditText)rv_edit.findViewHolderForAdapterPosition(0).itemView.findViewById(et_category_desc_edit);
            String tempTitle = et_tempTitle.getText().toString();

            databaseID = myMovieHelper.getID(tempTitle);

        }

        int itemCount = rva_edit.getItemCount();
        String colName, colData;
        View view;

        for (int i = 0; i < itemCount; i++) {

            view = rv_edit.findViewHolderForAdapterPosition(i).itemView;
            TextView tv_category_name_edit2 = (TextView)view.findViewById(R.id.tv_category_name_edit);
            EditText et_category_desc_edit2 =  (EditText)view.findViewById(R.id.et_category_desc_edit);


            colData = et_category_desc_edit2.getText().toString();

            colName = tv_category_name_edit2.getText().toString();


            if (myMovieHelper.getDataExist(databaseID,colName,colData)){

            }else{

                return false;
            }

        }

        return true;

    }

    public boolean MovieDoesNotExist(String title){

        myMovieHelper = new MyMovieHelper(EditMyMovieActivity.this);
        ArrayList<MyMovie> temp_arraylist = myMovieHelper.getAllMyMovies();

        for (int i = 0; i <temp_arraylist.size() ; i++) {

            if (title.equals(temp_arraylist.get(i).getTitle())){
                return false;

            }
        }
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_share_movie:

                if (getMovieTitle().isEmpty()){

                    Toast.makeText(this,"No Title, cant share",Toast.LENGTH_SHORT).show();
                    return true;

                }
                else{

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "I am watching "+getMovieTitle());
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "Share this movie with"));
                    return true;

                }
            case R.id.action_camera:
                dispatchTakePictureIntent();
                newDataAdded=true;
                return true;

            case R.id.action_delete_movie:

                int databaseID = getIntent().getIntExtra("Id",-1);

                if (databaseID !=-1){
                    newDataAdded=true;
                    myMovieHelper.deleteMyMovie(String.valueOf(databaseID));
                    onBackPressed();
                    return true;
                }
                Toast.makeText(this,"Nothing to delete",Toast.LENGTH_SHORT).show();
                return true;
        }

        Intent intent = new Intent();
        if (newDataAdded){
            setResult(RESULT_OK,intent);
        }
        else{
            setResult(RESULT_CANCELED,intent);
        }
        super.onBackPressed();

        return true;
    }



    public ArrayList<MyMovieCategory> MyMovieToMyCategoryArrayList(MyMovie myMovie) {

        ArrayList<MyMovieCategory> categoryArrayList = new ArrayList<>();


        categoryArrayList.add(new MyMovieCategory("Title",myMovie.getTitle()));
        categoryArrayList.add(new MyMovieCategory("Year",myMovie.getYear()));

        categoryArrayList.add(new MyMovieCategory("Rated",myMovie.getRated()));

        categoryArrayList.add(new MyMovieCategory("Released",myMovie.getReleased()));

        categoryArrayList.add(new MyMovieCategory("Runtime", myMovie.getRuntime()));

        categoryArrayList.add(new MyMovieCategory("Genre",myMovie.getGenre()));

        categoryArrayList.add(new MyMovieCategory("Director",myMovie.getDirector()));

        categoryArrayList.add(new MyMovieCategory("Writer",myMovie.getWriter()));

        categoryArrayList.add(new MyMovieCategory("Actors",myMovie.getActors()));

        categoryArrayList.add(new MyMovieCategory("Plot",myMovie.getPlot()));

        categoryArrayList.add(new MyMovieCategory("Language",myMovie.getLanguage()));

        categoryArrayList.add(new MyMovieCategory("Country",myMovie.getCountry()));

        categoryArrayList.add(new MyMovieCategory("Awards",myMovie.getAwards()));

        categoryArrayList.add(new MyMovieCategory("Poster",myMovie.getPoster()));

        categoryArrayList.add(new MyMovieCategory("Metascore",myMovie.getMetascore()));

        categoryArrayList.add(new MyMovieCategory("imdbRating",myMovie.getImdbrating()));

        categoryArrayList.add(new MyMovieCategory("imdbVotes",myMovie.getImdbvotes()));

        categoryArrayList.add(new MyMovieCategory("imdbID",myMovie.getImdbid()));

        categoryArrayList.add(new MyMovieCategory("Type",myMovie.getType()));

        return categoryArrayList;

    }

    public MyMovie buildMyMovieOutOfRV(){

        int itemcount = rva_edit.getItemCount();
        View view;// = holder.itemView;
        EditText et_category_desc_edit;
        TextView tv_category_name_edit;
        String string_category_desc_edit;
        String string_category_name_edit;

        MyMovie mymovie = new MyMovie();
        for (int i = 0; i < itemcount; i++) {

            view = rv_edit.findViewHolderForAdapterPosition(i).itemView;
            tv_category_name_edit = (TextView)view.findViewById(R.id.tv_category_name_edit);
            et_category_desc_edit =  (EditText)view.findViewById(R.id.et_category_desc_edit);
            string_category_name_edit = tv_category_name_edit.getText().toString();
            string_category_desc_edit = et_category_desc_edit.getText().toString();

            switch (string_category_name_edit){

                case "Title": mymovie.setTitle(string_category_desc_edit); break;
                case "Year": mymovie.setYear(string_category_desc_edit); break;
                case "Rated": mymovie.setRated(string_category_desc_edit);break;
                case "Released": mymovie.setReleased(string_category_desc_edit);break;
                case "Runtime": mymovie.setRuntime(string_category_desc_edit);break;
                case "Genre": mymovie.setGenre(string_category_desc_edit);break;
                case "Director": mymovie.setDirector(string_category_desc_edit);break;
                case "Writer": mymovie.setWriter(string_category_desc_edit);break;
                case "Actors": mymovie.setActors(string_category_desc_edit);break;
                case "Plot": mymovie.setPlot(string_category_desc_edit);break;
                case "Language": mymovie.setLanguage(string_category_desc_edit);break;
                case "Country": mymovie.setCountry(string_category_desc_edit);break;
                case "Awards": mymovie.setAwards(string_category_desc_edit);break;
                case "Poster": mymovie.setPoster(string_category_desc_edit);break;
                case "Metascore": mymovie.setMetascore(string_category_desc_edit);break;
                case "imdbRating": mymovie.setImdbrating(string_category_desc_edit);break;
                case "imdbVotes": mymovie.setImdbvotes(string_category_desc_edit);break;
                case "imdbID": mymovie.setImdbid(string_category_desc_edit);break;
                case "Type": mymovie.setType(string_category_desc_edit);break;
            }

        }

        return mymovie;

    }

    public boolean isTitleFieldPopulated(){

        RecyclerView.ViewHolder holder = rv_edit.findViewHolderForAdapterPosition(0);
        View view = holder.itemView;
        EditText et_category_desc_edit =  (EditText)view.findViewById(R.id.et_category_desc_edit);
        if (et_category_desc_edit.getText().toString().isEmpty()){
            return false;
        }

        return true;
    }

    public void assignValuesFromTask_GetMyMovieDetails(RecyclerViewAdapter_Edit rva_edit){
        this.rva_edit = rva_edit;
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
