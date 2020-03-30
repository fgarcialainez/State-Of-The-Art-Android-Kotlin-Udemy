package com.fgarcialainez.androidkotlincourse.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fgarcialainez.androidkotlincourse.R
import com.fgarcialainez.androidkotlincourse.model.Animal
import com.fgarcialainez.androidkotlincourse.utils.getProgressDrawable
import com.fgarcialainez.androidkotlincourse.utils.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>): RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal, parent, false)

        return AnimalViewHolder(view)
    }

    override fun getItemCount(): Int = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animalList[position]

        holder.view.animalName.text = animal.name
        holder.view.animalImage.loadImage(animal.imageUrl, getProgressDrawable(holder.view.context))
    }

    class AnimalViewHolder(var view: View): RecyclerView.ViewHolder(view)
}