package de.hshl.isd.parkaid

import com.google.android.gms.maps.model.LatLng

 class Adresse(latlngx: LatLng, strassex:String, ortx:String){
     var latlng: LatLng
     var strasse: String
     var ort:String

    init{
        latlng=latlngx
        strasse=strassex
        ort=ortx
    }
}