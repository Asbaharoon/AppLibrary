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

import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class Publisher_Search_Adapter extends RecyclerView.Adapter<Publisher_Search_Adapter.ViewHolder>  {

    List<PublisherModel> PublisherModelList;
    Context context;

    public Publisher_Search_Adapter(List<PublisherModel> PublisherModelList, Context context) {
        this.PublisherModelList = PublisherModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.publisher_search_recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PublisherModel PublishersModel = PublisherModelList.get(position);
        holder.BindData(PublishersModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return PublisherModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        Target target;
        ImageView publisher_image;
        TextView publisher_name;

        public ViewHolder(View itemView) {
            super(itemView);

            initTitleView(itemView);


        }



        private void initTitleView(View titleView) {
            publisher_image       = (ImageView) titleView.findViewById(R.id.publisher_image);
            publisher_name        = (TextView) titleView.findViewById(R.id.publisher_name);


       }
        public void BindData(PublisherModel publisherModel)
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    publisher_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };



            publisher_name.setText(publisherModel.getPub_name().toString());





        }
    }



}

