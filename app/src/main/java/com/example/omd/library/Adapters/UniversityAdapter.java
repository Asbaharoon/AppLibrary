package com.example.omd.library.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omd.library.Models.UniversityModel;
import com.example.omd.library.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.ViewHolder>  {

    List<UniversityModel> universityModelList;
    Context context;

    public UniversityAdapter(List<UniversityModel> universityModelList, Context context) {
        this.universityModelList = universityModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.uni_recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        UniversityModel universityModel = universityModelList.get(position);
        holder.BindData(universityModel);

    }

    @Override
    public int getItemCount() {
        return universityModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView uni_image;
        TextView uni_name;
        public ViewHolder(View itemView) {
            super(itemView);

            uni_image       = (ImageView) itemView.findViewById(R.id.university_image_item);
            uni_name        = (TextView) itemView.findViewById(R.id.university_name_item);



        }



        public void BindData(UniversityModel universityModel)
        {
            Picasso.with(context).load(R.drawable.header).into(uni_image);
            uni_name.setText(universityModel.getUni_name().toString());

        }
    }




}

