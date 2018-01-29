package com.example.omd.library.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.omd.library.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.chat_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        customToolBarView = LayoutInflater.from(this).inflate(R.layout.chat_custom_toolbar,null);
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
    }
}
