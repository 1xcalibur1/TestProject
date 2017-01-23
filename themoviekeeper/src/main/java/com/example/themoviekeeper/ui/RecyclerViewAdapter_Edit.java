package com.example.themoviekeeper.ui;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.themoviekeeper.R;
import com.example.themoviekeeper.db.MyMovieCategory;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.support.v7.recyclerview.R.styleable.RecyclerView;

/**
 * Created by Xcalibur on 01/01/2017.
 */

public class RecyclerViewAdapter_Edit extends RecyclerView.Adapter<RecyclerViewAdapter_Edit.MyViewHolder> {

    private Activity activity;
    private ArrayList<MyMovieCategory> myMovieCategoryArrayList;
    private LayoutInflater inflater;

    public RecyclerViewAdapter_Edit(Activity activity, ArrayList<MyMovieCategory> myMovieCategoryArrayList) {

        this.activity = activity;
        this.myMovieCategoryArrayList = myMovieCategoryArrayList;

        inflater = LayoutInflater.from(activity);

    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = inflater.inflate(R.layout.view_edit, parent, false);

        RecyclerViewAdapter_Edit.MyViewHolder holder = new RecyclerViewAdapter_Edit.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String description_name = myMovieCategoryArrayList.get(position).getCategory_name();
        String description_content = myMovieCategoryArrayList.get(position).getCategory_description();

        holder.tv_view_edit_description_name.setText(description_name);
        holder.et_view_edit_description.setText(description_content);

        switch (description_name){

            case "Title":
                holder.iv_view_edit_icon.setImageResource(R.drawable.ic_title_black_24dp);
                break;
            case "Year":
                holder.iv_view_edit_icon.setImageResource(R.drawable.timetable);
                break;
            case "Rated": holder.iv_view_edit_icon.setImageResource(R.drawable.rating_24);
                break;
            case "Released": holder.iv_view_edit_icon.setImageResource(R.drawable.news_24);
                break;
            case "Runtime": holder.iv_view_edit_icon.setImageResource(R.drawable.timer);
                break;
            case "Genre": holder.iv_view_edit_icon.setImageResource(R.drawable.adventures_24);
                break;
            case "Director": holder.iv_view_edit_icon.setImageResource(R.drawable.literature_24);
                break;
            case "Writer": holder.iv_view_edit_icon.setImageResource(R.drawable.school_director_24);
                break;
            case "Actors": holder.iv_view_edit_icon.setImageResource(R.drawable.cast_24);
                break;
            case "Plot": holder.iv_view_edit_icon.setImageResource(R.drawable.books_24);
                break;
            case "Language": holder.iv_view_edit_icon.setImageResource(R.drawable.language_24);
                break;
            case "Country": holder.iv_view_edit_icon.setImageResource(R.drawable.worldwide_location_24);
                break;
            case "Awards": holder.iv_view_edit_icon.setImageResource(R.drawable.trophy_24);
                break;
            case "Poster": holder.iv_view_edit_icon.setImageResource(R.drawable.poster_24);
                break;
            case "Metascore": holder.iv_view_edit_icon.setImageResource(R.drawable.ic_rate_review_black_24dp);
                break;
            case "imdbRating": holder.iv_view_edit_icon.setImageResource(R.drawable.star_24);
                break;
            case "imdbVotes": holder.iv_view_edit_icon.setImageResource(R.drawable.thumb_up_24);
                break;
            case "imdbID": holder.iv_view_edit_icon.setImageResource(R.drawable.ic_perm_identity_black_24dp);
                break;
            case "Type": holder.iv_view_edit_icon.setImageResource(R.drawable.ic_merge_type_black_24dp);
                break;

        }
    }



    @Override
    public int getItemCount() {
        return myMovieCategoryArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private  TextView tv_view_edit_description_name;
        private  EditText et_view_edit_description;
        private ImageView iv_view_edit_icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_view_edit_description_name = (TextView)itemView.findViewById(R.id.tv_category_name_edit);
            et_view_edit_description = (EditText)itemView.findViewById(R.id.et_category_desc_edit);
            iv_view_edit_icon = (ImageView)itemView.findViewById(R.id.iv_edit);
        }
    }

    public MyMovieCategory getItem(int position){
        return myMovieCategoryArrayList.get(position);
    }
}
