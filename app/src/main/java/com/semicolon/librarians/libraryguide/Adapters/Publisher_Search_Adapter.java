package com.semicolon.librarians.libraryguide.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.librarians.libraryguide.Activities.Activity_Profile;
import com.semicolon.librarians.libraryguide.Activities.Activity_Search_Results;
import com.semicolon.librarians.libraryguide.Activities.Chat_Activity;
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

public class Publisher_Search_Adapter extends RecyclerView.Adapter<Publisher_Search_Adapter.ViewHolder>  {

    List<PublisherModel> PublisherModelList;
    Context context;
    Activity_Search_Results activity_search_results;



    public Publisher_Search_Adapter(List<PublisherModel> PublisherModelList, Context context) {
        this.PublisherModelList = PublisherModelList;
        this.context = context;
        this.activity_search_results= (Activity_Search_Results) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.publisher_search_recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PublisherModel PublishersModel = PublisherModelList.get(position);
        holder.BindData(PublishersModel);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.rec_anim);
        holder.itemView.startAnimation(animation);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PublisherModel publisherModel = PublisherModelList.get(holder.getAdapterPosition());

                if (activity_search_results.publisherModel!=null)
                {
                    if (publisherModel.getPub_username().equals(activity_search_results.publisherModel.getPub_username()))
                    {
                        Intent intent = new Intent(context,Activity_Profile.class);
                        intent.putExtra("who_visit_myProfile","me");
                        intent.putExtra("publisherData",activity_search_results.publisherModel);
                        context.startActivity(intent);

                    }
                    else
                    {
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

                                Intent intent = new Intent(context,Activity_Profile.class);
                                intent.putExtra("who_visit_myProfile","visitor");
                                intent.putExtra("publisherData",publisherModel);
                                context.startActivity(intent);

                                dialog.dismiss();

                            }
                        });
                        send_msg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (activity_search_results.userData!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.userData.getUserId(),publisherModel.getPub_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","user");
                                    intent.putExtra("chat_userType","publisher");
                                    intent.putExtra("curr_user",activity_search_results.userData);
                                    intent.putExtra("chat_user",publisherModel);
                                    context.startActivity(intent);
                                    dialog.dismiss();
                                }else if (activity_search_results.publisherModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.publisherModel.getPub_username(),publisherModel.getPub_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","publisher");
                                    intent.putExtra("chat_userType","publisher");
                                    intent.putExtra("curr_user",activity_search_results.publisherModel);
                                    intent.putExtra("chat_user",publisherModel);
                                    context.startActivity(intent);
                                    dialog.dismiss();

                                }
                                else if (activity_search_results.libraryModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.libraryModel.getLib_username(),publisherModel.getPub_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","library");
                                    intent.putExtra("chat_userType","publisher");
                                    intent.putExtra("curr_user",activity_search_results.libraryModel);
                                    intent.putExtra("chat_user",publisherModel);
                                    context.startActivity(intent);
                                    dialog.dismiss();

                                }
                                else if (activity_search_results.universityModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.universityModel.getUni_username(),publisherModel.getPub_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","university");
                                    intent.putExtra("chat_userType","publisher");
                                    intent.putExtra("curr_user",activity_search_results.universityModel);
                                    intent.putExtra("chat_user",publisherModel);
                                    context.startActivity(intent);
                                    dialog.dismiss();

                                }
                                else if (activity_search_results.companyModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.companyModel.getComp_username(),publisherModel.getPub_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","company");
                                    intent.putExtra("chat_userType","publisher");
                                    intent.putExtra("curr_user",activity_search_results.companyModel);
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
                }
                else
                {
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

                            Intent intent = new Intent(context,Activity_Profile.class);
                            intent.putExtra("who_visit_myProfile","visitor");
                            intent.putExtra("publisherData",publisherModel);
                            context.startActivity(intent);

                            dialog.dismiss();

                        }
                    });
                    send_msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (activity_search_results.userData!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.userData.getUserId(),publisherModel.getPub_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","user");
                                intent.putExtra("chat_userType","publisher");
                                intent.putExtra("curr_user",activity_search_results.userData);
                                intent.putExtra("chat_user",publisherModel);
                                context.startActivity(intent);
                                dialog.dismiss();
                            }else if (activity_search_results.publisherModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.publisherModel.getPub_username(),publisherModel.getPub_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","publisher");
                                intent.putExtra("chat_userType","publisher");
                                intent.putExtra("curr_user",activity_search_results.publisherModel);
                                intent.putExtra("chat_user",publisherModel);
                                context.startActivity(intent);
                                dialog.dismiss();

                            }
                            else if (activity_search_results.libraryModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.libraryModel.getLib_username(),publisherModel.getPub_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","library");
                                intent.putExtra("chat_userType","publisher");
                                intent.putExtra("curr_user",activity_search_results.libraryModel);
                                intent.putExtra("chat_user",publisherModel);
                                context.startActivity(intent);
                                dialog.dismiss();

                            }
                            else if (activity_search_results.universityModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.universityModel.getUni_username(),publisherModel.getPub_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","university");
                                intent.putExtra("chat_userType","publisher");
                                intent.putExtra("curr_user",activity_search_results.universityModel);
                                intent.putExtra("chat_user",publisherModel);
                                context.startActivity(intent);
                                dialog.dismiss();

                            }
                            else if (activity_search_results.companyModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.companyModel.getComp_username(),publisherModel.getPub_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","company");
                                intent.putExtra("chat_userType","publisher");
                                intent.putExtra("curr_user",activity_search_results.companyModel);
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
        TextView publisher_name,publisher_country;

        public ViewHolder(View itemView) {
            super(itemView);

            initTitleView(itemView);


        }



        private void initTitleView(View titleView) {
            publisher_image       = (ImageView) titleView.findViewById(R.id.publisher_image);
            publisher_name        = (TextView) titleView.findViewById(R.id.publisher_name);
            publisher_country        = (TextView) titleView.findViewById(R.id.publisher_country);


       }
        public void BindData(PublisherModel publisherModel)
        {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(),Tags.font);

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


            Log.e("pubusername",""+publisherModel.getPub_username());
            publisher_name.setTypeface(typeface);
            publisher_country.setTypeface(typeface);

            publisher_name.setText(publisherModel.getPub_name());
            if (publisherModel.getTitle()==null)
            {
                publisher_country.setText(publisherModel.getPub_country());

            }else
                {
                    publisher_country.setText(publisherModel.getTitle());

                }




        }
    }


    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}

