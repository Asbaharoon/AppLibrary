package com.example.omd.library.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.omd.library.R;
import com.example.omd.library.Services.Tags;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

/**
 * Created by Delta on 08/12/2017.
 */

public class RegisterFragment extends Fragment {
    private ExpandableRelativeLayout layout_normal_user,layout_pub,layout_lib,library_spinner_expanded;
    private AppCompatSpinner spinner,lib_spinner;
    private MaterialEditText n_userFirstName,n_userLastName,n_userEmail,n_userPhone,n_userCountry,n_userJob,n_userInterests,n_userPassword;
    private MaterialEditText publisher_firstName,publisher_lastName,publisherEmail,publisherCountry,publisherPhone,publisherExpertise,publisher_webSite,publisherPassword;
    private MaterialEditText libraryName,libraryCommission,libraryCountry,libraryExpertise,libraryPassword,library_otherType;
    Context mContext;
    Handler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_fragment,container,false);
        initView(view);
        init_normalUserView(view);
        init_publisherView(view);
        init_libraryView(view);

        return view;
    }

    private void initView(View view)
    {
        handler = new Handler();
        mContext = view.getContext();
        layout_normal_user = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_normal_user);
        layout_pub = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_publisher);
        layout_lib = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_lib);
        library_spinner_expanded = (ExpandableRelativeLayout) view.findViewById(R.id.library_spinner_expanded);
        library_spinner_expanded.collapse();
        layout_normal_user.collapse();
        layout_lib.collapse();
        spinner = (AppCompatSpinner) view.findViewById(R.id.spinner);

        lib_spinner = (AppCompatSpinner) view.findViewById(R.id.lib_type);
        lib_spinner.setSelection(0);
        final ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(mContext,R.layout.spinner_row,getResources().getStringArray(R.array.libType));
        lib_spinner.setAdapter(adapter_type);
        lib_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getSelectedView()).setTextColor(ContextCompat.getColor(mContext,R.color.label));
                ((TextView)adapterView.getSelectedView()).setGravity(Gravity.CENTER);

                if (adapterView.getSelectedItem().toString().equals("Other"))
                {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            library_spinner_expanded.expand();

                        }
                    },500);

                }
                else
                {
                    library_spinner_expanded.collapse();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,R.layout.spinner_row,getResources().getStringArray(R.array.spinnerArray));
        spinner.setAdapter(adapter);
        spinner.getBackground().setColorFilter(ContextCompat.getColor(mContext,R.color.label), PorterDuff.Mode.SRC_ATOP);

        if (spinner.getSelectedItem().toString().equals("Normal user"))
        {
            if (layout_pub.isExpanded()||layout_lib.isExpanded())
            {
                layout_pub.collapse();
                layout_lib.collapse();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout_normal_user.expand();
                    }
                },1000);
            }
            else
            {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout_normal_user.expand();
                    }
                },1000);
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getSelectedView()).setGravity(Gravity.CENTER);
                ((TextView)adapterView.getSelectedView()).setTextColor(ContextCompat.getColor(mContext,R.color.label));
                if (adapterView.getSelectedItem().toString().equals("Normal user"))
                {
                    if (layout_pub.isExpanded()||layout_lib.isExpanded())
                    {
                        layout_pub.collapse();
                        layout_lib.collapse();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                layout_normal_user.expand();
                            }
                        },1000);
                    }
                    else
                    {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                layout_normal_user.expand();
                            }
                        },1000);

                    }
                }
                else if (adapterView.getSelectedItem().toString().equals("Publisher"))
                {
                    if (layout_normal_user.isExpanded()||layout_lib.isExpanded())
                    {
                        layout_normal_user.collapse();
                        layout_lib.collapse();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                layout_pub.expand();
                            }
                        },1000);
                    }
                    else
                    {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                layout_pub.expand();
                            }
                        },1000);
                    }

                }else if (adapterView.getSelectedItem().toString().equals("Library"))
                {
                    if (layout_normal_user.isExpanded()||layout_pub.isExpanded())
                    {
                        layout_normal_user.collapse();
                        layout_pub.collapse();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                layout_lib.expand();
                            }
                        },1000);
                    }
                    else
                    {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                layout_lib.expand();
                            }
                        },1000);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private void init_normalUserView(View view)
    {
        View userView = view.findViewById(R.id.normal_user_layout);

        n_userFirstName = (MaterialEditText) userView.findViewById(R.id.user_firstName);
        n_userFirstName.setAutoValidate(true);
        n_userFirstName.setShowClearButton(true);

        n_userLastName  = (MaterialEditText) userView.findViewById(R.id.user_lastName);
        n_userLastName.setAutoValidate(true);
        n_userLastName.setShowClearButton(true);

        n_userEmail = (MaterialEditText) userView.findViewById(R.id.user_email);
        n_userEmail.setAutoValidate(true);
        n_userEmail.setShowClearButton(true);
        n_userEmail.addValidator(new RegexpValidator(getString(R.string.invalidEmail),Tags.email_Regex));

        n_userPhone = (MaterialEditText) userView.findViewById(R.id.user_phone);
        n_userPhone.setAutoValidate(true);
        n_userPhone.setShowClearButton(true);

        n_userCountry = (MaterialEditText) userView.findViewById(R.id.user_country);
        n_userCountry.setAutoValidate(true);
        n_userCountry.setShowClearButton(true);

        n_userJob = (MaterialEditText) userView.findViewById(R.id.user_job);
        n_userJob.setAutoValidate(true);
        n_userJob.setShowClearButton(true);

        n_userInterests = (MaterialEditText) userView.findViewById(R.id.user_interests);
        n_userInterests.setAutoValidate(true);
        n_userInterests.setShowClearButton(true);

        n_userPassword = (MaterialEditText) userView.findViewById(R.id.user_password);
        n_userPassword.setAutoValidate(true);
        n_userPassword.setShowClearButton(true);

    }

    private void init_publisherView(View view)
    {
        View publisherView = view.findViewById(R.id.publisher_layout);
        publisher_firstName = (MaterialEditText) publisherView.findViewById(R.id.publisher_firstName);
        publisher_firstName.setAutoValidate(true);
        publisher_firstName.setShowClearButton(true);

        publisher_lastName = (MaterialEditText) publisherView.findViewById(R.id.publisher_lasttName);
        publisher_lastName.setAutoValidate(true);
        publisher_lastName.setShowClearButton(true);

        publisherEmail = (MaterialEditText) publisherView.findViewById(R.id.publisher_email);
        publisherEmail.setAutoValidate(true);
        publisherEmail.setShowClearButton(true);
        publisherEmail.addValidator(new RegexpValidator(getString(R.string.invalidEmail), Tags.email_Regex));

        publisherCountry = (MaterialEditText) publisherView.findViewById(R.id.publisher_country);
        publisherCountry.setAutoValidate(true);
        publisherCountry.setShowClearButton(true);

        publisherPhone = (MaterialEditText) publisherView.findViewById(R.id.publisher_phone);
        publisherPhone.setAutoValidate(true);
        publisherPhone.setShowClearButton(true);

        publisherExpertise = (MaterialEditText) publisherView.findViewById(R.id.publisher_expertise);
        publisherExpertise.setAutoValidate(true);
        publisherExpertise.setShowClearButton(true);

        publisher_webSite = (MaterialEditText) publisherView.findViewById(R.id.publisher_website);
        publisher_webSite.setAutoValidate(true);
        publisher_webSite.setShowClearButton(true);
        publisher_webSite.addValidator(new RegexpValidator(getString(R.string.invalid_url),Tags.url_Regex));

        publisherPassword = (MaterialEditText) publisherView.findViewById(R.id.publisher_password);
        publisherPassword.setAutoValidate(true);
        publisherPassword.setShowClearButton(true);




    }

    private void init_libraryView(View view)
    {
        View libraryView = view.findViewById(R.id.library_layout);
        libraryName = (MaterialEditText) libraryView.findViewById(R.id.library_Name);
        libraryName.setAutoValidate(true);
        libraryName.setShowClearButton(true);

        libraryCommission = (MaterialEditText) libraryView.findViewById(R.id.library_commission);
        libraryCommission.setAutoValidate(true);
        libraryCommission.setShowClearButton(true);

        libraryCountry = (MaterialEditText) libraryView.findViewById(R.id.library_country);
        libraryCountry.setAutoValidate(true);
        libraryCountry.setShowClearButton(true);

        libraryExpertise = (MaterialEditText) libraryView.findViewById(R.id.library_expertise);
        libraryExpertise.setAutoValidate(true);
        libraryExpertise.setShowClearButton(true);

        libraryPassword = (MaterialEditText) libraryView.findViewById(R.id.library_password);
        libraryPassword.setAutoValidate(true);
        libraryPassword.setShowClearButton(true);

        library_otherType = (MaterialEditText) libraryView.findViewById(R.id.library_otherType);
        library_otherType.setAutoValidate(true);
        library_otherType.setShowClearButton(true);

    }


}
