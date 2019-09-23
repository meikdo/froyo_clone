package de.hshl.isd.parkaid

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.parkhaus_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class Parkhaus(mode:Boolean, preis:Double, closetime:String)  : Fragment(), OnMapReadyCallback {
    var inputEbene:String = ""
    var inputNummer:String = ""
    var inputPreis:Double = 0.00
    var inputSZ:String = "--"
    var inputPZ: String = "--"
    private lateinit var objektx: pObjekt

    var modeAnzeige:Boolean
    var modePreis:Double
    var modeTime:String

    private lateinit var mMap: GoogleMap
    private var mapFragment : SupportMapFragment?=null

    companion object {
        fun newInstance(mode:Boolean, preis:Double, closetime:String) = Parkhaus(mode, preis, closetime)
    }

    init{
        modeAnzeige=mode
        modePreis=preis
        modeTime=closetime
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //*MAP*
        if(mapFragment==null){
            mapFragment = SupportMapFragment.newInstance()
            fragmentManager?.inTransaction {
                replace(
                    R.id.mapParkhaus,
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
        return inflater.inflate(R.layout.parkhaus_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        EbenePH.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                inputEbene = "$s"
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        NummerPH.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                inputNummer = "$s"
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        PreisPH.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                inputPreis = "$s".toDouble()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        SchließzeitPH.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
              //  inputSZ = ""
            }
            override fun afterTextChanged(s: Editable?) {
                inputSZ = "$s"
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        if(modeAnzeige==true){
            PreisPH.setText(modePreis.toString())
            SchließzeitPH.setText(modeTime)
        }


        ButtonPH.setOnClickListener {
            objektx = pObjekt(inputEbene, inputNummer, inputPreis, inputSZ, getTime(), getLocation())

            fragmentManager!!.inTransaction {
                replace(
                    R.id.main_screen,
                    ParkenNB.newInstance(objektx)
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

    //LOCATION
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



