package com.semicolon.librarians.libraryguide.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.semicolon.librarians.libraryguide.Adapters.MessageAdapter;
import com.semicolon.librarians.libraryguide.MVP.getLastMsg_MVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.getLastMsg_MVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.getLastMsg_MVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.CommonUsersData;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.ID;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.MessageModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Manager_Notification;
import com.semicolon.librarians.libraryguide.Services.NetworkConnection;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class Chat_Activity extends AppCompatActivity implements View.OnClickListener,ViewData, com.semicolon.librarians.libraryguide.MVP.Messages_MVP.ViewData {

    private Toolbar toolbar;
    private View customToolBarView;
    private ImageView emoji_btn;
    private EmojiconEditText msg_et;
    private ImageButton sendBtn;
    private EmojIconActions iconActions;
    private ImageView back_arrow;
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
    private String chat_user_token;
    private CommonUsersData data;

    private CircleImageView user_chat_image;
    private TextView user_chat_name,user_chat_status;
    private Target target;

    private com.semicolon.librarians.libraryguide.MVP.Messages_MVP.Presenter presenter;
    private RecyclerView chatRecyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private ProgressBar chat_progress;
    private List<MessageModel>messageModelList;
    private Presenter presenter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        presenter = new com.semicolon.librarians.libraryguide.MVP.Messages_MVP.PresenterImp(this,this);
        presenter2 = new PresenterImp(this,this);
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
                    if (curr_normalUserData.getUserPhoto()==null)
                    {
                        curr_user_image=curr_normalUserData.getUser_photo().toString();

                    }else
                        {
                            curr_user_image=curr_normalUserData.getUserPhoto().toString();

                        }
                    curr_user_name = curr_normalUserData.getUserName();
                    Log.e("gggggg",curr_normalUserData.getUserId());
                    break;
                case "publisher":
                    curr_publisherModel = (PublisherModel) intent.getSerializableExtra("curr_user");
                    curr_user_id = curr_publisherModel.getPub_username().toString();
                    curr_user_name = curr_publisherModel.getPub_name();
                    curr_user_image = curr_publisherModel.getUser_photo();
                    Log.e("gggggg",curr_publisherModel.getPub_username());



                    break;
                case "library":
                    curr_libraryModel = (LibraryModel) intent.getSerializableExtra("curr_user");
                    curr_user_id = curr_libraryModel.getLib_username().toString();
                    curr_user_name = curr_libraryModel.getLib_name();
                    curr_user_image = curr_libraryModel.getUser_photo();

                    Log.e("gggggg",curr_libraryModel.getLib_username());


                    break;
                case "university":
                    curr_universityModel = (UniversityModel) intent.getSerializableExtra("curr_user");
                    curr_user_id = curr_universityModel.getUni_username();
                    curr_user_name = curr_universityModel.getUni_name();
                    curr_user_image = curr_universityModel.getUser_photo();

                    Log.e("gggggg",curr_universityModel.getUni_username());

                    break;
                case "company":
                    curr_companyModel   = (CompanyModel) intent.getSerializableExtra("curr_user");
                    curr_user_id = curr_companyModel.getComp_username();
                    curr_user_name = curr_companyModel.getComp_name();
                    curr_user_image = curr_companyModel.getUser_photo();

                    Log.e("gggggg",curr_companyModel.getComp_username());

                    break;
            }

            switch (chat_user_type)
            {
                case "user":
                    chat_normalUserData = (NormalUserData) intent.getSerializableExtra("chat_user");
                    chat_user_id =   chat_normalUserData.getUserId().toString();
                    chat_user_name = chat_normalUserData.getUserName().toString();
                    chat_user_token= chat_normalUserData.getUser_token();
                    if (chat_normalUserData.getUserPhoto()!=null)
                    {

                        updateUi(chat_normalUserData.getUserPhoto(),chat_normalUserData.getUserName(),"Active Now");
                        chat_user_image= chat_normalUserData.getUserPhoto().toString();
                    }else
                        {
                            if (!chat_normalUserData.getUser_photo().equals("0"))
                            {
                                updateUi(Tags.image_path+chat_normalUserData.getUser_photo(),chat_normalUserData.getUserName(),"Active now");
                                chat_user_image= chat_normalUserData.getUser_photo().toString();
                            }
                        }
                    break;
                case "publisher":
                    chat_publisherModel = (PublisherModel) intent.getSerializableExtra("chat_user");
                    chat_user_id   = chat_publisherModel.getPub_username();
                    chat_user_name = chat_publisherModel.getPub_name();
                    chat_user_image = chat_publisherModel.getUser_photo();
                    chat_user_token = chat_publisherModel.getUser_token();
                    if (!chat_publisherModel.getUser_photo().equals("0"))
                    {
                        updateUi(Tags.image_path+chat_publisherModel.getUser_photo(),chat_publisherModel.getPub_name(),"Active now");

                    }else
                        {
                            updateUi("",chat_publisherModel.getPub_name(),"Active now");

                        }
                    break;
                case "library":
                    chat_libraryModel = (LibraryModel) intent.getSerializableExtra("chat_user");
                    chat_user_id   = chat_libraryModel.getLib_username().toString();
                    chat_user_name = chat_libraryModel.getLib_name();
                    chat_user_image = chat_libraryModel.getUser_photo();
                    chat_user_token = chat_libraryModel.getUser_token();

                    if (!chat_libraryModel.getUser_photo().equals("0"))
                    {
                        updateUi(Tags.image_path+chat_libraryModel.getUser_photo(),chat_libraryModel.getLib_name(),"Active now");

                    }else
                        {
                            updateUi("",chat_libraryModel.getLib_name(),"Active now");

                        }
                    break;
                case "university":
                    chat_universityModel = (UniversityModel) intent.getSerializableExtra("chat_user");
                    chat_user_id   =chat_universityModel.getUni_username();
                    chat_user_name =chat_universityModel.getUni_name();
                    chat_user_image = chat_universityModel.getUser_photo();
                    chat_user_token = chat_universityModel.getUser_token();

                    if (!chat_universityModel.getUser_photo().equals("0"))
                    {
                        updateUi(Tags.image_path+chat_universityModel.getUser_photo(),chat_universityModel.getUni_name(),"Active now");

                    }else
                        {
                            updateUi("",chat_universityModel.getUni_name(),"Active now");

                        }

                    break;
                case "company":
                    chat_companyModel   = (CompanyModel) intent.getSerializableExtra("chat_user");
                    chat_user_id   = chat_companyModel.getComp_username();
                    chat_user_name = chat_companyModel.getComp_name();
                    chat_user_image = chat_companyModel.getUser_photo();
                    chat_user_token = chat_companyModel.getUser_token();

                    if (!chat_companyModel.getUser_photo().equals("0"))
                    {
                        updateUi(Tags.image_path+chat_companyModel.getUser_photo(),chat_companyModel.getComp_name(),"Active now");

                    }else
                        {
                            updateUi("",chat_companyModel.getComp_name(),"Active now");

                        }

                    break;

            }

        }else if (intent.hasExtra("chatRoomIntent"))
        {
            curr_user_type = intent.getStringExtra("curr_userType");
            chat_user_type = intent.getStringExtra("chat_userType");
            data = (CommonUsersData) intent.getSerializableExtra("chat_userData");
            chat_user_name = data.getUser_name();
            chat_user_token = data.getUser_token();
            chat_user_id = data.getUser_username();

            if (data.getUser_photo_link()==null)
            {
                if (!data.getUser_photo().equals("0"))
                {
                    chat_user_image = data.getUser_photo();
                    updateUi(Tags.image_path+data.getUser_photo(),data.getUser_name(),"Active now");

                }

            }else
                {
                    chat_user_image = data.getUser_photo_link();

                    updateUi(data.getUser_photo_link(),data.getUser_name(),"Active now");
                }
            switch (curr_user_type)
            {
                case "user":
                    curr_normalUserData = (NormalUserData) intent.getSerializableExtra("curr_userData");
                    curr_user_id = curr_normalUserData.getUserId();
                    curr_user_name=curr_normalUserData.getUserName();

                    if (curr_normalUserData.getUserPhoto()!=null)
                    {
                        curr_user_image= curr_normalUserData.getUserPhoto().toString();
                    }else
                    {
                        if (!curr_normalUserData.getUser_photo().equals("0"))
                        {
                            curr_user_image= curr_normalUserData.getUser_photo().toString();
                        }
                    }
                    break;
                case "publisher":
                    curr_publisherModel = (PublisherModel) intent.getSerializableExtra("curr_userData");
                    curr_user_id = curr_publisherModel.getPub_username();
                    curr_user_name = curr_publisherModel.getPub_name();
                    curr_user_image = curr_publisherModel.getUser_photo();

                    break;
                case "library":
                    curr_libraryModel = (LibraryModel) intent.getSerializableExtra("curr_userData");
                    curr_user_id = curr_libraryModel.getLib_username();
                    curr_user_name = curr_libraryModel.getLib_name();
                    curr_user_image = curr_libraryModel.getUser_photo();

                    break;
                case "university":
                    curr_universityModel = (UniversityModel) intent.getSerializableExtra("curr_userData");
                    curr_user_id = curr_universityModel.getUni_username();
                    curr_user_name =curr_universityModel.getUni_name();
                    curr_user_image = curr_universityModel.getUser_photo();

                    break;
                case "company":
                    curr_companyModel = (CompanyModel) intent.getSerializableExtra("curr_userData");
                    curr_user_id = curr_companyModel.getComp_username();
                    curr_user_name = curr_companyModel.getComp_name();
                    curr_user_image = curr_companyModel.getUser_photo();

                    break;


            }
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.chat_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        customToolBarView = LayoutInflater.from(this).inflate(R.layout.chat_custom_toolbar,null);
        user_chat_image   = (CircleImageView) customToolBarView.findViewById(R.id.user_chat_image);
        user_chat_name    = (TextView) customToolBarView.findViewById(R.id.user_chat_name);
        user_chat_status  = (TextView) customToolBarView.findViewById(R.id.user_chat_status);
        back_arrow        = (ImageView) customToolBarView.findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(this);
        getSupportActionBar().setCustomView(customToolBarView);

        rootView  = findViewById(R.id.rootView);
        emoji_btn = (ImageView) findViewById(R.id.emoji_btn);
        msg_et = (EmojiconEditText) findViewById(R.id.msg_et);
        sendBtn = (ImageButton) findViewById(R.id.sendBtn);
        iconActions = new EmojIconActions(this,rootView,msg_et,emoji_btn);
        iconActions.ShowEmojIcon();
        iconActions.setIconsIds(R.drawable.ic_action_keyboard,R.drawable.emoji_icon);
        sendBtn.setOnClickListener(this);
        chat_progress = (ProgressBar) findViewById(R.id.chat_progress);
        chat_progress.getIndeterminateDrawable().setColorFilter(ActivityCompat.getColor(this,R.color.centercolor), PorterDuff.Mode.SRC_IN);
        chatRecyclerView = (RecyclerView) findViewById(R.id.chatRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatRecyclerView.setHasFixedSize(true);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.sendBtn:
                sendMessage();
                break;
            case R.id.back_arrow:
                finish();
                break;

        }
    }

    private void sendMessage() {
        iconActions.closeEmojIcon();
        if (new NetworkConnection(this).CheckConnection()==true)
        {
            if (!TextUtils.isEmpty(msg_et.getText().toString()))
            {

                String m =msg_et.getText().toString();
                byte[] m2 = new byte[0];
                try {
                    m2 = m.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                new Manager_Notification();
                EventBus.getDefault().post(new ID(curr_user_id));
                String msg = Base64.encodeToString(m2,Base64.DEFAULT);
                presenter.sendMessage(curr_user_id,chat_user_id,curr_user_name,chat_user_name,msg, chat_user_token,curr_user_image);
                msg_et.setText("");


            }
        }else
            {
                Toast.makeText(this, R.string.network_connection, Toast.LENGTH_SHORT).show();
            }
    }

    private void updateUi(String image_url,String user_chat_Name,String user_chat_Status)
    {
        user_chat_name.setText(user_chat_Name.toString());
        if (user_chat_Status.equals("Active Now"))
        {
            //user_chat_status.setText(getString(R.string.active_now));

        }else
            {
                //user_chat_status.setText(user_chat_Status);
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
    public void onMessagesSuccess(List<MessageModel> messageModelList) {
        this.messageModelList= messageModelList;
        adapter = new MessageAdapter(this,messageModelList,curr_user_id,chat_user_id,chat_user_type);
        adapter.notifyDataSetChanged();
        chatRecyclerView.setAdapter(adapter);
        chatRecyclerView.scrollToPosition(adapter.getItemCount()-1);
        chat_progress.setVisibility(View.GONE);
    }

    @Override
    public void onMessagesSuccess(MessageModel messageModel) {
        this.messageModelList.add(messageModel);
        adapter.notifyDataSetChanged();
        chatRecyclerView.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    public void onFailed(String error) {

        Log.e("error",error);
        chat_progress.setVisibility(View.GONE);

    }

    @Override
    public void onMessageSendSuccess() {
        presenter2.getMessages(curr_user_id,chat_user_id);
    }



    @Override
    protected void onStart() {
        super.onStart();
        presenter.getMessages(curr_user_id,chat_user_id);
    }
}
