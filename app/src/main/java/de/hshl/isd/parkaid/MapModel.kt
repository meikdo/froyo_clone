package de.hshl.isd.parkaid

import android.location.Location

class MapModel  {

    private var latitude = 51.682874
    private var longitude = 7.838794
    private lateinit var center: Location

    init{
        setCenter()
    }


    fun setCenter() {
        center= Location("")
        center.latitude = 51.682874
        center.longitude = 7.838794
    }


    fun getLocation(): Location{
        val location:Location= Location("")
        location.latitude = 51.682874
        location.longitude = 7.838794
        return location
    }

}






