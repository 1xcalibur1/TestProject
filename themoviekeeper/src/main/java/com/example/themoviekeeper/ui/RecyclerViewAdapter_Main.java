package com.example.themoviekeeper.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.themoviekeeper.R;
import com.example.themoviekeeper.db.MyMovie;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import static com.example.themoviekeeper.db.DBConstants.CALL_EDITMYMOVIEACTIVITY_UPDATE;

/**
 * Created by Xcalibur on 07/01/2017.
 */

public class RecyclerViewAdapter_Main extends RecyclerView.Adapter<RecyclerViewAdapter_Main.MyViewHolder> {

    private ArrayList<MyMovie> _list;
    private Activity _activity;
    private LayoutInflater inflater;

    public RecyclerViewAdapter_Main(ArrayList<MyMovie> list, Activity activity) {

        _list = list;
        _activity = activity;

        inflater = LayoutInflater.from(_activity);
    }


    @Override
    public RecyclerViewAdapter_Main.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_main, parent, false);

        RecyclerViewAdapter_Main.MyViewHolder holder = new RecyclerViewAdapter_Main.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_Main.MyViewHolder holder, int position) {

        holder.tv_moviename_rcva_main.setText(_list.get(position).getTitle());
        //holder.tv_duration_rcva_main.setText(_list.get(position).getRuntime());
        holder.tv_imdbrating_rcva_main.setText(_list.get(position).getImdbrating());
        holder.tv_year_rcva_main.setText(_list.get(position).getYear());
        holder.rb_rcva_main.setRating(_list.get(position).getPersonalRating());
        //holder.tv_metascore_rcva_main.setText(_list.get(position).getMetascore());


        String imgUrlOrPath = _list.get(position).getPoster();

        if (imgUrlOrPath != null && imgUrlOrPath.length()>0){

            if (imgUrlOrPath.contains("storage") ||
                    imgUrlOrPath.contains("sdcard")){
                Picasso.with(_activity).load(new File(imgUrlOrPath)).into(holder.iv_rcva_main);
            }
            if (imgUrlOrPath.contains("http://") ||
                    imgUrlOrPath.contains("https://") ){
                Picasso.with(_activity).load(imgUrlOrPath).into(holder.iv_rcva_main);
            }
        }

    }

    @Override
    public int getItemCount() {
        return _list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_rcva_main;
        private TextView tv_moviename_rcva_main,tv_year_rcva_main,
        tv_imdbrating_rcva_main,tv_duration_rcva_main,tv_metascore_rcva_main;

        private RatingBar rb_rcva_main;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv_rcva_main = (ImageView)itemView.findViewById(R.id.iv_main_cv);
            tv_moviename_rcva_main = (TextView)itemView.findViewById(R.id.tv_moviename_main_cv);
            tv_year_rcva_main = (TextView)itemView.findViewById(R.id.tv_year_mainactivity);
            tv_imdbrating_rcva_main = (TextView)itemView.findViewById(R.id.tv_imdbRating_mainactivity);
            //tv_duration_rcva_main = (TextView)itemView.findViewById(R.id.tv_duration_mainactivity);
            rb_rcva_main = (RatingBar)itemView.findViewById(R.id.rb_mainactiviry);
            //tv_metascore_rcva_main = (TextView)itemView.findViewById(R.id.tv_metascore_mainactivity);
            

//            Drawable drawable = rb_rcva_main.getProgressDrawable();
//            drawable.setColorFilter(Color.parseColor("#C10E19"), PorterDuff.Mode.SRC_ATOP);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(_activity,EditMyMovieActivity.class);
                    intent.putExtra("FromMainActivity",true);
                    intent.putExtra("Update",true);
                    intent.putExtra("Id",getLayoutPosition()+1);

                    _activity.startActivityForResult(intent,CALL_EDITMYMOVIEACTIVITY_UPDATE);
                }
            });
        }
    }

    public MyMovie getItem(int position) {
        return _list.get(position);
    }
}
