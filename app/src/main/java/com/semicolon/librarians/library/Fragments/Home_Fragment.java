package com.semicolon.librarians.library.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.semicolon.librarians.library.Activities.HomeActivity;
import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;
import com.semicolon.librarians.library.R;
import com.semicolon.librarians.library.Services.Tags;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Delta on 15/12/2017.
 */

public class Home_Fragment extends Fragment  {
    private Context mContext;
    private AHBottomNavigation navBar;
    private NormalUserData user_Data = null;
    private PublisherModel publisher_Model = null;
    private LibraryModel library_Model = null;
    private UniversityModel university_Model = null;
    private CompanyModel company_Model = null;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        initView(view);

        setUpnavBar();
        getDataFromBundle();

        CheckCurrentUser_LoggedIn();
        return view;
    }

    private void CheckCurrentUser_LoggedIn() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user_Data!=null)
                {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user_data",user_Data);
                    Chat_Fragment chat_fragment = new Chat_Fragment();
                    chat_fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_fragmentsContainer,chat_fragment,"chat_fragment").addToBackStack(null).commitAllowingStateLoss();
                    navBar.setCurrentItem(0,false);

                }else if (publisher_Model!=null)
                {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("publisherData",publisher_Model);
                    Chat_Fragment chat_fragment = new Chat_Fragment();
                    chat_fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_fragmentsContainer,chat_fragment,"chat_fragment").addToBackStack(null).commitAllowingStateLoss();
                    navBar.setCurrentItem(0,false);
                }
                else if (library_Model!=null)
                {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("libraryData",library_Model);
                    Chat_Fragment chat_fragment = new Chat_Fragment();
                    chat_fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_fragmentsContainer,chat_fragment,"chat_fragment").addToBackStack(null).commitAllowingStateLoss();
                    navBar.setCurrentItem(0,false);

                }
                else if (university_Model!=null)
                {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("universityData",university_Model);
                    Chat_Fragment chat_fragment = new Chat_Fragment();
                    chat_fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_fragmentsContainer,chat_fragment,"chat_fragment").addToBackStack(null).commitAllowingStateLoss();
                    navBar.setCurrentItem(0,false);

                }
                else if (company_Model!=null)
                {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("companyData",company_Model);
                    Chat_Fragment chat_fragment = new Chat_Fragment();
                    chat_fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_fragmentsContainer,chat_fragment,"chat_fragment").addToBackStack(null).commitAllowingStateLoss();
                    navBar.setCurrentItem(0,false);

                }
            }
        },200);
    }

    private void getDataFromBundle() {
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if (bundle.getSerializable("user_data")!=null)
            {
                user_Data = (NormalUserData) bundle.getSerializable("user_data");
            }
            else if (bundle.getSerializable("publisherData")!=null)
            {
                publisher_Model = (PublisherModel) bundle.getSerializable("publisherData");
            }
            else if (bundle.getSerializable("universityData")!=null)
            {
                university_Model = (UniversityModel) bundle.getSerializable("universityData");
            }
            else if (bundle.getSerializable("libraryData")!=null)
            {
                library_Model = (LibraryModel) bundle.getSerializable("libraryData");
            }
            else if (bundle.getSerializable("companyData")!=null)
            {
                company_Model = (CompanyModel) bundle.getSerializable("companyData");
            }
        }
    }


    private void initView(View view) {
        mContext = view.getContext();
        navBar = (AHBottomNavigation)view.findViewById(R.id.bottom_navBar);


    }
    private void setUpnavBar()
    {

        AHBottomNavigationAdapter adapter = new AHBottomNavigationAdapter(getActivity(),R.menu.navbar_menu);
        navBar.setDefaultBackgroundColor(ContextCompat.getColor(getActivity(),R.color.base));
        navBar.setInactiveColor(ContextCompat.getColor(getActivity(),R.color.dark_gray));
        navBar.setAccentColor(ContextCompat.getColor(getActivity(), R.color.centercolor));
        adapter.setupWithBottomNavigation(navBar);
        navBar.setCurrentItem(0);
        navBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        navBar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position==0)
                {

                    if (user_Data!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user_data",user_Data);
                        Chat_Fragment chat_fragment = new Chat_Fragment();
                        chat_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,chat_fragment,"chat_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }else if (publisher_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("publisherData",publisher_Model);
                        Chat_Fragment chat_fragment = new Chat_Fragment();
                        chat_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,chat_fragment,"chat_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (library_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("libraryData",library_Model);
                        Chat_Fragment chat_fragment = new Chat_Fragment();
                        chat_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,chat_fragment,"chat_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (university_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("universityData",university_Model);
                        Chat_Fragment chat_fragment = new Chat_Fragment();
                        chat_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,chat_fragment,"chat_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (company_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("companyData",company_Model);
                        Chat_Fragment chat_fragment = new Chat_Fragment();
                        chat_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,chat_fragment,"chat_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }


                    return true;
                }
                else if (position==1)
                {
                    if (user_Data!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user_data",user_Data);
                        University_Fragment university_fragment = new University_Fragment();
                        university_fragment.setArguments(bundle);
                        getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,university_fragment,"uni_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);
                    }else if (publisher_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("publisherData",publisher_Model);
                        University_Fragment university_fragment = new University_Fragment();
                        university_fragment.setArguments(bundle);
                        getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,university_fragment,"uni_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (library_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("libraryData",library_Model);
                        University_Fragment university_fragment = new University_Fragment();
                        university_fragment.setArguments(bundle);
                        getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,university_fragment,"uni_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (university_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("universityData",university_Model);
                        University_Fragment university_fragment = new University_Fragment();
                        university_fragment.setArguments(bundle);
                        getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,university_fragment,"uni_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (company_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("companyData",company_Model);
                        University_Fragment university_fragment = new University_Fragment();
                        university_fragment.setArguments(bundle);
                        getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,university_fragment,"uni_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }



                    return true;

                }
                else if (position==2)
                {


                    if (user_Data!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user_data",user_Data);
                        Library_Fragment library_fragment = new Library_Fragment();
                        library_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,library_fragment,"lib_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }else if (publisher_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("publisherData",publisher_Model);
                        Library_Fragment library_fragment = new Library_Fragment();
                        library_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,library_fragment,"lib_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (library_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("libraryData",library_Model);
                        Library_Fragment library_fragment = new Library_Fragment();
                        library_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,library_fragment,"lib_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (university_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("universityData",university_Model);
                        Library_Fragment library_fragment = new Library_Fragment();
                        library_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,library_fragment,"lib_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (company_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("companyData",company_Model);
                        Library_Fragment library_fragment = new Library_Fragment();
                        library_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,library_fragment,"lib_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }

                    return true;

                }
                else if (position==3)
                {


                    if (user_Data!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user_data",user_Data);
                        Publisher_Fragment publisher_fragment = new Publisher_Fragment();
                        publisher_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,publisher_fragment,"pub_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }else if (publisher_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("publisherData",publisher_Model);
                        Publisher_Fragment publisher_fragment = new Publisher_Fragment();
                        publisher_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,publisher_fragment,"pub_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (library_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("libraryData",library_Model);
                        Publisher_Fragment publisher_fragment = new Publisher_Fragment();
                        publisher_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,publisher_fragment,"pub_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (university_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("universityData",university_Model);
                        Publisher_Fragment publisher_fragment = new Publisher_Fragment();
                        publisher_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,publisher_fragment,"pub_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }
                    else if (company_Model!=null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("companyData",company_Model);
                        Publisher_Fragment publisher_fragment = new Publisher_Fragment();
                        publisher_fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainer,publisher_fragment,"pub_fragment").addToBackStack(null).commit();
                        navBar.setCurrentItem(position,false);

                    }

                    return true;

                }
                return false;
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Toolbar toolbar = ((HomeActivity)getActivity()).getToolBar();
        toolbar.getMenu().clear();
    }
}
