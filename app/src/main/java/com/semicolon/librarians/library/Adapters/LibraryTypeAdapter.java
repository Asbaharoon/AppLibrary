package com.semicolon.librarians.library.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.semicolon.librarians.library.Activities.Activity_Search_Results;
import com.semicolon.librarians.library.Models.LibraryType_Model;
import com.semicolon.librarians.library.R;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class LibraryTypeAdapter extends RecyclerView.Adapter<LibraryTypeAdapter.ViewHolder>  {

    List<LibraryType_Model>libraryTypeModelList;
    Context context;
    Activity_Search_Results activity_search_results;

    public LibraryTypeAdapter(List<LibraryType_Model> libraryTypeModelList, Context context) {
        this.libraryTypeModelList = libraryTypeModelList;
        this.context = context;
        this.activity_search_results = (Activity_Search_Results) context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.library_type_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position) {
        final LibraryType_Model libraryType_model = libraryTypeModelList.get(position);
        holder.BindData(libraryType_model);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LibraryType_Model libraryType_model = libraryTypeModelList.get(holder.getAdapterPosition());
                Intent intent = activity_search_results.getIntent();
                intent.putExtra("libType_data",libraryType_model);
                activity_search_results.setResult(Activity.RESULT_OK,intent);
                activity_search_results.finish();



            }
        });

    }

    @Override
    public int getItemCount() {
        return libraryTypeModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView lib_type;
        public ViewHolder(View itemView) {
            super(itemView);
            lib_type = (TextView) itemView.findViewById(R.id.libType);


        }

        public void BindData(LibraryType_Model libraryTypeModel)
        {
            lib_type.setText(libraryTypeModel.getLib_type_title());
        }
    }




}

