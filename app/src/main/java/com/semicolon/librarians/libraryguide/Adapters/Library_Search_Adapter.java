package com.semicolon.librarians.libraryguide.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
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
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class Library_Search_Adapter extends RecyclerView.Adapter<Library_Search_Adapter.ViewHolder>  {

    List<LibraryModel> libraryModelList;
    Context context;
    Activity_Search_Results activity_search_results;


    public Library_Search_Adapter(List<LibraryModel> libraryModelList, Context context) {
        this.libraryModelList = libraryModelList;
        this.context = context;
        activity_search_results = (Activity_Search_Results) context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.library_search_recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        LibraryModel libraryModel = libraryModelList.get(position);
        holder.BindData(libraryModel);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.rec_anim);
        holder.itemView.startAnimation(animation);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LibraryModel libraryModel2 = libraryModelList.get(holder.getAdapterPosition());

                if (activity_search_results.libraryModel!=null)
                {
                    if (libraryModel2.getLib_username().equals(activity_search_results.libraryModel.getLib_username()))
                    {
                        Intent intent = new Intent(context,Activity_Profile.class);
                        intent.putExtra("who_visit_myProfile","me");
                        intent.putExtra("libraryData",activity_search_results.libraryModel);
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
                                intent.putExtra("libraryData",libraryModel2);
                                context.startActivity(intent);

                                dialog.dismiss();

                            }
                        });
                        send_msg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (activity_search_results.userData!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.userData.getUserId(),libraryModel2.getLib_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","user");
                                    intent.putExtra("chat_userType","library");
                                    intent.putExtra("curr_user",activity_search_results.userData);
                                    intent.putExtra("chat_user",libraryModel2);
                                    context.startActivity(intent);
                                    dialog.dismiss();
                                }else if (activity_search_results.publisherModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.publisherModel.getPub_username(),libraryModel2.getLib_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","publisher");
                                    intent.putExtra("chat_userType","library");
                                    intent.putExtra("curr_user",activity_search_results.publisherModel);
                                    intent.putExtra("chat_user",libraryModel2);
                                    context.startActivity(intent);
                                    dialog.dismiss();

                                }
                                else if (activity_search_results.libraryModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.libraryModel.getLib_username(),libraryModel2.getLib_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","library");
                                    intent.putExtra("chat_userType","library");
                                    intent.putExtra("curr_user",activity_search_results.libraryModel);
                                    intent.putExtra("chat_user",libraryModel2);
                                    context.startActivity(intent);
                                    dialog.dismiss();

                                }
                                else if (activity_search_results.universityModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.universityModel.getUni_username(),libraryModel2.getLib_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","university");
                                    intent.putExtra("chat_userType","library");
                                    intent.putExtra("curr_user",activity_search_results.universityModel);
                                    intent.putExtra("chat_user",libraryModel2);
                                    context.startActivity(intent);
                                    dialog.dismiss();

                                }
                                else if (activity_search_results.companyModel!=null)
                                {
                                    activity_search_results.presenter.Create_ChatRoom(activity_search_results.companyModel.getComp_username(),libraryModel2.getLib_username());

                                    Intent intent = new Intent(context, Chat_Activity.class);
                                    intent.putExtra("curr_userType","company");
                                    intent.putExtra("chat_userType","library");
                                    intent.putExtra("curr_user",activity_search_results.companyModel);
                                    intent.putExtra("chat_user",libraryModel2);
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
                            intent.putExtra("libraryData",libraryModel2);
                            context.startActivity(intent);

                            dialog.dismiss();

                        }
                    });
                    send_msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (activity_search_results.userData!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.userData.getUserId(),libraryModel2.getLib_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","user");
                                intent.putExtra("chat_userType","library");
                                intent.putExtra("curr_user",activity_search_results.userData);
                                intent.putExtra("chat_user",libraryModel2);
                                context.startActivity(intent);
                                dialog.dismiss();
                            }else if (activity_search_results.publisherModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.publisherModel.getPub_username(),libraryModel2.getLib_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","publisher");
                                intent.putExtra("chat_userType","library");
                                intent.putExtra("curr_user",activity_search_results.publisherModel);
                                intent.putExtra("chat_user",libraryModel2);
                                context.startActivity(intent);
                                dialog.dismiss();

                            }
                            else if (activity_search_results.libraryModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.libraryModel.getLib_username(),libraryModel2.getLib_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","library");
                                intent.putExtra("chat_userType","library");
                                intent.putExtra("curr_user",activity_search_results.libraryModel);
                                intent.putExtra("chat_user",libraryModel2);
                                context.startActivity(intent);
                                dialog.dismiss();

                            }
                            else if (activity_search_results.universityModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.universityModel.getUni_username(),libraryModel2.getLib_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","university");
                                intent.putExtra("chat_userType","library");
                                intent.putExtra("curr_user",activity_search_results.universityModel);
                                intent.putExtra("chat_user",libraryModel2);
                                context.startActivity(intent);
                                dialog.dismiss();

                            }
                            else if (activity_search_results.companyModel!=null)
                            {
                                activity_search_results.presenter.Create_ChatRoom(activity_search_results.companyModel.getComp_username(),libraryModel2.getLib_username());

                                Intent intent = new Intent(context, Chat_Activity.class);
                                intent.putExtra("curr_userType","company");
                                intent.putExtra("chat_userType","library");
                                intent.putExtra("curr_user",activity_search_results.companyModel);
                                intent.putExtra("chat_user",libraryModel2);
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
        return libraryModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        Target target;
        ImageView lib_image;
        TextView lib_name,libCountry;

        public ViewHolder(View itemView) {
            super(itemView);

            initTitleView(itemView);


        }



        private void initTitleView(View titleView) {
            lib_image       = (ImageView) titleView.findViewById(R.id.library_image_item);
            lib_name        = (TextView) titleView.findViewById(R.id.library_name_item);
            libCountry        = (TextView) titleView.findViewById(R.id.library_country_item);


        }
        public void BindData(LibraryModel libraryModel)
        {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(),Tags.font);

            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    lib_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };


            if (!libraryModel.getUser_photo().equals("0"))
            {
                Picasso.with(context).load(Uri.parse(Tags.image_path+libraryModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);
            }

            lib_name.setText(libraryModel.getLib_name());
            if (libraryModel.getTitle()==null)
            {
                libCountry.setText(libraryModel.getLib_country());

            }else
                {
                    libCountry.setText(libraryModel.getTitle());

                }
            lib_name.setTypeface(typeface);
            libCountry.setTypeface(typeface);




        }
    }


    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}

