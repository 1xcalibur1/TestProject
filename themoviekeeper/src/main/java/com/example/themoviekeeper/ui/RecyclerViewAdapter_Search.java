package com.example.themoviekeeper.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.themoviekeeper.R;
import com.example.themoviekeeper.db.MyMovie;
import com.example.themoviekeeper.db.MyMovieHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.themoviekeeper.db.DBConstants.CALL_EDITMYMOVIEACTIVITY_SEARCH;

/**
 * Created by Xcalibur on 30/12/2016.
 */

public class RecyclerViewAdapter_Search extends RecyclerView.Adapter<RecyclerViewAdapter_Search.MyViewHolder>{

    private ArrayList<MyMovie> _list;
    private Activity _activity;
    private LayoutInflater inflater;

    public RecyclerViewAdapter_Search(ArrayList<MyMovie> list, Activity activity) {

        _list = list;
        _activity = activity;

        inflater = LayoutInflater.from(_activity);
    }

    @Override
    public RecyclerViewAdapter_Search.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.view_search, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String year = _list.get(position).getYear();
        String title = _list.get(position).getTitle();
        String type = _list.get(position).getType();
        String poster = _list.get(position).getPoster();

        holder.tv_year.setText(year);
        holder.tv_type.setText(type);
        holder.tv_title.setText(title);

        if (poster != null && poster.length()>0){

            Picasso.with(_activity).load(poster).placeholder(R.drawable.movie_default).into(holder.iv_poster);
        }
        else{
            Picasso.with(_activity).load(R.drawable.movie_default).into(holder.iv_poster);
        }

    }


    @Override
    public int getItemCount() {
        return _list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_year,tv_title,tv_type;
        public ImageView iv_poster;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView)itemView.findViewById(R.id.tv_title_searchview);
            tv_type = (TextView)itemView.findViewById(R.id.tv_type_searchview);
            tv_year = (TextView)itemView.findViewById(R.id.tv_year_searchview);
            iv_poster = (ImageView)itemView.findViewById(R.id.iv_searchview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String titlename = tv_title.getText().toString();
                    MyMovieHelper movieHelper = new MyMovieHelper(_activity);

                    if (movieHelper.checkMovieTitleExist(titlename)){

                        Toast.makeText(_activity, "Movie already in local db, " +
                                "please use main window for editing", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        Intent intent = new Intent(_activity,EditMyMovieActivity.class);
                        intent.putExtra("FromSearchActivity",true);
                        intent.putExtra("Add",true);
                        intent.putExtra("imdbid",_list.get(getLayoutPosition()).getImdbid());
                        _activity.startActivityForResult(intent,CALL_EDITMYMOVIEACTIVITY_SEARCH);

                    }

                }
            });

        }

    }


}
