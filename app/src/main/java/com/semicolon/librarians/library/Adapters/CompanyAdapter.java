package com.semicolon.librarians.library.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.R;
import com.semicolon.librarians.library.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder>  {

    List<CompanyModel> companyModelList;
    Context context;
    HomeActivity homeActivity;
    Target target;

    public CompanyAdapter(List<CompanyModel> companyModelList, Context context) {
        this.companyModelList = companyModelList;
        this.context = context;
        homeActivity = (HomeActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.company_recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CompanyModel companyModel = companyModelList.get(position);
        holder.BindData(companyModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CompanyModel companyModel2 = companyModelList.get(holder.getAdapterPosition());
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
                        Intent intent = new Intent(context,Activity_Profile.class);
                        intent.putExtra("who_visit_myProfile","visitor");
                        intent.putExtra("companyData",companyModel2);
                        context.startActivity(intent);

                        dialog.dismiss();
                        /*if (homeActivity.user_Data!=null)
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

                        }*/
                    }
                });
                send_msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (homeActivity.user_Data!=null)
                        {
                            homeActivity.chatRoomPresenter.Create_ChatRoom(homeActivity.user_Data.getUserId(),companyModel2.getComp_username());

                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","user");
                            intent.putExtra("chat_userType","company");
                            intent.putExtra("curr_user",homeActivity.user_Data);
                            intent.putExtra("chat_user",companyModel2);
                            context.startActivity(intent);
                            dialog.dismiss();
                        }else if (homeActivity.publisher_Model!=null)
                        {
                            homeActivity.chatRoomPresenter.Create_ChatRoom(homeActivity.publisher_Model.getPub_username(),companyModel2.getComp_username());

                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","publisher");
                            intent.putExtra("chat_userType","company");
                            intent.putExtra("curr_user",homeActivity.publisher_Model);
                            intent.putExtra("chat_user",companyModel2);
                            context.startActivity(intent);
                            dialog.dismiss();

                        }
                        else if (homeActivity.library_Model!=null)
                        {
                            homeActivity.chatRoomPresenter.Create_ChatRoom(homeActivity.library_Model.getLib_username(),companyModel2.getComp_username());

                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","library");
                            intent.putExtra("chat_userType","company");
                            intent.putExtra("curr_user",homeActivity.library_Model);
                            intent.putExtra("chat_user",companyModel2);
                            context.startActivity(intent);
                            dialog.dismiss();

                        }
                        else if (homeActivity.university_Model!=null)
                        {
                            homeActivity.chatRoomPresenter.Create_ChatRoom(homeActivity.university_Model.getUni_username(),companyModel2.getComp_username());

                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","university");
                            intent.putExtra("chat_userType","company");
                            intent.putExtra("curr_user",homeActivity.university_Model);
                            intent.putExtra("chat_user",companyModel2);
                            context.startActivity(intent);
                            dialog.dismiss();

                        }
                        else if (homeActivity.company_Model!=null)
                        {
                            homeActivity.chatRoomPresenter.Create_ChatRoom(homeActivity.company_Model.getComp_username(),companyModel2.getComp_username());

                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","company");
                            intent.putExtra("chat_userType","company");
                            intent.putExtra("curr_user",homeActivity.company_Model);
                            intent.putExtra("chat_user",companyModel2);
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
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    comp_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            if (!companyModel.getUser_photo().equals("0"))
            {
                Picasso.with(context).load(Tags.image_path+companyModel.getUser_photo()).placeholder(R.drawable.user_profile).into(target);

            }else
            {
                Picasso.with(context).load(R.drawable.user_profile).into(target);

            }


            comp_name.setText(companyModel.getComp_name().toString());
            comp_phone.setText(companyModel.getComp_phone().toString());
            comp_country.setText(companyModel.getComp_country());
            comp_site.setText(companyModel.getComp_site().toString());
            comp_email.setText(companyModel.getComp2_email().toString());

        }
    }



}

