package com.semicolon.librarians.library.Models;

import android.location.Location;

/**
 * Created by Delta on 10/02/2018.
 */

public class Location_Model {
    Location location =null;

    public Location getLocation() {
        return location;
    }

    public Location_Model(Location location) {
        this.location = location;
    }
}
