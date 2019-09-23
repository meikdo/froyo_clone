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
import kotlinx.android.synthetic.main.parken_nbp_fragment.*


class ParkenNBPP (objekt: ppObjekt) : Fragment(), OnMapReadyCallback {

    private var objektPP : ppObjekt
    private lateinit var mMap: GoogleMap
    private var mapFragment : SupportMapFragment?=null

    companion object {
        fun newInstance(ppObjektx: ppObjekt) = ParkenNBPP(ppObjektx)
    }
    init {
        objektPP=objekt
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //*MAP*
        if(mapFragment==null){
            mapFragment = SupportMapFragment.newInstance()
            fragmentManager?.inTransaction {
                replace(
                    R.id.mapParkenNBPP,
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
        return inflater.inflate(R.layout.parken_nbp_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        zeigPlatz()
    }
    fun zeigPlatz(){
        preispp.text="Preis/Stunde: \n" + objektPP.preis
        namepp.text=objektPP.name
        Zeitbezahlt.text="Ticket gilt für:" + "\n" + objektPP.bezahlt + " Stunden"
        endzeitpp.text="Schließt um: " + "\n" + objektPP.endzeit + "Uhr"
        startzeitpp.text="Parkstart: " + objektPP.parkstart
        Kosten.text="Bisher bezahlt: \n" + objektPP.Kosten

        zeitNBPP.text="weniger als 1 Stunde"
        kostenNBPP.text=objektPP.preis.toString() + "€"
    }

    //**MAP
    override fun onMapReady(p0: GoogleMap?) {
        mMap=p0!!

        mMap.addMarker(
            MarkerOptions().position(objektPP.standort).title("Parkplatz").icon(
                BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN)))
        val minZoom=16.0f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(objektPP.standort, minZoom))

    }

}
