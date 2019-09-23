package de.hshl.isd.parkaid

import com.google.android.gms.maps.model.LatLng

class pObjekt (var ebeneP: String, var nummerP: String, var preisP: Double, var endzeitP: String, var parkzeitP: String, var standortP: LatLng) {



    var ebene : String
    var nummer : String
    var preis: Double
    var endzeit : String
    var parkzeit : String
    var standort: LatLng

    init {
        parkzeit = parkzeitP
        ebene = ebeneP
        nummer = nummerP
        preis = preisP
        endzeit = endzeitP
        standort=standortP
    }
}