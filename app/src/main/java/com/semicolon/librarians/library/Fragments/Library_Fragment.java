package com.semicolon.librarians.library.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.semicolon.librarians.library.Activities.Activity_Search;
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

public class Library_Fragment extends Fragment {
    private NormalUserData user_Data = null;
    private PublisherModel publisher_Model = null;
    private LibraryModel library_Model = null;
    private UniversityModel university_Model = null;
    private CompanyModel company_Model = null;
    private Menu menu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library_fragment,container,false);
        initView(view);
        getDataFromBundle();

        return view;
    }

    private void initView(View view) {



    }
    private void getDataFromBundle() {
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if (bundle.getSerializable("user_data")!=null)
            {
                user_Data = (NormalUserData) bundle.getSerializable("user_data");
                Toast.makeText(getActivity(), user_Data.getUserName()+"\n"+user_Data.getUserType(), Toast.LENGTH_SHORT).show();

            }
            else if (bundle.getSerializable("publisherData")!=null)
            {
                publisher_Model = (PublisherModel) bundle.getSerializable("publisherData");
                Toast.makeText(getActivity(), publisher_Model.getPub_name()+"\n"+publisher_Model.getUser_type(), Toast.LENGTH_SHORT).show();

            }
            else if (bundle.getSerializable("universityData")!=null)
            {
                university_Model = (UniversityModel) bundle.getSerializable("universityData");
                Toast.makeText(getActivity(), university_Model.getUni_name()+"\n"+university_Model.getUser_type(), Toast.LENGTH_SHORT).show();

            }
            else if (bundle.getSerializable("libraryData")!=null)
            {
                library_Model = (LibraryModel) bundle.getSerializable("libraryData");
                Toast.makeText(getActivity(), library_Model.getLib_name()+"\n"+library_Model.getUser_type(), Toast.LENGTH_SHORT).show();

            }
            else if (bundle.getSerializable("companyData")!=null)
            {
                company_Model = (CompanyModel) bundle.getSerializable("companyData");
                Toast.makeText(getActivity(), company_Model.getComp_name()+"\n"+company_Model.getUser_type(), Toast.LENGTH_SHORT).show();

            }
        }
    }
   /* private void getDataFromBundle() {

        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if ( bundle.getSerializable("userData")!=null)
            {
                user_Data = (NormalUserData) bundle.getSerializable("userData");
                Toast.makeText(getActivity(), user_Data.getUserName()+"\n"+user_Data.getUserType(), Toast.LENGTH_SHORT).show();
            }else if (bundle.getSerializable("publisherData")!=null)
            {
                publisher_Model = (PublisherModel) bundle.getSerializable("publisherData");
                Toast.makeText(getActivity(), publisher_Model.getPub_name()+"\n"+publisher_Model.getUser_type(), Toast.LENGTH_SHORT).show();

            }else if (bundle.getSerializable("libraryData")!=null)
            {
                libraryModel = (LibraryModel) bundle.getSerializable("libraryData");
                Toast.makeText(getActivity(), libraryModel.getLib_name()+"\n"+libraryModel.getUser_type(), Toast.LENGTH_SHORT).show();

            }
            else if (bundle.getSerializable("universityData")!=null)
            {
                university_Model = (UniversityModel) bundle.getSerializable("universityData");
                Toast.makeText(getActivity(), university_Model.getUni_name()+"\n"+university_Model.getUser_type(), Toast.LENGTH_SHORT).show();

            }
            else if (bundle.getSerializable("companyData")!=null)
            {
                company_Model = (CompanyModel) bundle.getSerializable("companyData");
                Toast.makeText(getActivity(), company_Model.getComp_name()+"\n"+company_Model.getUser_type(), Toast.LENGTH_SHORT).show();

            }
        }
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.library_search,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.library_searchIcon)
        {
            Intent intent = new Intent(getActivity(), Activity_Search.class);
            intent.putExtra("library_search","library");
            getActivity().startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }


}
