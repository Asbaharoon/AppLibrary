package com.semicolon.librarians.libraryguide.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;
import com.intrusoft.squint.DiagonalView;
import com.semicolon.librarians.libraryguide.Activities.Activity_PhoneNumber;
import com.semicolon.librarians.libraryguide.Activities.Activity_Search_Results;
import com.semicolon.librarians.libraryguide.Activities.Profile_Items;
import com.semicolon.librarians.libraryguide.MVP.UpdateUsersProfile_MVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.UpdateUsersProfile_MVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.UpdateUsersProfile_MVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.CountriesModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Delta on 24/02/2018.
 */

public class FragmentUpdate_UserProfile extends Fragment implements ViewData,View.OnClickListener {

    private NormalUserData userData;
    private ImageView userImage;
    private DiagonalView secondImage;
    private TextView userId,user_name,user_phone,user_email,user_country;
    private String encodedImage;
    private Button updateBtn;
    private final int SEL_IMAGE_REQ_CODE=333;
    private final int NAME_REQ_CODE     = 444;
    private final int EMAIL_REQ_CODE    = 555;
    private final int COUNTRY_REQ_CODE  = 666;
    private final int PHONE_REQ_CODE    = 777;
    private String country_id ;
    private ProgressDialog dialog;
    private Presenter presenter;


    private Target target;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.update_user_profile,container,false);
        initView(view);
        presenter = new PresenterImp(this,getActivity());
        CreateProgressDialog();
        getDataFromBundle();
        return view;
    }



    private void initView(View view) {
        userImage   = (ImageView) view.findViewById(R.id.userImage);
        secondImage = (DiagonalView) view.findViewById(R.id.secondImage);
        userId      = (TextView) view.findViewById(R.id.userId);
        user_name   = (TextView) view.findViewById(R.id.userName);
        user_email  = (TextView) view.findViewById(R.id.userEmail);
        user_phone  = (TextView) view.findViewById(R.id.userPhone);
        user_country= (TextView) view.findViewById(R.id.userCountry);
        updateBtn   = (Button) view.findViewById(R.id.user_updBtn);

        updateBtn.setOnClickListener(this);
        userImage.setOnClickListener(this);
        user_name.setOnClickListener(this);
        user_email.setOnClickListener(this);
        user_phone.setOnClickListener(this);
        user_country.setOnClickListener(this);



    }

    private void getDataFromBundle() {
        Bundle bundle = getArguments();

        if (bundle!=null)
        {
            userData = (NormalUserData) bundle.getSerializable("userData");
            if (userData!=null)
            {
                UpdateUI(userData);
            }
        }
    }

    private void UpdateUI(NormalUserData userData) {

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                userImage.setImageBitmap(bitmap);
                secondImage.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        if (userData.getUserPhoto()==null)
        {
            Picasso.with(getActivity()).load(Uri.parse(Tags.image_path+userData.getUser_photo())).placeholder(R.drawable.user_profile).into(target);
        }else
            {
               Picasso.with(getActivity()).load(Uri.parse(userData.getUserPhoto())).placeholder(R.drawable.user_profile).into(target);
            }

        userId.setText(userData.getUserId());
        user_name.setText(userData.getUserName());
        user_email.setText(userData.getUserEmail());
        user_phone.setText(userData.getUserPhone());
        user_country.setText(userData.getUserCountry());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.userImage:
                SelectImage();
                break;
            case R.id.userName:
                Intent n_intent = new Intent(getActivity(), Profile_Items.class);
                n_intent.putExtra("name",user_name.getText().toString());
                startActivityForResult(n_intent,NAME_REQ_CODE);
                break;
            case R.id.userEmail:
                Intent e_intent = new Intent(getActivity(), Profile_Items.class);
                e_intent.putExtra("email",user_email.getText().toString());
                startActivityForResult(e_intent,EMAIL_REQ_CODE);
                break;
            case R.id.userPhone :
                Intent p_intent = new Intent(getActivity(), Activity_PhoneNumber.class);
                p_intent.putExtra("phone",user_phone.getText().toString());
                startActivityForResult(p_intent,PHONE_REQ_CODE);
                break;
            case R.id.userCountry:
                Intent c_intent = new Intent(getActivity(), Activity_Search_Results.class);
                c_intent.putExtra("searchType","country");
                getActivity().startActivityForResult(c_intent,COUNTRY_REQ_CODE);
                break;
            case R.id.user_updBtn:
                String u_name   =user_name.getText().toString();
                String u_email   =user_email.getText().toString();
                String u_phone  =user_phone.getText().toString();
                String u_country=user_country.getText().toString();
                String u_id = userId.getText().toString();

                presenter.UpdateUserData(encodedImage,u_id,u_name,u_email,u_phone,u_country);
                break;
        }
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent.createChooser(intent,getString(R.string.ch_photo)),SEL_IMAGE_REQ_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SEL_IMAGE_REQ_CODE && resultCode==getActivity().RESULT_OK && data!=null)
        {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                userImage.setImageBitmap(bitmap);
                secondImage.setImageBitmap(bitmap);
                encodedImage = EncodeImage(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }else if (requestCode==NAME_REQ_CODE && resultCode==getActivity().RESULT_OK && data!=null)
        {
            String  name = data.getStringExtra("username");

            user_name.setText(name);
        }
        else if (requestCode==EMAIL_REQ_CODE && resultCode==getActivity().RESULT_OK && data!=null)
        {
            String  email = data.getStringExtra("useremail");

            user_email.setText(email);
        }
        else if (requestCode==PHONE_REQ_CODE && resultCode==getActivity().RESULT_OK && data!=null)
        {
            String  phone = data.getStringExtra("phone_number");

            user_phone.setText(phone);
        }
        else if (requestCode==COUNTRY_REQ_CODE && resultCode==getActivity().RESULT_OK && data!=null)
        {
            CountriesModel countriesModel = (CountriesModel) data.getSerializableExtra("country_data");
            country_id = countriesModel.getCountry_id();
            user_country.setText(countriesModel.getCountry_title()+"-"+countriesModel.getCountry_flag());

        }
    }

    private String EncodeImage(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream);
        return Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);
    }

    private void CreateProgressDialog()
    {
        dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        dialog.setMessage(getString(R.string.wait_update_profile));
        ProgressBar progressBar = new ProgressBar(getActivity());
        Drawable drawable =progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ActivityCompat.getColor(getActivity(),R.color.centercolor), PorterDuff.Mode.SRC_IN);
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Picasso.with(getActivity()).cancelRequest(target);

    }///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void setNormalUserPhone_Error() {
        Toast.makeText(getActivity(), getActivity().getString(R.string.empty_name), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNormalUserEmail_Error() {
        Toast.makeText(getActivity(), getActivity().getString(R.string.email_empty), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setNormalUser_invalidEmail_Error() {
        Toast.makeText(getActivity(), getActivity().getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setNormalUserCountry_Error() {
        Toast.makeText(getActivity(), getActivity().getString(R.string.country_empty), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void onNormalUserDataSuccess(NormalUserData normalUserData) {
        UpdateUI(normalUserData);
        dialog.dismiss();

        new CDialog(getActivity()).createAlert(getString(R.string.prof_up_suc), CDConstants.SUCCESS,CDConstants.MEDIUM)
                .setAnimation(CDConstants.SCALE_FROM_TOP_TO_BOTTOM)
                .setDuration(2000)
                .setTextSize(CDConstants.LARGE_TEXT_SIZE)
                .show();

    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
        dialog.dismiss();

    }
}
