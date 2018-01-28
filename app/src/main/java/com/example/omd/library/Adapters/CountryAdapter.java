package com.example.omd.library.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.omd.library.Activities.Activity_Countries;
import com.example.omd.library.Models.CountriesModel;
import com.example.omd.library.R;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>  {

    List<CountriesModel>countriesModels;
    Context context;
    Activity_Countries activityCountries;

    public CountryAdapter(List<CountriesModel> countriesModels, Context context) {
        this.countriesModels = countriesModels;
        this.context = context;
        this.activityCountries = (Activity_Countries) context;
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
                Intent intent = activityCountries.getIntent();
                intent.putExtra("country_data",countriesModel);
                activityCountries.setResult(activityCountries.RESULT_OK,intent);
                activityCountries.finish();

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
            countryName.setText(countriesModel.getCountry_title().toString());
        }
    }




}

