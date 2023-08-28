package com.softstarcompanyltd.starwars.components.recyclers

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.softstarcompanyltd.starwars.R
import com.softstarcompanyltd.starwars.databinding.FragmentSplashBinding
import com.softstarcompanyltd.starwars.repository.DB.FeedReaderDbHelper
import com.softstarcompanyltd.starwars.singleton.FeedReaderDbHelper
import com.softstarcompanyltd.starwars.singleton.MAIN_ACTIVITY
import com.softstarcompanyltd.starwars.singleton.getCurrentFilmModel
import com.softstarcompanyltd.starwars.singleton.getFeedReaderInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Arrays


class RecyclerViewAdapterSplashScreen (private val list: ArrayList<String>, private val binding: FragmentSplashBinding) : RecyclerView.Adapter<RecyclerViewAdapterSplashScreen.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val save: ImageView = itemView.findViewById(R.id.save)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_splash_item, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ClickableViewAccessibility")
    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        view.save.setImageDrawable(MAIN_ACTIVITY.getDrawable(R.drawable.ic_save_not_press))
        val result = list[position]
        //////////////////////Присваиваем View весь текст из переменной до информации о фильме
        var descriptionEntity = list[position].substring(0, list[position].indexOf("Films: "))
        view.name.text = descriptionEntity
        /////////////////////////Массив для парсинга
        val array = result.split("\n")
        //////////////////////Если есть в базе данных, меняем цвет иконки
        val index = array[1].substring(6, array[1].length)
        CoroutineScope(Dispatchers.Main).launch {
            if (FeedReaderDbHelper.exists(index, FeedReaderDbHelper)) {
                view.save.setImageDrawable(MAIN_ACTIVITY.getDrawable(R.drawable.ic_save_press))
            }
        }
        ////////////////////Информация о фильме
        val films = result.substring(list[position].indexOf("[") + 1, list[position].length - 2)
        ///////////////////////Обработка добавления в избранное
        view.save.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                binding.progressBar.visibility = View.VISIBLE
                getFilmWithEntity(descriptionEntity, films, view)
            }
        }
    }
    override fun getItemCount() = list.size

    private suspend fun getFilmWithEntity(descriptionEntity: String, films: String, view : ViewHolder) {
        val arrayWithLinkOnFilm = films.split(",")
        Log.println(Log.INFO, "INFO_PROGRESS_SIZE", "${listOf(arrayWithLinkOnFilm)}")
        var result = ""
        result += descriptionEntity
        for (i in arrayWithLinkOnFilm.indices) {
            CoroutineScope(Dispatchers.Main).launch {
                val number = arrayWithLinkOnFilm[i].substring(arrayWithLinkOnFilm[i].indexOf("films/"), arrayWithLinkOnFilm[i].length)
                getCurrentFilmModel.getFilmData(number)
            }
        }
        while (getCurrentFilmModel.dataFilmList.size != arrayWithLinkOnFilm.size) {
            delay(500)
            Log.println(Log.INFO, "INFO_PROGRESS_SIZE", "${getCurrentFilmModel.dataFilmList.size} + ${arrayWithLinkOnFilm.size}")
        }
        for (i in 0 until getCurrentFilmModel.dataFilmList.size) {
            result += getCurrentFilmModel.dataFilmList[i]
        }
        ////////////////////обновляем список фильмов
        getCurrentFilmModel.dataFilmList.clear()
        val array = result.split("\n")
        saveFavourite(result, array[1].substring(6), view)
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun saveFavourite(result: String, id: String, view : ViewHolder) {
        if (FeedReaderDbHelper.exists(id, FeedReaderDbHelper)) {
            Toast.makeText(MAIN_ACTIVITY, "Уже в избранном", Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility = View.INVISIBLE
        }else{
            FeedReaderDbHelper.writeToDbEntityData(id, result, FeedReaderDbHelper)
            Toast.makeText(MAIN_ACTIVITY, "Добавлено в избранное", Toast.LENGTH_SHORT).show()
            view.save.setImageDrawable(MAIN_ACTIVITY.getDrawable(R.drawable.ic_save_press))
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}