package com.semicolon.librarians.libraryguide.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.librarians.libraryguide.Models.NewsModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Delta on 22/01/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>  {

    List<NewsModel> newsModelList;
    Context context;

    public NewsAdapter(List<NewsModel> newsModelList, Context context) {
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
        NewsModel NewsModel = newsModelList.get(position);
        holder.BindData(NewsModel);
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
        TextView news_Title,news_Date,news_Detail,more;
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
            more = (TextView) itemView.findViewById(R.id.more);
        }

        private void initContentView(View contentView) {


            Title_news_image       = (ImageView) contentView.findViewById(R.id.item_news_image);
            news_Title        = (TextView) contentView.findViewById(R.id.item_news_title);
            news_Date         = (TextView) contentView.findViewById(R.id.item_news_date);
            news_Detail      = (TextView) contentView.findViewById(R.id.item_news_detail);

        }
        public void BindData(NewsModel NewsModel)
        {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(),Tags.font);

            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Title_news_image.setImageBitmap(bitmap);
                    //Title_news_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(context).load(Uri.parse(Tags.image_path+ NewsModel.getnews_photo())).into(target);
            //Picasso.with(context).load(Uri.parse(Tags.image_path+ NewsModel.getnews_photo())).into(target);


            Long l_date = Long.parseLong(NewsModel.getnews_date().toString());
            String date = new SimpleDateFormat("dd,MMM,yyyy", Locale.getDefault()).format(l_date);

            news_Title.setTypeface(typeface);
            news_Detail.setTypeface(typeface);
            news_Date.setTypeface(typeface);
            more.setTypeface(typeface);

            news_Title.setText(NewsModel.getnews_title().toString());
            news_Detail.setText(NewsModel.getnews_details());
            news_Date.setText(date.toString());





        }
    }




}

