package com.example.omd.library.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.R;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder>  {

    List<CompanyModel> companyModelList;
    Context context;

    public CompanyAdapter(List<CompanyModel> companyModelList, Context context) {
        this.companyModelList = companyModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.company_recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        CompanyModel companyModel = companyModelList.get(position);
        holder.BindData(companyModel);

    }

    @Override
    public int getItemCount() {
        return companyModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView comp_image;
        TextView comp_name,comp_country,comp_email,comp_phone,comp_site;

        public ViewHolder(View itemView) {
            super(itemView);
            comp_image = (ImageView) itemView.findViewById(R.id.company_image);
            comp_name  = (TextView) itemView.findViewById(R.id.company_name);
            comp_phone = (TextView) itemView.findViewById(R.id.company_phone);
            comp_country = (TextView) itemView.findViewById(R.id.company_country);
            comp_email = (TextView) itemView.findViewById(R.id.company_email);
            comp_site  = (TextView) itemView.findViewById(R.id.company_site);


        }




        public void BindData(CompanyModel companyModel)
        {
            comp_image.setImageResource(R.drawable.user_profile);
            comp_name.setText(companyModel.getComp_name().toString());
            comp_phone.setText(companyModel.getComp_phone().toString());
            comp_country.setText(companyModel.getComp_country());
            comp_site.setText(companyModel.getComp_site().toString());
            comp_email.setText(companyModel.getComp2_email().toString());

        }
    }



}

