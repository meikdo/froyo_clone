package de.hshl.isd.parkaid

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import kotlinx.android.synthetic.main.main_screen_layout.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.parkhaus_fragment.*


class MainScreenFragment(mListe:ArrayList<Parkhaus_m>, context: Context) : Fragment(), OnMapReadyCallback {

    private var parkhaeuserList: ArrayList<Parkhaus_m>
    private var mContext: Context

    lateinit var pObjekt: pObjekt
    //MAP
    private lateinit var mMap: GoogleMap
    private var mapFragment : SupportMapFragment?=null

    //HSHL-Koordinaten
    private var latitude = 51.682874
    private var longitude = 7.838794

    private lateinit var mPosition: Location
    private var mapModel: MapModel = MapModel()

    companion object {
        fun newInstance(list: ArrayList<Parkhaus_m>, context: Context) = MainScreenFragment(list, context)
    }

    init {
        parkhaeuserList = mListe
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //*MAP*
        if(mapFragment==null){
            mapFragment = SupportMapFragment.newInstance()
            fragmentManager?.inTransaction {
                replace(
                    R.id.mapview,
                    mapFragment!!
                )
            }
        }
        mapFragment?.getMapAsync(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPosition = mapModel.getLocation()

        //Switch Entfernung-Preis
        switchMain.setOnClickListener {
            fillListView()
        }

        //**LISTE ON-CLICK**
        val listView = parkhaus_liste_Main as ListView
        listView.setOnItemClickListener { _, _, position, _ ->
            val parkhaus = listView.getItemAtPosition(position) as Parkhaus_m
            fragmentManager!!.inTransaction {
                replace(
                    R.id.main_screen,
                    ListClick.newInstance(parkhaus)
                )
            }
        }
        fillListView()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.main_screen_layout, container, false)
    }


    //**LIST**
    fun sortList() {
        val sortedList: List<Parkhaus_m>
        if (switchMain.isChecked) {
            sortedList = parkhaeuserList.sortedWith(compareBy({ it.preis }))
        } else {
            sortedList = parkhaeuserList.sortedWith(compareBy({ it.distance }))
        }
        parkhaeuserList = ArrayList<Parkhaus_m>()
        parkhaeuserList.addAll(sortedList)
    }

    fun fillListView() {
        sortList()
        val listAdapter = ListAdapterCustom(mContext, parkhaeuserList)
        parkhaus_liste_Main.adapter = listAdapter
    }
    /////////////////



    //**MAP
    override fun onMapReady(p0: GoogleMap?) {
        mMap=p0!!

        //FÜGE MARKER HINZU
        for (item in parkhaeuserList) {
            val geo = LatLng(item.koordinate.latitude, item.koordinate.longitude)
            val markerOptions: MarkerOptions = MarkerOptions().position(geo).title(item.name)
            mMap.addMarker(markerOptions)
        }

        //SET CENTER~HSHL
        val hshl = LatLng(51.682874, 7.838794)
        mMap.addMarker(MarkerOptions().position(hshl).title("HSHL").icon(
            BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_AZURE)))
        val minZoom=12.0f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hshl, minZoom))

    }
}








