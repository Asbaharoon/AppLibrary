package com.semicolon.librarians.libraryguide.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.Presenter;
import com.semicolon.librarians.libraryguide.Models.MessageModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.UnsupportedEncodingException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by Delta on 14/02/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final int ITEM_ONE=1;
    private final int ITEM_TWO=2;
    private Target target;


    private Context context;
    private List<MessageModel> messageModelList;
    private String curr_user_id;
    private String chatuserid;
    private String chatuserType;
    private Presenter presenter;
    private ViewHolder2 holder2;
    private String chat_userImage;

    public MessageAdapter(Context context, List<MessageModel> messageModelList, String curr_user_id, String chatuserid, String chatuserType,String chat_userImage) {
        this.context = context;
        this.messageModelList = messageModelList;
        this.curr_user_id = curr_user_id;
        this.chatuserid = chatuserid;
        this.chatuserType = chatuserType;
        this.chat_userImage = chat_userImage;

       // presenter = new PresenterImp(this,context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ITEM_ONE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.msg_right_container,parent,false);
            ViewHolder1 holder1= new ViewHolder1(view);
            return holder1;

        }else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.msg_left_container,parent,false);

            holder2 =new ViewHolder2(view);
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = messageModelList.get(position);
        int item = holder.getItemViewType();

        if (item==ITEM_ONE)
        {
             ((ViewHolder1)holder).BindData(messageModel.getMessage(),messageModel.getDate());
             Log.e("item1","1");
        }
        else
            {
                Log.e("item1","2");

                byte[] decode = Base64.decode(messageModel.getMessage().toString(),Base64.DEFAULT);
                try {
                    String decoded_msg = new String(decode,"UTF-8");
                    //((ViewHolder2)holder).Message.setText(decoded_msg);
                    ((ViewHolder2)holder).BindData(chat_userImage,decoded_msg);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //GetuserPhoto(chatuserType);
            }


    }



    public class ViewHolder1 extends RecyclerView.ViewHolder{

        private EmojiconTextView Message;
        private TextView Date;

        public ViewHolder1(View itemView) {
            super(itemView);

            Message = (EmojiconTextView) itemView.findViewById(R.id.msg_tv);
            Date    = (TextView) itemView.findViewById(R.id.msgDate);



        }
        public void BindData(String msg,String date)
        {

            byte [] decode = Base64.decode(msg,Base64.DEFAULT);
            try {
                String decoded_msg = new String(decode,"UTF-8");
                Message.setText(decoded_msg);
                Date.setText(date);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
    }
    public class ViewHolder2 extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        private EmojiconTextView Message;

        public ViewHolder2(View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.image);
            Message   = (EmojiconTextView) itemView.findViewById(R.id.msg_tv);

        }

        public void BindData(String photo,String msg)
        {
            Message.setText(msg);
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imageView.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            if (photo.startsWith("http"))
            {

                Picasso.with(context).load(Uri.parse(photo)).placeholder(R.drawable.user_profile).into(target);
            }else
                {

                    if (!photo.equals("0"))
                    {

                        Picasso.with(context).load(Uri.parse(Tags.image_path+photo)).placeholder(R.drawable.user_profile).into(target);

                    }
                }
        }
    }


   /* public void GetuserPhoto(String chatuserType)
    {
        switch (chatuserType)
        {
            case "user":
                presenter.getNormalUserData(chatuserType,chatuserid);
                break;
            case "publisher":
                presenter.getPublisherData(chatuserType,chatuserid);
                break;
            case "library":
                presenter.getLibraryData(chatuserType,chatuserid);
                break;
            case "university":
                presenter.getUniversityData(chatuserType,chatuserid);
                break;
            case "company":
                presenter.getCompanyData(chatuserType,chatuserid);
                break;

        }
    }*/
   /* @Override
    public void onNormalUserDataSuccess(NormalUserData normalUserData) {
        if (normalUserData.getUserPhoto()==null)
        {
            if (!normalUserData.getUser_photo().equals("0"))
            {
                holder2.BindData(normalUserData.getUser_photo());

            }
        }else
            {
                holder2.BindData(normalUserData.getUserPhoto());

            }
    }

    @Override
    public void onPublisherDataSuccess(PublisherModel publisherModel) {

        if (!publisherModel.getUser_photo().equals("0"))
        {
            holder2.BindData(publisherModel.getUser_photo());
        }
    }

    @Override
    public void onLibraryDataSuccess(LibraryModel libraryModel) {
        if (!libraryModel.getUser_photo().equals("0"))
        {
            holder2.BindData(libraryModel.getUser_photo());
        }
    }

    @Override
    public void onCompanyDataSuccess(CompanyModel companyModel) {
        if (!companyModel.getUser_photo().equals("0"))
        {
            holder2.BindData(companyModel.getUser_photo());
        }
    }

    @Override
    public void onUniversityDataSuccess(UniversityModel universityModel) {
        if (!universityModel.getUser_photo().equals("0"))
        {
            holder2.BindData(universityModel.getUser_photo());
        }
    }

    @Override
    public void onFailed(String error) {

    }*/

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }
    @Override
    public int getItemViewType(int position) {
        Log.e("from"," "+messageModelList.get(position).getFrom()+"  "+curr_user_id);
        if (messageModelList.get(position).getFrom().equals(curr_user_id))
        {
            return ITEM_ONE;
        }else
            {
                return ITEM_TWO;
            }
    }

    public void UpdateData(MessageModel messageModel,String curr_user_id,String chatuserid,String chat_user_type)
    {
        this.curr_user_id =curr_user_id;
        this.chatuserid = chatuserid;
        this.chatuserType=chat_user_type;

        messageModelList.add(messageModel);
        notifyDataSetChanged();
    }
}
