package com.softstarcompanyltd.starwars.components.recyclers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softstarcompanyltd.starwars.R
import com.softstarcompanyltd.starwars.singleton.FeedReaderDbHelper
import com.softstarcompanyltd.starwars.viewModel.FavouriteViewModel


class RecyclerViewAdapterFavouriteScreen (private val list: ArrayList<String>, private val favouriteViewModel: FavouriteViewModel) : RecyclerView.Adapter<RecyclerViewAdapterFavouriteScreen.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descriptionItem : TextView = itemView.findViewById(R.id.description)
        val remove : ImageView = itemView.findViewById(R.id.remove)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_favourite_item, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ClickableViewAccessibility")
    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        view.descriptionItem.text = list[position]
        val array = list[position].split("\n")
        view.remove.setOnClickListener {
            favouriteViewModel.removeItemFromDB(array[1].substring(6))
        }
    }
    override fun getItemCount() = list.size


}