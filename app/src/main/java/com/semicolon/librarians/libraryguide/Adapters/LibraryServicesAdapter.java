package com.semicolon.librarians.libraryguide.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.semicolon.librarians.libraryguide.Activities.Activity_Search_Results;
import com.semicolon.librarians.libraryguide.Models.LibraryServices_Model;
import com.semicolon.librarians.libraryguide.R;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class LibraryServicesAdapter extends RecyclerView.Adapter<LibraryServicesAdapter.ViewHolder>  {

    List<LibraryServices_Model>libraryServicesModelList;
    Context context;
    Activity_Search_Results activity_search_results;

    public LibraryServicesAdapter(List<LibraryServices_Model> libraryServicesModelList, Context context) {
        this.libraryServicesModelList = libraryServicesModelList;
        this.context = context;
        this.activity_search_results = (Activity_Search_Results) context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.library_services_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position) {
        LibraryServices_Model libraryServicesModel = libraryServicesModelList.get(position);
        holder.BindData(libraryServicesModel);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LibraryServices_Model libraryServicesModel = libraryServicesModelList.get(holder.getAdapterPosition());
                Intent intent = activity_search_results.getIntent();
                intent.putExtra("libServices_data",libraryServicesModel);
                activity_search_results.setResult(Activity.RESULT_OK,intent);
                activity_search_results.finish();



            }
        });

    }

    @Override
    public int getItemCount() {
        return libraryServicesModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView lib_service;
        public ViewHolder(View itemView) {
            super(itemView);
            lib_service = (TextView) itemView.findViewById(R.id.libService);


        }

        public void BindData(LibraryServices_Model libraryServices_model)
        {
            lib_service.setText(libraryServices_model.getLib_service_title().toString());
        }
    }




}

