package com.example.omd.library.MVP.NearbyMVP;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Delta on 17/01/2018.
 */

public interface Presenter {
    /*void getNearbyUsers(String userType, LatLng latLng);
    void getNearbyPublishers(String userType,LatLng latLng);
    void getNearbyLibraries(String userType,LatLng latLng);
    void getNearbyUniversities(String userType,LatLng latLng);
    void getNearbyCompanies(String userType,LatLng latLng);*/
    void getNearbyUsers(String currUserType,String filteredUserType, LatLng currLatLng);

}
