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

import com.example.omd.library.Models.newsModel;
import com.example.omd.library.R;
import com.example.omd.library.Services.Tags;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.ViewHolder>  {

    List<newsModel> newsModelList;
    Context context;

    public newsAdapter(List<newsModel> newsModelList, Context context) {
        this.newsModelList = newsModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        newsModel newsModel = newsModelList.get(position);
        holder.BindData(newsModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.foldCell.toggle(false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        Target target;

        FoldingCell foldCell;
        ImageView Title_news_image;
        TextView news_Title,news_Date,news_Detail;
        public ViewHolder(View itemView) {
            super(itemView);

            foldCell = (FoldingCell) itemView.findViewById(R.id.foldCell);
            ////////////////////////////////////////////////////////////
            initTitleView(itemView);

            /////////////////////////////////////////////////////////////
            initContentView(itemView);


        }



        private void initTitleView(View titleView) {
            Title_news_image       = (ImageView) titleView.findViewById(R.id.item_news_image);
            news_Title        = (TextView) titleView.findViewById(R.id.item_news_title);
            news_Date         = (TextView) titleView.findViewById(R.id.item_news_date);
            news_Detail      = (TextView) titleView.findViewById(R.id.item_news_detail);
        }

        private void initContentView(View contentView) {


            Title_news_image       = (ImageView) contentView.findViewById(R.id.item_news_image);
            news_Title        = (TextView) contentView.findViewById(R.id.item_news_title);
            news_Date         = (TextView) contentView.findViewById(R.id.item_news_date);
            news_Detail      = (TextView) contentView.findViewById(R.id.item_news_detail);

        }
        public void BindData(newsModel newsModel)
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Title_news_image.setImageBitmap(bitmap);
                    Title_news_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(context).load(Uri.parse(Tags.image_path+newsModel.getnews_photo())).into(target);
            Picasso.with(context).load(Uri.parse(Tags.image_path+newsModel.getnews_photo())).into(target);

           /* Long sd = Long.getLong(newsModel.getnewstartDate());
            Long ed = Long.getLong(newsModel.getJobEndDate());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MM,yy");
            String s_Date = dateFormat.format(new Date(sd));
            String e_Date = dateFormat.format(new Date(ed));*/


            news_Title.setText(newsModel.getnews_title().toString());

            news_Detail.setText(newsModel.getnews_details());



            news_Date.setText(newsModel.getnews_date().toString());





        }
    }



}

