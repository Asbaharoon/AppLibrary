package com.semicolon.librarians.libraryguide.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.librarians.libraryguide.Activities.Activity_Profile;
import com.semicolon.librarians.libraryguide.Activities.Chat_Activity;
import com.semicolon.librarians.libraryguide.Activities.HomeActivity;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class PublisherAdapter extends RecyclerView.Adapter<PublisherAdapter.ViewHolder>  {

    List<PublisherModel> PublisherModelList;
    HomeActivity homeActivity;
    Context context;

    public PublisherAdapter(List<PublisherModel> PublisherModelList, Context context) {
        this.PublisherModelList = PublisherModelList;
        this.context = context;
        homeActivity = (HomeActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.publisher_recyclerview_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final PublisherModel PublishersModel = PublisherModelList.get(position);
        holder.BindData(PublishersModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PublisherModel publisherModel = PublisherModelList.get(holder.getAdapterPosition());
                View custom_view = LayoutInflater.from(context).inflate(R.layout.custom_alert_msg_profile,null);
                TextView open_profile = (TextView) custom_view.findViewById(R.id.open_profile_tv);
                TextView send_msg = (TextView) custom_view.findViewById(R.id.send_msg_tv);
                Button cancelBtn = (Button) custom_view.findViewById(R.id.cancelBtn);

                final LovelyCustomDialog dialog = new LovelyCustomDialog(context);
                dialog.setCancelable(true);
                dialog.setTopTitle(context.getString(R.string.sel_opt));
                dialog.setTopColor(ActivityCompat.getColor(context,R.color.centercolor));
                dialog.setTopTitleColor(ActivityCompat.getColor(context,R.color.base));

                open_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(homeActivity,Activity_Profile.class);
                        intent.putExtra("who_visit_myProfile","visitor");
                        intent.putExtra("publisherData",publisherModel);
                        context.startActivity(intent);

                        dialog.dismiss();

                    }
                });
                send_msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (homeActivity.user_Data!=null)
                        {
                            homeActivity.chatRoomPresenter.Create_ChatRoom(homeActivity.user_Data.getUserId(),publisherModel.getPub_username());
                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","user");
                            intent.putExtra("chat_userType","publisher");
                            intent.putExtra("curr_user",homeActivity.user_Data);
                            intent.putExtra("chat_user",publisherModel);
                            context.startActivity(intent);
                            dialog.dismiss();
                        }else if (homeActivity.publisher_Model!=null)
                        {
                            homeActivity.chatRoomPresenter.Create_ChatRoom(homeActivity.publisher_Model.getPub_username(),publisherModel.getPub_username());

                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","publisher");
                            intent.putExtra("chat_userType","publisher");
                            intent.putExtra("curr_user",homeActivity.publisher_Model);
                            intent.putExtra("chat_user",publisherModel);
                            context.startActivity(intent);
                            dialog.dismiss();

                        }
                        else if (homeActivity.library_Model!=null)
                        {
                            homeActivity.chatRoomPresenter.Create_ChatRoom(homeActivity.library_Model.getLib_username(),publisherModel.getPub_username());

                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","library");
                            intent.putExtra("chat_userType","publisher");
                            intent.putExtra("curr_user",homeActivity.library_Model);
                            intent.putExtra("chat_user",publisherModel);
                            context.startActivity(intent);
                            dialog.dismiss();

                        }
                        else if (homeActivity.university_Model!=null)
                        {
                            homeActivity.chatRoomPresenter.Create_ChatRoom(homeActivity.university_Model.getUni_username(),publisherModel.getPub_username());

                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","university");
                            intent.putExtra("chat_userType","publisher");
                            intent.putExtra("curr_user",homeActivity.university_Model);
                            intent.putExtra("chat_user",publisherModel);
                            context.startActivity(intent);
                            dialog.dismiss();

                        }
                        else if (homeActivity.company_Model!=null)
                        {
                            homeActivity.chatRoomPresenter.Create_ChatRoom(homeActivity.company_Model.getComp_username(),publisherModel.getPub_username());

                            Intent intent = new Intent(context, Chat_Activity.class);
                            intent.putExtra("curr_userType","company");
                            intent.putExtra("chat_userType","publisher");
                            intent.putExtra("curr_user",homeActivity.company_Model);
                            intent.putExtra("chat_user",publisherModel);
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
        return PublisherModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        Target target;
        ImageView publisher_image;
        TextView publisher_name,publisher_country,publisher_email,publisher_phone,publisher_site;

        public ViewHolder(View itemView) {
            super(itemView);

            initTitleView(itemView);


        }



        private void initTitleView(View titleView) {

            publisher_image       = (ImageView) titleView.findViewById(R.id.publisher_image);
            publisher_name        = (TextView) titleView.findViewById(R.id.publisher_name);
            publisher_country         = (TextView) titleView.findViewById(R.id.publisher_country);
            publisher_email      = (TextView) titleView.findViewById(R.id.publisher_email);
            publisher_site  = (TextView) titleView.findViewById(R.id.publisher_site);



       }
        public void BindData(PublisherModel publisherModel)
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    publisher_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            if (!publisherModel.getUser_photo().equals("0"))
            {
                Picasso.with(context).load(Tags.image_path+publisherModel.getUser_photo()).placeholder(R.drawable.user_profile).into(target);

            }else
                {
                    Picasso.with(context).load(R.drawable.user_profile).into(target);

                }

           /* Long sd = Long.getLong(PublisherModel.getPublishertartDate());
            Long ed = Long.getLong(PublisherModel.getJobEndDate());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MM,yy");
            String s_Date = dateFormat.format(new Date(sd));
            String e_Date = dateFormat.format(new Date(ed));*/


            publisher_name.setText(publisherModel.getPub_name().toString());
            publisher_country.setText(publisherModel.getPub_country().toString());
            publisher_email.setText(publisherModel.getPub_email());
            publisher_site.setText(publisherModel.getPub_website());
            Log.e("pubusername",""+publisherModel.getPub_username());




        }
    }



}

