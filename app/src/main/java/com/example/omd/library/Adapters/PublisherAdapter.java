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

public class PublisherAdapter extends RecyclerView.Adapter<PublisherAdapter.ViewHolder>  {

    List<PublisherModel> PublisherModelList;
    Context context;

    public PublisherAdapter(List<PublisherModel> PublisherModelList, Context context) {
        this.PublisherModelList = PublisherModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.publisher_recyclerview_row,parent,false);

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
        TextView publisher_name,publisher_country,publisher_email,publisher_phone,publisher_site;

        public ViewHolder(View itemView) {
            super(itemView);

            initTitleView(itemView);


        }



        private void initTitleView(View titleView) {
            publisher_image       = (ImageView) titleView.findViewById(R.id.publisher_image);
            publisher_name        = (TextView) titleView.findViewById(R.id.publisher_name);
            publisher_country         = (TextView) titleView.findViewById(R.id.publisher_country);
            publisher_email      = (TextView) titleView.findViewById(R.id.publisher_email);
            publisher_site  = (TextView) titleView.findViewById(R.id.publisher_site);

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

           /* Long sd = Long.getLong(PublisherModel.getPublishertartDate());
            Long ed = Long.getLong(PublisherModel.getJobEndDate());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MM,yy");
            String s_Date = dateFormat.format(new Date(sd));
            String e_Date = dateFormat.format(new Date(ed));*/


            publisher_name.setText(publisherModel.getPub_name().toString());
            publisher_country.setText(publisherModel.getPub_country().toString());
            publisher_email.setText(publisherModel.getPub_email());
            publisher_site.setText(publisherModel.getPub_website());




        }
    }



}

