package com.example.omd.library.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omd.library.Models.PublishersModel;
import com.example.omd.library.R;
import com.example.omd.library.Services.Tags;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class publisherAdapter extends RecyclerView.Adapter<publisherAdapter.ViewHolder>  {

    List<PublishersModel> PublisherModelList;
    Context context;

    public publisherAdapter(List<PublishersModel> PublisherModelList, Context context) {
        this.PublisherModelList = PublisherModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PublishersModel PublishersModel = PublisherModelList.get(position);
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
            publisher_phone  = (TextView) titleView.findViewById(R.id.publisher_phone);
            publisher_site  = (TextView) titleView.findViewById(R.id.publisher_site);

       }
        public void BindData(PublishersModel PublisherModel)
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


            publisher_name.setText(PublishersModel.getPub_name().toString());

            publisher_country.setText(PublishersModel.getPub_country().toString());
            publisher_phone.setText(PublishersModel.getPub_phone());
            publisher_email.setText(PublishersModel.getPub_email());
            publisher_site.setText(PublishersModel.getPub_website());




        }
    }



}

