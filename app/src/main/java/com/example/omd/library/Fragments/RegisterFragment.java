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
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

/**
 * Created by Delta on 08/12/2017.
 */

public class RegisterFragment extends Fragment {
    private ExpandableRelativeLayout layout_normal_user,layout_pub,layout_lib,library_spinner_expanded;
    private AppCompatSpinner spinner,lib_spinner;
    Context mContext;
    Handler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_fragment,container,false);
        initView(view);

        return view;
    }

    private void initView(View view) {
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
}
