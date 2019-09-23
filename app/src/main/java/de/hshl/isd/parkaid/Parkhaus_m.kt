package de.hshl.isd.parkaid

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import java.sql.Time

import kotlin.random.Random


class Parkhaus_m (var idx:Int, var select_timingx:String, var handicapx: Boolean, var womensx: Boolean, var elekrox: Boolean
) {

    lateinit var name: String
    var bitmap:Bitmap?=null
    var id: Int
    var select_timing: String

    var distance: Double=0.0
    lateinit var distanceString: String

    var preis: Double=0.0
    lateinit var preisString:String
    lateinit var preisIndikator:String

    lateinit var openTime: String
    lateinit var closeTime: String

    var mapModel : MapModel = MapModel()
    var handicap: Boolean
    var womens: Boolean
    var elektro: Boolean

    var adresse: Adresse=Adresse(LatLng(51.682874,7.838794), "Paracelsuspark 1", "59063 Hamm")
    var strasse: String = ""
    var ort: String = ""
    var koordinate: Location=Location("")



    init {
        id=idx
        handicap = handicapx
        womens = womensx
        elektro = elekrox
        select_timing = select_timingx
        setTime()
        generatePreis()

    }

    fun CalculateDistance(StartP: Location, EndP: Location): Double {
        return StartP.distanceTo(EndP).toDouble()
    }

    fun set(){
        strasse=adresse.strasse
        koordinate.latitude=adresse.latlng.latitude
        koordinate.longitude=adresse.latlng.longitude
        ort=adresse.ort
        distance = CalculateDistance(mapModel.getLocation(), koordinate)
        if (distance > 1000) {
            val cache=distance/1000
            distanceString="%.2f".format((cache)).toString() + " km"  //in km
        } else {
            distanceString = "%.0f".format(distance).toString() + " m" //in meter
        }
    }

    fun generatePreis(){
        val preisArray=ArrayList<Double>()
        preisArray.add(0.90)
        preisArray.add(1.00)
        preisArray.add(1.20)
        preisArray.add(1.40)
        preisArray.add(1.80)
        preisArray.add(2.00)
        preisArray.add(2.20)
        preisArray.add(2.50)
        preisArray.add(0.80)
        preisArray.add(0.60)
        preisArray.add(1.10)
        preisArray.add(1.30)
        preisArray.add(2.40)

        val rand= Random.nextInt(0, preisArray.size-1)
        preis=preisArray[rand]
        preisString="%.2f".format(preis) + "€"

    }


    fun setTime(){
        var rand=Random.nextInt(0, 2)
        var open=ArrayList<Int>()
        open.add(6)
        open.add(7)
        open.add(8)
        openTime=open[rand].toString() + ":00"

        rand=Random.nextInt(0,2)
        var end=ArrayList<Int>()
        end.add(23)
        end.add(0)
        end.add(2)
        closeTime=end[rand].toString() + ":00"
    }


}
