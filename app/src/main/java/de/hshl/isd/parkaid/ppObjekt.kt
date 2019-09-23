package de.hshl.isd.parkaid

import com.google.android.gms.maps.model.LatLng

class ppObjekt(var NamePP : String, var preisPP: Double, var endzeitPP: String, var paidTime: Double, var startPark: String, var standortPP: LatLng ) {


    var name: String
    var preis: Double
    var endzeit: String
    var bezahlt: Double
    var parkstart: String
    var Kosten: Double
    var standort: LatLng


    init {
        bezahlt = paidTime
        name = NamePP
        preis = preisPP
        endzeit = endzeitPP
        parkstart=startPark
        Kosten=preis*bezahlt
        standort=standortPP


    }

}