package com.example.omd.library.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omd.library.Models.UniversityModel;
import com.example.omd.library.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class University_Search_Adapter extends RecyclerView.Adapter<University_Search_Adapter.ViewHolder>  {

    List<UniversityModel> universityModelList;
    Context context;

    public University_Search_Adapter(List<UniversityModel> universityModelList, Context context) {
        this.universityModelList = universityModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.university_search_recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        UniversityModel universityModel = universityModelList.get(position);
        holder.BindData(universityModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return universityModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        Target target;
        ImageView uni_image;
        TextView uni_name,uni_country;

        public ViewHolder(View itemView) {
            super(itemView);

            initTitleView(itemView);


        }



        private void initTitleView(View titleView) {
            uni_image       = (ImageView) titleView.findViewById(R.id.university_image);
            uni_name        = (TextView) titleView.findViewById(R.id.university_name);
            uni_country        = (TextView) titleView.findViewById(R.id.university_country);


       }
        public void BindData(UniversityModel universityModel)
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    uni_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };



            uni_name.setText(universityModel.getUni_name().toString());
            uni_country.setText(universityModel.getUni_country().toString());




        }
    }



}

