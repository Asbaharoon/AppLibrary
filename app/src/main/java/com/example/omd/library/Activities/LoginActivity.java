package com.example.omd.library.Activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.example.omd.library.Fragments.LoginFragment;
import com.example.omd.library.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        getFragmentManager().beginTransaction().add(R.id.fragmentContainer,new LoginFragment()).commit();
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.omd.library", PackageManager.GET_SIGNATURES);
            for (Signature signature:info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                String key = Base64.encodeToString(md.digest(),Base64.DEFAULT);
                Log.e("fffffffffffffff",key);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void initView() {


    }

}
