package com.semicolon.librarians.library.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class Library_Search_Adapter extends RecyclerView.Adapter<Library_Search_Adapter.ViewHolder>  {

    List<LibraryModel> libraryModelList;
    Context context;

    public Library_Search_Adapter(List<LibraryModel> libraryModelList, Context context) {
        this.libraryModelList = libraryModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.library_search_recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        LibraryModel libraryModel = libraryModelList.get(position);
        holder.BindData(libraryModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return libraryModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        Target target;
        ImageView lib_image;
        TextView lib_name,libCountry,libService;

        public ViewHolder(View itemView) {
            super(itemView);

            initTitleView(itemView);


        }



        private void initTitleView(View titleView) {
            lib_image       = (ImageView) titleView.findViewById(R.id.library_image_item);
            lib_name        = (TextView) titleView.findViewById(R.id.library_name_item);
            libCountry        = (TextView) titleView.findViewById(R.id.library_country_item);
            libService        = (TextView) titleView.findViewById(R.id.library_service_item);


        }
        public void BindData(LibraryModel libraryModel)
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    lib_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };



            lib_name.setText(libraryModel.getLib_name().toString());
            libCountry.setText(libraryModel.getLib_country().toString());
            libService.setText("");




        }
    }



}

