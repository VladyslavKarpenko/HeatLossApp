package com.chi.heat.loss.app.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chi.heat.loss.app.databinding.ItemHouseBinding
import com.chi.heat.loss.app.presentation.model.HousePresentation
import com.chi.heat.loss.app.utils.loadImageFromRes

class HouseAdapter(val onItemClicked: (String) -> Unit, val onDeleteClicked: (String) -> Unit): RecyclerView.Adapter<HouseAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<HousePresentation>() {
        override fun areItemsTheSame(oldItem: HousePresentation, newItem: HousePresentation): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: HousePresentation, newItem: HousePresentation): Boolean {
            return oldItem == newItem
        }
    }
    private val houses = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHouseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = houses.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(houses.currentList[position])
    }

    fun submitList(list: List<HousePresentation>) {
        houses.submitList(list)
    }

    inner class ViewHolder(private val binding: ItemHouseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                    val house = houses.currentList[position]
                    onItemClicked.invoke(house.id)
                    }
                }
                deleteMaterialButton.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val house = houses.currentList[position]
                        onDeleteClicked.invoke(house.id)
                    }
                }
            }
        }

        fun bind(house: HousePresentation) {
            with(binding) {
                houseAvatarImageView.loadImageFromRes(house.picture)
                nameOfHouseMaterialTextView.text = house.name
            }
        }
    }
}