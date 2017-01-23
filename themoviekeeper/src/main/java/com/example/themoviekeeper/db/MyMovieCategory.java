package com.example.themoviekeeper.db;

import android.widget.ImageView;

/**
 * Created by Xcalibur on 01/01/2017.
 */

public class MyMovieCategory    {

    private String category_name,category_description;

    public MyMovieCategory(String category_name, String category_description) {
        this.category_name = category_name;
        this.category_description = category_description;
    }

    public ImageView getIv_category() {

        return iv_category;
    }

    public void setIv_category(ImageView iv_category) {
        this.iv_category = iv_category;
    }

    private ImageView iv_category;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public MyMovieCategory() {

    }


}
