package com.semicolon.librarians.libraryguide.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.semicolon.librarians.libraryguide.Activities.Chat_Activity;
import com.semicolon.librarians.libraryguide.Activities.HomeActivity;
import com.semicolon.librarians.libraryguide.Models.CommonUsersData;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class AllChatRoomsAdapter extends RecyclerView.Adapter<AllChatRoomsAdapter.ViewHolder>  {

    List<CommonUsersData> commonUsersDataList;
    Context context;
    HomeActivity homeActivity;

    public AllChatRoomsAdapter(List<CommonUsersData> commonUsersDataList, Context context) {
        this.commonUsersDataList = commonUsersDataList;
        this.context = context;
        homeActivity = (HomeActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_room_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CommonUsersData commonUsersData = commonUsersDataList.get(position);
        holder.BindData(commonUsersData);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUsersData commonUsersData = commonUsersDataList.get(holder.getAdapterPosition());
                if (homeActivity.user_Data!=null)
                {
                    Intent intent = new Intent(context, Chat_Activity.class);
                    intent.putExtra("chatRoomIntent","chatRoomIntent");
                    intent.putExtra("curr_userType","user");
                    intent.putExtra("chat_userType",commonUsersData.getUser_type());
                    intent.putExtra("curr_userData",homeActivity.user_Data);
                    intent.putExtra("chat_userData",commonUsersData);
                    context.startActivity(intent);


                }else if (homeActivity.publisher_Model!=null)
                {
                    Intent intent = new Intent(context, Chat_Activity.class);
                    intent.putExtra("chatRoomIntent","chatRoomIntent");
                    intent.putExtra("curr_userType","publisher");
                    intent.putExtra("chat_userType",commonUsersData.getUser_type());
                    intent.putExtra("curr_userData",homeActivity.publisher_Model);
                    intent.putExtra("chat_userData",commonUsersData);
                    context.startActivity(intent);
                }
                else if (homeActivity.library_Model!=null)
                {
                    Intent intent = new Intent(context, Chat_Activity.class);
                    intent.putExtra("chatRoomIntent","chatRoomIntent");
                    intent.putExtra("curr_userType","library");
                    intent.putExtra("chat_userType",commonUsersData.getUser_type());
                    intent.putExtra("curr_userData",homeActivity.library_Model);
                    intent.putExtra("chat_userData",commonUsersData);
                    context.startActivity(intent);
                }else if (homeActivity.university_Model!=null)
                {
                    Intent intent = new Intent(context, Chat_Activity.class);
                    intent.putExtra("chatRoomIntent","chatRoomIntent");
                    intent.putExtra("curr_userType","university");
                    intent.putExtra("chat_userType",commonUsersData.getUser_type());
                    intent.putExtra("curr_userData",homeActivity.university_Model);
                    intent.putExtra("chat_userData",commonUsersData);
                    context.startActivity(intent);

                }else if (homeActivity.company_Model!=null)
                {
                    Intent intent = new Intent(context, Chat_Activity.class);
                    intent.putExtra("chatRoomIntent","chatRoomIntent");
                    intent.putExtra("curr_userType","company");
                    intent.putExtra("chat_userType",commonUsersData.getUser_type());
                    intent.putExtra("curr_userData",homeActivity.company_Model);
                    intent.putExtra("chat_userData",commonUsersData);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return commonUsersDataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        Target target;

        ImageView chat_user_image;
        TextView chatuserName;
        public ViewHolder(View itemView) {
            super(itemView);

            chat_user_image = (ImageView) itemView.findViewById(R.id.chat_userImage);
            chatuserName    = (TextView) itemView.findViewById(R.id.chat_userName);

        }

        public void BindData(CommonUsersData commonUsersData)
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    //Title_news_image.setImageBitmap(bitmap);
                    chat_user_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            if (commonUsersData.getUser_photo_link()==null)
            {
                Picasso.with(context).load(Uri.parse(Tags.image_path+commonUsersData.getUser_photo())).placeholder(R.drawable.user_profile).into(target);
            }else
                {
                    Picasso.with(context).load(Uri.parse(commonUsersData.getUser_photo_link())).placeholder(R.drawable.user_profile).into(target);

                }

            chatuserName.setText(commonUsersData.getUser_name().toString());



        }
    }




}

