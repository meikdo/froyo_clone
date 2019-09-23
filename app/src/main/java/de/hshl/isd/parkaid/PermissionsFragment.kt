package de.hshl.isd.parkaid

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


class PermissionsFragment : Fragment() {

    private val TAG = "PermissionDemo"
    var MY_PERMISSIONS_REQUEST_LOCATION = 99

    companion object {

        fun newInstance(): PermissionsFragment {
            return PermissionsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setupPermissions()
        return inflater.inflate(R.layout.permission_fragment, container, false)


    }

    private fun setupPermissions() {


        if (ContextCompat.checkSelfPermission(getContext()!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to access coarse location denied")
        makeRequest()
        }
    }

    private fun makeRequest() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }

    override fun onRequestPermissionsResult (requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user")
                }else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }
}