package com.softstarcompanyltd.starwars.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softstarcompanyltd.starwars.repository.model.GetAllPeopleModel
import com.softstarcompanyltd.starwars.repository.model.GetAllPlanetModel
import com.softstarcompanyltd.starwars.repository.model.GetAllSpaceshipModel
import com.softstarcompanyltd.starwars.singleton.FeedReaderDbHelper
import com.softstarcompanyltd.starwars.singleton.MAIN_ACTIVITY
import com.softstarcompanyltd.starwars.singleton.getAllPeopleModel
import com.softstarcompanyltd.starwars.singleton.getAllPlanetModel
import com.softstarcompanyltd.starwars.singleton.getAllSpaceshipModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavouriteViewModel : ViewModel() {

    val arrayWithDataEntity : MutableLiveData <ArrayList<String>> = MutableLiveData()

    fun getDataFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = FeedReaderDbHelper.getAllFavouriteEntity(FeedReaderDbHelper)
            arrayWithDataEntity.value = result
        }
    }
    fun removeItemFromDB(index: String) {
        CoroutineScope(Dispatchers.Main).launch {
            FeedReaderDbHelper.deleteIntoToDb(index, FeedReaderDbHelper)
            getDataFromDatabase()
            Toast.makeText(MAIN_ACTIVITY, "Элемент удален", Toast.LENGTH_SHORT).show()
        }
    }
}