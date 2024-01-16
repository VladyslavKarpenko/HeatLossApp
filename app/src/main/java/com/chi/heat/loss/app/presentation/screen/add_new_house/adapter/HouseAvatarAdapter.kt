package com.chi.heat.loss.app.presentation.screen.add_new_house.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chi.heat.loss.app.databinding.ItemAvatarBinding
import com.chi.heat.loss.app.presentation.model.HouseAvatarPresentation
import com.chi.heat.loss.app.utils.loadCircleImageFromRes

class HouseAvatarAdapter : RecyclerView.Adapter<HouseAvatarAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<HouseAvatarPresentation>() {
        override fun areItemsTheSame(
            oldItem: HouseAvatarPresentation,
            newItem: HouseAvatarPresentation
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: HouseAvatarPresentation,
            newItem: HouseAvatarPresentation
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val avatars = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemAvatarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = avatars.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (avatars.currentList[position].isSelected) {
            holder.selectedBackground()
        } else {
            holder.defaultBackground()
        }
        holder.bind(avatars.currentList[position])
    }

    fun submitList(list: List<HouseAvatarPresentation>) {
        avatars.submitList(list)
    }

    fun getCurrentSelectedAvatar() =
        avatars.currentList.first { it.isSelected }.resourceId

    inner class ViewHolder(private val binding: ItemAvatarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val avatar = avatars.currentList[position]
                        submitList(
                            avatars.currentList.map {
                                if (it == avatar) {
                                    it.copy(isSelected = true)
                                } else {
                                    it.copy(isSelected = false)
                                }
                            }
                        )
                    }
                }
            }
        }

        fun bind(avatar: HouseAvatarPresentation) = with(binding) {
            houseAvatarImageView.loadCircleImageFromRes(avatar.resourceId)
        }

        fun selectedBackground() = with(binding) {
            houseAvatarImageView.background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setStroke(10, Color.BLACK)
            }
        }

        fun defaultBackground() = with(binding) {
            houseAvatarImageView.background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
            }
        }
    }
}