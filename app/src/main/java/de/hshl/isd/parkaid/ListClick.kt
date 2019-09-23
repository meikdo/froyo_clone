package de.hshl.isd.parkaid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.list_click.*
import kotlinx.android.synthetic.main.parkhaus_fragment.*


class ListClick (parkhaus: Parkhaus_m) : Fragment(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    private var mapFragment : SupportMapFragment?=null

    private var mParkhaus:Parkhaus_m

    companion object {
        fun newInstance(parkhaus: Parkhaus_m) = ListClick(parkhaus)
    }

    init{
        mParkhaus=parkhaus
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_click, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //*MAP*
        if(mapFragment==null){
            mapFragment = SupportMapFragment.newInstance()
            fragmentManager?.inTransaction {
                replace(
                    R.id.mapListClick,
                    mapFragment!!
                )
            }
        }
        mapFragment?.getMapAsync(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showElement()
        Filter()
        Preisfilter()
        var preis:Double= "%.2f".format(mParkhaus.preis).toDouble()
        parkenButton.setOnClickListener {


            fragmentManager!!.inTransaction {
                replace(
                    R.id.main_screen,
                    Parkhaus.newInstance(true, preis, mParkhaus.closeTime)
                )
            }
        }
    }


    fun showElement(){
        name.text=mParkhaus.name
        preis.text="Preis: " + mParkhaus.preisString + "/h"

        strasse.text="Straße \n\n" + mParkhaus.strasse
        ort.text="Ort \n\n" + mParkhaus.ort
        distanz.text= "Distanz \n\n" + mParkhaus.distanceString
        endzeitph.text="Öffnungszeiten \n\n" + mParkhaus.openTime + " - " + mParkhaus.closeTime
        parkhausBild.setImageBitmap(mParkhaus.bitmap)
    }

    fun Filter(){
        if (mParkhaus.handicap==true){
            filter1.setBackgroundResource(R.drawable.green2)
        }
        else{
            filter1.setBackgroundResource(R.drawable.red)
        }
        if (mParkhaus.womens==true) {
            filter2.setBackgroundResource(R.drawable.green2)
        }
        else{
            filter2.setBackgroundResource(R.drawable.red)
            }
        }
    fun Preisfilter(){
        if (mParkhaus.preis<=1.20) {
            preis.setBackgroundResource(R.drawable.green2)
        }else if (mParkhaus.preis>=2.00) {
            preis.setBackgroundResource(R.drawable.red)
        }else{
            preis.setBackgroundResource(R.drawable.orange)
        }
    }


    //**MAP
    override fun onMapReady(p0: GoogleMap?) {
        mMap=p0!!

        //FÜGE MARKER HINZU
            val geo = LatLng(mParkhaus.koordinate.latitude, mParkhaus.koordinate.longitude)
            val markerOptions: MarkerOptions = MarkerOptions().position(geo).title(mParkhaus.name)
            mMap.addMarker(markerOptions)

        //SET CENTER
        val minZoom=16.0f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geo, minZoom))

    }

}



