package de.hshl.isd.parkaid


import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.PreferenceManager
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng




class MainActivity : AppCompatActivity(){
    //DRAWER
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    //filteredList
    private var parkhaeuser_list_sorted: ArrayList<Parkhaus_m> = ArrayList<Parkhaus_m>()

    private lateinit var mMap: GoogleMap
    private var mapFragment: SupportMapFragment? = null

    private var parkhaeuser_list: ArrayList<Parkhaus_m> = ArrayList<Parkhaus_m>()
    private var bitmapArray: ArrayList<Bitmap> = ArrayList<Bitmap>()
    private lateinit var namenArray: ArrayList<String>
    private lateinit var adressenArray: ArrayList<Adresse>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //**TOOLBAR-DRAWER**
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        //*LISTE*
        initResources()

        //STARTER-FRAGMENT
        supportFragmentManager.inTransaction {
            replace(
                R.id.main_screen,
                MainScreenFragment.newInstance(parkhaeuser_list, this@MainActivity.applicationContext)
            )
        }

    }



    //NAVIGATION-DRAWER
    fun navigate(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_item_zero -> {
                parkhaeuser_list_sorted.clear()
// 1. FR+HA+EL
                val prefs = PreferenceManager.getDefaultSharedPreferences(this)
                val elementlist = parkhaeuser_list.listIterator()
                if (prefs.getBoolean("womens", false)) {
                    if (prefs.getBoolean("handicapped", false)) {
                        if (prefs.getBoolean("elektro", false)) {
                            elementlist.forEach {
                                if (it.womens) {
                                    if (it.handicap) {
                                        if (it.elektro) {
                                            parkhaeuser_list_sorted.add(it)
                                        }
                                    }
                                }
                            }
                            sortednav(parkhaeuser_list_sorted)
                        }
// 2. FR+HA
                        else {
                            elementlist.forEach {
                                if (it.handicap) {
                                    if (it.womens) {
                                        parkhaeuser_list_sorted.add(it)
                                    }
                                }
                            }
                            sortednav(parkhaeuser_list_sorted)
                        }
                    }
// 3. FR+EL
                    else if (prefs.getBoolean("elektro", false)) {
                        elementlist.forEach {
                            if (it.womens) {
                                if (it.elektro) {
                                    parkhaeuser_list_sorted.add(it)
                                }
                            }
                        }
                        sortednav(parkhaeuser_list_sorted)
                    }
// 4. FR
                    else {
                        elementlist.forEach {
                            if (it.womens) {
                                parkhaeuser_list_sorted.add(it)
                            }
                        }
                        sortednav(parkhaeuser_list_sorted)
                    }
                }
// 5. HA+EL
                else if (prefs.getBoolean("handicapped", false)) {
                    if (prefs.getBoolean("elektro", false)) {
                        elementlist.forEach {
                            if (it.handicap) {
                                if (it.elektro) {
                                    parkhaeuser_list_sorted.add(it)
                                }
                            }
                        }
                        sortednav(parkhaeuser_list_sorted)
                    }
// 6. HA
                    else {
                        elementlist.forEach {
                            if (it.handicap) {
                                parkhaeuser_list_sorted.add(it)
                            }
                        }
                        sortednav(parkhaeuser_list_sorted)
                    }
                }
// 7. EL
                else if (prefs.getBoolean("elektro", false)) {
                    elementlist.forEach {
                        if (it.elektro) {
                            parkhaeuser_list_sorted.add(it)
                        }
                    }
                    sortednav(parkhaeuser_list_sorted)
                } else {
                    supportFragmentManager.inTransaction {
                        replace(
                            R.id.main_screen,
                            MainScreenFragment.newInstance(parkhaeuser_list, this@MainActivity.applicationContext)
                        )
                        supportActionBar?.hide()
                    }
                }
            }
            R.id.nav_item_one -> {
                supportFragmentManager.inTransaction {
                    replace(R.id.main_screen, Parkhaus.newInstance(false, 0.0, ""))
                }
                supportActionBar?.hide()
            }
            R.id.nav_item_two -> {
                supportFragmentManager.inTransaction {
                    replace(R.id.main_screen, Parkplatz.newInstance())
                }
                supportActionBar?.hide()
            }
            R.id.nav_item_three -> {
                supportFragmentManager.inTransaction {
                    replace(R.id.main_screen, SettingsFragment.newInstance())
                }
                supportActionBar?.hide()
            }

            else -> {
            }
        }
        drawer.closeDrawer(Gravity.LEFT)
    }

    fun sortednav(park_list_sorted: ArrayList<Parkhaus_m>) {
        if (park_list_sorted.isEmpty()) {
            Toast.makeText(this, "Keine Parkhäuser, bitte andere Filter wählen.", Toast.LENGTH_LONG).show()
        } else {
            supportFragmentManager.inTransaction {
                replace(
                    R.id.main_screen,
                    MainScreenFragment.newInstance(park_list_sorted, this@MainActivity.applicationContext)
                )
                supportActionBar?.hide()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_main_drawer, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }


    //NAMES
    fun setNamen() {
        parkhaeuser_list[0].name = "Parkhaus Lago"
        parkhaeuser_list[1].name = "Am Dom"
        parkhaeuser_list[2].name = "Parken Centre"
        parkhaeuser_list[3].name = "Parkplatz P3"
        parkhaeuser_list[4].name = "Bahnhof"
        parkhaeuser_list[5].name = "Parkhaus Hafen"
        parkhaeuser_list[6].name = "SP-Safe Parken"
        parkhaeuser_list[7].name = "Theater P&R"
        parkhaeuser_list[8].name = "Stadt-Parkhaus"
        parkhaeuser_list[9].name = "Park and Ride"
        parkhaeuser_list[10].name = "Parkhaus A2"
    }

    //ADRESSEN

    fun setAdressen(){
        parkhaeuser_list[0].adresse=Adresse(LatLng(51.677948,7.818021), "Südring 16", "59065 Hamm")
        parkhaeuser_list[1].adresse=Adresse(LatLng(51.683812,7.831793), "Doktor-Hart-Weg 14" ,"59063 Hamm")
        parkhaeuser_list[2].adresse=Adresse(LatLng(51.710759,7.999040), "Uentruper Weg 1", "59510 Lippe")
        parkhaeuser_list[3].adresse=Adresse(LatLng(51.685283,7.853426), "Knappenstraße 54", "59071 Hamm")
        parkhaeuser_list[4].adresse=Adresse(LatLng(51.704648,7.821518), "Fischer-Weg 12", "59073 Hamm")
        parkhaeuser_list[5].adresse=Adresse(LatLng(51.688069,7.779638), "Bockumer Weg 255", "59065 Hamm")
        parkhaeuser_list[6].adresse=Adresse(LatLng(51.693583,7.770008), "Wernerstraße 35", "59075 Hamm")
        parkhaeuser_list[7].adresse=Adresse(LatLng(51.178922,6.761221), "Am Reckberg 5", "41468 Neuss")
        parkhaeuser_list[8].adresse=Adresse(LatLng(51.697027,7.760395), "Oswaldstraße 18", "59075 Hamm")
        parkhaeuser_list[9].adresse=Adresse(LatLng(51.664883,7.839311), "Karl-Arnold-Straße 6", "59063 Hamm")
        parkhaeuser_list[10].adresse=Adresse(LatLng(51.686370,7.892072), "Schäferstraße 1A", "59071 Hamm")

        var index=0
        for(Parkhaus_m in parkhaeuser_list){
            parkhaeuser_list[index].set()
            index++
        }
    }

    //BITMAPS
    fun generateBitmaps() {
        var index = 0
        for (Int in 0..10) {
            val name: String = "p" + index.toString() + ".jpg"
            val variable = assets.open(name)
            bitmapArray.add(BitmapFactory.decodeStream(variable))
            index++
        }
    }

    fun setBitmaps() {
        var index = 0
        for (Int in 0..10) {
            parkhaeuser_list[index].bitmap = bitmapArray[index]
            index++
        }
    }


    //**PARKHAUS-LISTE**
    private fun generateParkhaeuser() {
        var auswahl: String = "up"
        for (i in 0..10) {
            val handicap: Boolean = i % 2 != 0
            val womens: Boolean = i >= 8
            val elektro: Boolean = i <= 4

            if (i % 2 == 0) {
                auswahl = "down"
            }
            parkhaeuser_list.add(
                Parkhaus_m(
                    i,
                    auswahl,
                    handicap,
                    womens,
                    elektro
                )
            )
            auswahl = "up"
        }
    }


    //GENERATE-RESOURCES
    fun initResources(){
        generateParkhaeuser()
        generateBitmaps()
        setNamen()
        setAdressen()
        setBitmaps()
    }


}






