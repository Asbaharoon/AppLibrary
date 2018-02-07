package com.semicolon.librarians.library.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.librarians.library.Activities.Activity_Profile;
import com.semicolon.librarians.library.Activities.Chat_Activity;
import com.semicolon.librarians.library.Activities.HomeActivity;
import com.semicolon.librarians.library.Models.UniversityModel;
import com.semicolon.librarians.library.R;
import com.squareup.picasso.Picasso;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.ViewHolder>  {

    List<UniversityModel> universityModelList;
    Context context;
    HomeActivity homeActivity;

    public UniversityAdapter(List<UniversityModel> universityModelList, Context context) {
        this.universityModelList = universityModelList;
        this.context = context;
        homeActivity = (HomeActivity) context;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final UniversityModel universityModel2 = universityModelList.get(holder.getAdapterPosition());
                View custom_view = LayoutInflater.from(context).inflate(R.layout.custom_alert_msg_profile,null);
                TextView open_profile = (TextView) custom_view.findViewById(R.id.open_profile_tv);
                TextView send_msg = (TextView) custom_view.findViewById(R.id.send_msg_tv);
                Button cancelBtn = (Button) custom_view.findViewById(R.id.cancelBtn);

                final LovelyCustomDialog dialog = new LovelyCustomDialog(context);
                dialog.setCancelable(true);
                dialog.setTopTitle("Select Options");
                dialog.setTopColor(ActivityCompat.getColor(context,R.color.centercolor));
                dialog.setTopTitleColor(ActivityCompat.getColor(context,R.color.base));

                open_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (homeActivity.user_Data!=null)
                        {
                            Intent intent = new Intent(homeActivity,Activity_Profile.class);
                            intent.putExtra("who_visit_myProfile","visitor");
                            intent.putExtra("userData",homeActivity.user_Data);
                            context.startActivity(intent);
                            dialog.dismiss();

                        }else if (homeActivity.publisher_Model!=null)
                        {
                            Intent intent = new Intent(homeActivity,Activity_Profile.class);
                            intent.putExtra("who_visit_myProfile","visitor");
                            intent.putExtra("publisherData",homeActivity.publisher_Model);
                            context.startActivity(intent);

                            dialog.dismiss();


                        }else if (homeActivity.library_Model!=null)
                        {
                            Intent intent = new Intent(homeActivity,Activity_Profile.class);
                            intent.putExtra("who_visit_myProfile","visitor");
                            intent.putExtra("libraryData",homeActivity.library_Model);
                            context.startActivity(intent);

                            dialog.dismiss();

                        }else if (homeActivity.university_Model!=null)
                        {
                            Intent intent = new Intent(homeActivity,Activity_Profile.class);
                            intent.putExtra("who_visit_myProfile","visitor");
                            intent.putExtra("universityData",homeActivity.university_Model);
                            context.startActivity(intent);

                            dialog.dismiss();

                        }
                        else if (homeActivity.company_Model!=null)
                        {
                            Intent intent = new Intent(homeActivity,Activity_Profile.class);
                            intent.putExtra("who_visit_myProfile","visitor");
                            intent.putExtra("companyData",homeActivity.company_Model);
                            context.startActivity(intent);

                            dialog.dismiss();

                        }
                    }
                });
                send_msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (homeActivity.user_Data!=null)
                        {
                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","user");
                            intent.putExtra("chat_userType","university");
                            intent.putExtra("curr_user",homeActivity.user_Data);
                            intent.putExtra("chat_user",universityModel2);
                            context.startActivity(intent);
                            dialog.dismiss();
                        }else if (homeActivity.publisher_Model!=null)
                        {
                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","publisher");
                            intent.putExtra("chat_userType","university");
                            intent.putExtra("curr_user",homeActivity.publisher_Model);
                            intent.putExtra("chat_user",universityModel2);
                            context.startActivity(intent);
                            dialog.dismiss();

                        }
                        else if (homeActivity.library_Model!=null)
                        {
                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","library");
                            intent.putExtra("chat_userType","university");
                            intent.putExtra("curr_user",homeActivity.library_Model);
                            intent.putExtra("chat_user",universityModel2);
                            context.startActivity(intent);
                            dialog.dismiss();

                        }
                        else if (homeActivity.university_Model!=null)
                        {
                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","university");
                            intent.putExtra("chat_userType","university");
                            intent.putExtra("curr_user",homeActivity.university_Model);
                            intent.putExtra("chat_user",universityModel2);
                            context.startActivity(intent);
                            dialog.dismiss();

                        }
                        else if (homeActivity.company_Model!=null)
                        {
                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","company");
                            intent.putExtra("chat_userType","university");
                            intent.putExtra("curr_user",homeActivity.company_Model);
                            intent.putExtra("chat_user",universityModel2);
                            context.startActivity(intent);
                            dialog.dismiss();

                        }
                    }
                });
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.setView(custom_view);
                dialog.create();
                dialog.show();
            }
        });

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

