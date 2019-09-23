package de.hshl.isd.parkaid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.parkplatz_fragment.*
import android.text.Editable
import android.text.TextWatcher
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.util.*

class Parkplatz : Fragment(), OnMapReadyCallback {
    var  inputName: String=""
    var  inputppEndzeit : String="--"
    var  inputppPreis: Double=0.00
    var  inputppZeit: Double=0.00
    lateinit var ppObjektx : ppObjekt

    private lateinit var mMap: GoogleMap
    private var mapFragment : SupportMapFragment?=null

    companion object {
        fun newInstance() = Parkplatz()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //*MAP*
        if(mapFragment==null){
            mapFragment = SupportMapFragment.newInstance()
            fragmentManager?.inTransaction {
                replace(
                    R.id.mapParkplatz,
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
        return inflater.inflate(R.layout.parkplatz_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ppname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                inputName = "$s"
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        pppreis.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                inputppPreis = "$s".toDouble()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        ppendzeit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                inputppEndzeit = "$s"
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        ppzeit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                inputppZeit = "$s".toDouble()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        ButtonPP.setOnClickListener {
            ppObjektx = ppObjekt(inputName, inputppPreis, inputppEndzeit, inputppZeit, getTime(), getLocation())

            fragmentManager!!.inTransaction {
                replace(
                    R.id.main_screen,
                    ParkenNBPP.newInstance(ppObjektx)

                )
            }
        }
    }

    //TIME
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
    fun getTime(): String {
        val date = getCurrentDateTime()
        val dateInString = date.toString("HH:mm")
        return dateInString
    }
    fun getLocation(): LatLng { // Location für Maps Einbindung
        val location: LatLng = LatLng(51.682874,7.838794)
        return location
    }


    //**MAP
    override fun onMapReady(p0: GoogleMap?) {
        mMap=p0!!

        mMap.addMarker(
            MarkerOptions().position(getLocation()).title("Position").icon(
                BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_BLUE)))
        val minZoom=16.0f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLocation(), minZoom))
    }


}
