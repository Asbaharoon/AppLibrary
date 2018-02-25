package com.semicolon.librarians.libraryguide.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Preferences;
import com.semicolon.librarians.libraryguide.Services.Tags;

import it.beppi.tristatetogglebutton_library.TriStateToggleButton;
import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Delta on 15/12/2017.
 */

public class Settings_Fragment extends Fragment {
    private TriStateToggleButton sound_btn,vibrarte_btn;
    private Preferences pref;
    private TextView sel_not;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment,container,false);
        initView(view);
        pref = new Preferences(getActivity());
        String soundState = pref.getSoundState();
        String vibrateState=pref.getVibrateState();

        if (!TextUtils.isEmpty(soundState)&&soundState.equals("on"))
        {
            sound_btn.setToggleOn();
        }else if (!TextUtils.isEmpty(soundState)&&soundState.equals("off"))
        {
            sound_btn.setToggleOff();
        }

        if (!TextUtils.isEmpty(vibrateState)&&vibrateState.equals("on"))
        {
            vibrarte_btn.setToggleOn();
        }else if (!TextUtils.isEmpty(vibrateState)&&vibrateState.equals("off"))
        {
            vibrarte_btn.setToggleOff();
        }

        return view;
    }

    private void initView(View view) {

        sound_btn = (TriStateToggleButton) view.findViewById(R.id.sound_btn);
        vibrarte_btn = (TriStateToggleButton) view.findViewById(R.id.vibrate_btn);
        sel_not = (TextView) view.findViewById(R.id.sel_not);

        sel_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE,"Select notification sound");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,(Uri)null);
                getActivity().startActivityForResult(intent,320);
            }
        });

        sound_btn.setOnColor(ActivityCompat.getColor(getActivity(),R.color.centercolor));
        vibrarte_btn.setOnColor(ActivityCompat.getColor(getActivity(),R.color.centercolor));

        sound_btn.setOnToggleChanged(new TriStateToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean b, int i) {
                switch (toggleStatus)
                {
                    case on:
                        pref.CreateSoundtoggle("on");
                        Toast.makeText(getActivity(), "sound on", Toast.LENGTH_SHORT).show();
                        break;
                    case off:
                        pref.CreateSoundtoggle("off");

                        Toast.makeText(getActivity(), "sound off", Toast.LENGTH_SHORT).show();

                }
            }
        });
        vibrarte_btn.setOnToggleChanged(new TriStateToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean b, int i) {
                switch (toggleStatus)
                {
                    case on:
                        pref.CreateVibratetoggle("on");
                        Toast.makeText(getActivity(), "vibrate on", Toast.LENGTH_SHORT).show();
                        break;
                    case off:
                        pref.CreateVibratetoggle("off");

                        Toast.makeText(getActivity(), "vibrate off", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==320 && resultCode== Activity.RESULT_OK&&data!=null)
        {
            Uri notUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            pref.CreatePref_forRingtone(notUri.toString());
        }
    }
}
