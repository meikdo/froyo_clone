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
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.parken_nb_fragment.*


class ParkenNB (objekt: pObjekt) : Fragment(), OnMapReadyCallback {

    private var objektP : pObjekt
    private lateinit var mMap: GoogleMap
    private var mapFragment : SupportMapFragment?=null



    companion object {
        fun newInstance(objektx: pObjekt) = ParkenNB(objektx)
    }
    init {
        objektP=objekt

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //*MAP*
        if(mapFragment==null){
            mapFragment = SupportMapFragment.newInstance()
            fragmentManager?.inTransaction {
                replace(
                    R.id.mapParkenNB,
                    mapFragment!!
                )
            }
        }
        mapFragment?.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.parken_nb_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showElement()
    }

    fun showElement(){
        ebeneph.text="Ebene: " + objektP.ebene
        preisph.text="Preis/Stunde: " + objektP.preis + "€"
        nummerph.text="Nummer: " + objektP.nummer
        endzeitph.text="Schließt um" + "\n" + objektP.endzeit + " Uhr"
        parkzeitph.text= "Parkanfang: " + objektP.parkzeit + " Uhr"
        zeitNB.text="weniger als 1 Stunde"
        kostenNB.text=objektP.preis.toString() + "€"
    }

    //**MAP
    override fun onMapReady(p0: GoogleMap?) {
        mMap=p0!!

        mMap.addMarker(
            MarkerOptions().position(objektP.standort).title("Parkplatz").icon(
                BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN)))
        val minZoom=16.0f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(objektP.standort, minZoom))

    }






}
