package com.semicolon.librarians.library.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.semicolon.librarians.library.Models.CommonUsersData;
import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;
import com.semicolon.librarians.library.R;
import com.semicolon.librarians.library.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class Chat_Activity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private View customToolBarView;
    private ImageView emoji_btn;
    private EmojiconEditText msg_et;
    private ImageButton sendBtn;
    private EmojIconActions iconActions;
    private View rootView;
    private String curr_user_type;
    private String chat_user_type;
    private String curr_user_id;
    private String curr_user_name;
    private String curr_user_image;

    private String chat_user_id;
    private String chat_user_name;
    private String chat_user_image;

    private NormalUserData curr_normalUserData;
    private PublisherModel curr_publisherModel;
    private LibraryModel curr_libraryModel;
    private UniversityModel curr_universityModel;
    private CompanyModel curr_companyModel;

    private NormalUserData chat_normalUserData;
    private PublisherModel chat_publisherModel;
    private LibraryModel chat_libraryModel;
    private UniversityModel chat_universityModel;
    private CompanyModel chat_companyModel;

    private CommonUsersData data;

    private CircleImageView user_chat_image;
    private TextView user_chat_name,user_chat_status;
    private Target target;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("curr_user")&&intent.hasExtra("chat_user"))
        {
            curr_user_type = intent.getStringExtra("curr_userType");
            chat_user_type = intent.getStringExtra("chat_userType");

            switch (curr_user_type)
            {
                case "user":
                    curr_normalUserData = (NormalUserData) intent.getSerializableExtra("curr_user");
                    curr_user_id = curr_normalUserData.getUserId().toString();
                    curr_user_name = curr_normalUserData.getUserName().toString();
                    //curr_user_image=curr_normalUserData.getUserPhoto().toString();
                    Log.e("gggggg",curr_normalUserData.getUserId());
                    break;
                case "publisher":
                    curr_publisherModel = (PublisherModel) intent.getSerializableExtra("curr_user");
                    curr_user_id = curr_publisherModel.getPub_username().toString();
                    curr_user_name = curr_publisherModel.getPub_name();
                    Log.e("gggggg",curr_publisherModel.getPub_username());

                    break;
                case "library":
                    curr_libraryModel = (LibraryModel) intent.getSerializableExtra("curr_user");
                    curr_user_id = curr_libraryModel.getLib_username().toString();
                    curr_user_name = curr_libraryModel.getLib_name();
                    Log.e("gggggg",curr_libraryModel.getLib_username());

                    break;
                case "university":
                    curr_universityModel = (UniversityModel) intent.getSerializableExtra("curr_user");
                    curr_user_id = curr_universityModel.getUni_username();
                    curr_user_name = curr_universityModel.getUni_name();
                    Log.e("gggggg",curr_universityModel.getUni_username());

                    break;
                case "company":
                    curr_companyModel   = (CompanyModel) intent.getSerializableExtra("curr_user");
                    curr_user_id = curr_companyModel.getComp_username();
                    curr_user_name = curr_companyModel.getComp_name();
                    Log.e("gggggg",curr_companyModel.getComp_username());

                    break;
            }

            switch (chat_user_type)
            {
                case "user":
                    chat_normalUserData = (NormalUserData) intent.getSerializableExtra("chat_user");
                    chat_user_id =   chat_normalUserData.getUserId().toString();
                    chat_user_name = chat_normalUserData.getUserName().toString();
                    //chat_user_image= chat_normalUserData.getUserPhoto().toString();
                    if (chat_normalUserData.getUserPhoto()!=null)
                    {
                        updateUi(chat_normalUserData.getUserPhoto(),chat_normalUserData.getUserName(),"online");

                    }else
                        {
                            if (!chat_normalUserData.getUser_photo().equals("0"))
                            {
                                updateUi(Tags.image_path+chat_normalUserData.getUser_photo(),chat_normalUserData.getUserName(),"online");

                            }
                        }
                    break;
                case "publisher":
                    chat_publisherModel = (PublisherModel) intent.getSerializableExtra("chat_user");
                    chat_user_id   = chat_publisherModel.getPub_username();
                    chat_user_name = chat_publisherModel.getPub_name();

                    if (!chat_publisherModel.getUser_photo().equals("0"))
                    {
                        updateUi(Tags.image_path+chat_publisherModel.getUser_photo(),chat_publisherModel.getPub_name(),"online");

                    }else
                        {
                            updateUi("",chat_publisherModel.getPub_name(),"online");

                        }
                    break;
                case "library":
                    chat_libraryModel = (LibraryModel) intent.getSerializableExtra("chat_user");
                    chat_user_id   = chat_libraryModel.getLib_username().toString();
                    chat_user_name = chat_libraryModel.getLib_name();

                    if (!chat_libraryModel.getUser_photo().equals("0"))
                    {
                        updateUi(Tags.image_path+chat_libraryModel.getUser_photo(),chat_libraryModel.getLib_name(),"online");

                    }else
                        {
                            updateUi("",chat_libraryModel.getLib_name(),"online");

                        }
                    break;
                case "university":
                    chat_universityModel = (UniversityModel) intent.getSerializableExtra("chat_user");
                    chat_user_id   =chat_universityModel.getUni_username();
                    chat_user_name =chat_universityModel.getUni_name();

                    if (!chat_universityModel.getUser_photo().equals("0"))
                    {
                        updateUi(Tags.image_path+chat_universityModel.getUser_photo(),chat_universityModel.getUni_name(),"online");

                    }else
                        {
                            updateUi("",chat_universityModel.getUni_name(),"online");

                        }

                    break;
                case "company":
                    chat_companyModel   = (CompanyModel) intent.getSerializableExtra("chat_user");
                    chat_user_id   = chat_companyModel.getComp_username();
                    chat_user_name = chat_companyModel.getComp_name();

                    if (!chat_companyModel.getUser_photo().equals("0"))
                    {
                        updateUi(Tags.image_path+chat_companyModel.getUser_photo(),chat_companyModel.getComp_name(),"online");

                    }else
                        {
                            updateUi("",chat_companyModel.getComp_name(),"online");

                        }

                    break;

            }

        }else if (intent.hasExtra("chatRoomIntent"))
        {
            curr_user_type = intent.getStringExtra("curr_userType");
            chat_user_type = intent.getStringExtra("chat_userType");
            data = (CommonUsersData) intent.getSerializableExtra("chat_userData");
            chat_user_id = data.getUser_username();

            if (data.getUser_photo_link()==null)
            {
                if (!data.getUser_photo().equals("0"))
                {
                    updateUi(Tags.image_path+data.getUser_photo(),data.getUser_name(),"online");

                }

            }else
                {
                    updateUi(data.getUser_photo_link(),data.getUser_name(),"online");
                }
            switch (curr_user_type)
            {
                case "user":
                    curr_normalUserData = (NormalUserData) intent.getSerializableExtra("curr_userData");
                    curr_user_id = curr_normalUserData.getUserId();
                    break;
                case "publisher":
                    curr_publisherModel = (PublisherModel) intent.getSerializableExtra("curr_userData");
                    curr_user_id = curr_publisherModel.getPub_username();
                    break;
                case "library":
                    curr_libraryModel = (LibraryModel) intent.getSerializableExtra("curr_userData");
                    curr_user_id = curr_libraryModel.getLib_username();
                    break;
                case "university":
                    curr_universityModel = (UniversityModel) intent.getSerializableExtra("curr_userData");
                    curr_user_id = curr_universityModel.getUni_username();
                    break;
                case "company":
                    curr_companyModel = (CompanyModel) intent.getSerializableExtra("curr_userData");
                    curr_user_id = curr_companyModel.getComp_username();
                    break;


            }
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.chat_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        customToolBarView = LayoutInflater.from(this).inflate(R.layout.chat_custom_toolbar,null);
        user_chat_image   = (CircleImageView) customToolBarView.findViewById(R.id.user_chat_image);
        user_chat_name    = (TextView) customToolBarView.findViewById(R.id.user_chat_name);
        user_chat_status  = (TextView) customToolBarView.findViewById(R.id.user_chat_status);
        getSupportActionBar().setCustomView(customToolBarView);

        rootView  = findViewById(R.id.rootView);
        emoji_btn = (ImageView) findViewById(R.id.emoji_btn);
        msg_et = (EmojiconEditText) findViewById(R.id.msg_et);
        sendBtn = (ImageButton) findViewById(R.id.sendBtn);
        iconActions = new EmojIconActions(this,rootView,msg_et,emoji_btn);
        iconActions.ShowEmojIcon();
        iconActions.setIconsIds(R.drawable.ic_action_keyboard,R.drawable.emoji_icon);
        sendBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.sendBtn:
                sendMessage();
                break;

        }
    }

    private void sendMessage() {
        Log.e("emoji",msg_et.getText().toString());
        msg_et.setText("");
        iconActions.closeEmojIcon();
        Toast.makeText(this, "user_curr_type"+curr_user_type+"\n"+"curr_user_id"+curr_user_id+"\n"+"chat_user_type"+chat_user_type+"\n"+"chat_user_id"+chat_user_id, Toast.LENGTH_SHORT).show();
    }

    private void updateUi(String image_url,String user_chat_Name,String user_chat_Status)
    {
        user_chat_name.setText(user_chat_Name.toString());
        if (user_chat_Status.equals("online"))
        {
            user_chat_status.setText(user_chat_Status);
            user_chat_status.setTextColor(ActivityCompat.getColor(this,R.color.online_color));

        }else
            {
                user_chat_status.setText(user_chat_Status);
            }
        if (TextUtils.isEmpty(image_url))
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    user_chat_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(this).load(R.drawable.user_profile).placeholder(R.drawable.user_profile).into(target);
        }else
            {
                target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        user_chat_image.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.with(this).load(Uri.parse(image_url.toString())).placeholder(R.drawable.user_profile).into(target);

            }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return true;
    }
}
