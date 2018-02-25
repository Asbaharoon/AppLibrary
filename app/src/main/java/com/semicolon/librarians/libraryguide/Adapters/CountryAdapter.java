package com.semicolon.librarians.libraryguide.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.semicolon.librarians.libraryguide.Activities.Activity_Search_Results;
import com.semicolon.librarians.libraryguide.Models.CountriesModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>  {

    List<CountriesModel>countriesModels;
    Context context;
    Activity_Search_Results activity_search_results;

    public CountryAdapter(List<CountriesModel> countriesModels, Context context) {
        this.countriesModels = countriesModels;
        this.context = context;

            this.activity_search_results = (Activity_Search_Results) context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position) {
        CountriesModel countriesModel = countriesModels.get(position);
        holder.BindData(countriesModel);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountriesModel countriesModel = countriesModels.get(holder.getAdapterPosition());
                Intent intent = activity_search_results.getIntent();
                intent.putExtra("country_data",countriesModel);
                activity_search_results.setResult(Activity.RESULT_OK,intent);
                activity_search_results.finish();



            }
        });

    }

    @Override
    public int getItemCount() {
        return countriesModels.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView countryName;
        public ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.countryName);


        }

        public void BindData(CountriesModel countriesModel)
        {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), Tags.font);

            countryName.setText(countriesModel.getCountry_title().toString());
            countryName.setTypeface(typeface);
        }
    }




}

