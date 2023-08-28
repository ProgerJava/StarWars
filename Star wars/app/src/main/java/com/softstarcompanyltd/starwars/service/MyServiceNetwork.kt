package com.example.redlanterns.service

import android.app.Service
import android.content.Intent
import android.net.ConnectivityManager
import android.os.IBinder
import android.widget.Toast
import com.softstarcompanyltd.starwars.singleton.MAIN_ACTIVITY
import com.softstarcompanyltd.starwars.singleton.getAllPeopleModel
import com.softstarcompanyltd.starwars.singleton.getAllPlanetModel
import com.softstarcompanyltd.starwars.singleton.getAllSpaceshipModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MyServiceNetwork : Service() {

    private var flag : Boolean = true

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.Main).launch {
            checkConnection()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        flag = false
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }
    private suspend fun checkConnection () {
        while (flag) {
            delay(5000)
            val cs = CONNECTIVITY_SERVICE
            val cm = getSystemService(cs) as ConnectivityManager
            if (cm.activeNetworkInfo?.isConnected == true) {
                if (getAllPeopleModel.listWithPeopleData.size == 0) {
                    getAllPeopleModel.getAllPeople()
                }
                if (getAllPlanetModel.listWithPlanetData.size == 0) {
                    getAllPlanetModel.getAllPlanet()
                }
                if (getAllSpaceshipModel.listWithShipData.size == 0) {
                    getAllSpaceshipModel.getAllSpaceship()
                }
            } else {
                Toast.makeText(MAIN_ACTIVITY, "Check your network connection", Toast.LENGTH_SHORT).show()
            }
        }
    }
}