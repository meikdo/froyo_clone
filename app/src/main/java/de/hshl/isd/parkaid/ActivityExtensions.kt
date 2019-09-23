package de.hshl.isd.parkaid

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


inline fun FragmentManager.inTransaction(func:
FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}