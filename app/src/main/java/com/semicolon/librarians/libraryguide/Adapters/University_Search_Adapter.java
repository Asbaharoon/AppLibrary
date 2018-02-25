package com.semicolon.librarians.libraryguide.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.librarians.libraryguide.Activities.Activity_Profile;
import com.semicolon.librarians.libraryguide.Activities.Activity_Search_Results;
import com.semicolon.librarians.libraryguide.Activities.Chat_Activity;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class University_Search_Adapter extends RecyclerView.Adapter<University_Search_Adapter.ViewHolder>  {

    List<UniversityModel> universityModelList;
    Context context;
    Activity_Search_Results activity_search_results;


    public University_Search_Adapter(List<UniversityModel> universityModelList, Context context) {
        this.universityModelList = universityModelList;
        this.context = context;
        activity_search_results = (Activity_Search_Results) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.university_search_recyclerview_row,parent,false);

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

                if (activity_search_results.universityModel!=null)
                {
                    if (universityModel2.getUni_username().equals(activity_search_results.universityModel.getUni_username()))
                    {
                        Intent intent = new Intent(context,Activity_Profile.class);
                        intent.putExtra("who_visit_myProfile","me");
                        intent.putExtra("universityData",activity_search_results.universityModel);
                        context.startActivity(intent);

                    }else {

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
                                intent.putExtra("universityData",universityModel2);
                                context.startActivity(intent);

                                dialog.dismiss();

                            }
                        });
                        send_msg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (activity_search_results.userData!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.userData.getUserId(),universityModel2.getUni_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","user");
                                    intent.putExtra("chat_userType","university");
                                    intent.putExtra("curr_user",activity_search_results.userData);
                                    intent.putExtra("chat_user",universityModel2);
                                    context.startActivity(intent);
                                    dialog.dismiss();
                                }else if (activity_search_results.publisherModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.publisherModel.getPub_username(),universityModel2.getUni_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","publisher");
                                    intent.putExtra("chat_userType","university");
                                    intent.putExtra("curr_user",activity_search_results.publisherModel);
                                    intent.putExtra("chat_user",universityModel2);
                                    context.startActivity(intent);
                                    dialog.dismiss();

                                }
                                else if (activity_search_results.libraryModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.libraryModel.getLib_username(),universityModel2.getUni_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","library");
                                    intent.putExtra("chat_userType","university");
                                    intent.putExtra("curr_user",activity_search_results.libraryModel);
                                    intent.putExtra("chat_user",universityModel2);
                                    context.startActivity(intent);
                                    dialog.dismiss();

                                }
                                else if (activity_search_results.universityModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.universityModel.getUni_username(),universityModel2.getUni_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","university");
                                    intent.putExtra("chat_userType","university");
                                    intent.putExtra("curr_user",activity_search_results.universityModel);
                                    intent.putExtra("chat_user",universityModel2);
                                    context.startActivity(intent);
                                    dialog.dismiss();

                                }
                                else if (activity_search_results.companyModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.companyModel.getComp_username(),universityModel2.getUni_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","company");
                                    intent.putExtra("chat_userType","university");
                                    intent.putExtra("curr_user",activity_search_results.companyModel);
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

                }else
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
                            intent.putExtra("universityData",universityModel2);
                            context.startActivity(intent);

                            dialog.dismiss();

                        }
                    });
                    send_msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (activity_search_results.userData!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.userData.getUserId(),universityModel2.getUni_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","user");
                                intent.putExtra("chat_userType","university");
                                intent.putExtra("curr_user",activity_search_results.userData);
                                intent.putExtra("chat_user",universityModel2);
                                context.startActivity(intent);
                                dialog.dismiss();
                            }else if (activity_search_results.publisherModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.publisherModel.getPub_username(),universityModel2.getUni_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","publisher");
                                intent.putExtra("chat_userType","university");
                                intent.putExtra("curr_user",activity_search_results.publisherModel);
                                intent.putExtra("chat_user",universityModel2);
                                context.startActivity(intent);
                                dialog.dismiss();

                            }
                            else if (activity_search_results.libraryModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.libraryModel.getLib_username(),universityModel2.getUni_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","library");
                                intent.putExtra("chat_userType","university");
                                intent.putExtra("curr_user",activity_search_results.libraryModel);
                                intent.putExtra("chat_user",universityModel2);
                                context.startActivity(intent);
                                dialog.dismiss();

                            }
                            else if (activity_search_results.universityModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.universityModel.getUni_username(),universityModel2.getUni_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","university");
                                intent.putExtra("chat_userType","university");
                                intent.putExtra("curr_user",activity_search_results.universityModel);
                                intent.putExtra("chat_user",universityModel2);
                                context.startActivity(intent);
                                dialog.dismiss();

                            }
                            else if (activity_search_results.companyModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.companyModel.getComp_username(),universityModel2.getUni_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","company");
                                intent.putExtra("chat_userType","university");
                                intent.putExtra("curr_user",activity_search_results.companyModel);
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

            }
        });

    }

    @Override
    public int getItemCount() {
        return universityModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        Target target;
        ImageView uni_image;
        TextView uni_name,uni_country;

        public ViewHolder(View itemView) {
            super(itemView);

            initTitleView(itemView);


        }



        private void initTitleView(View titleView) {
            uni_image       = (ImageView) titleView.findViewById(R.id.university_image);
            uni_name        = (TextView) titleView.findViewById(R.id.university_name);
            uni_country        = (TextView) titleView.findViewById(R.id.university_country);


       }
        public void BindData(UniversityModel universityModel)
        {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(),Tags.font);

            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    uni_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            if (!universityModel.getUser_photo().equals("0"))
            {
                Picasso.with(context).load(Tags.image_path+universityModel.getUser_photo()).placeholder(R.drawable.user_profile).into(target);

            }else
            {
                Picasso.with(context).load(R.drawable.user_profile).into(target);

            }




            uni_name.setTypeface(typeface);
            uni_country.setTypeface(typeface);

            uni_name.setText(universityModel.getUni_name().toString());
            uni_country.setText(universityModel.getUni_country().toString());




        }
    }



}

