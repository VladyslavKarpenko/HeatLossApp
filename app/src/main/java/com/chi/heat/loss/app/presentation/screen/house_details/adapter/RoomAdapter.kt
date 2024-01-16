package com.chi.heat.loss.app.presentation.screen.house_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chi.heat.loss.app.databinding.ItemRoomBinding
import com.chi.heat.loss.app.presentation.model.RoomPresentation
import com.chi.heat.loss.app.utils.RoomType
import com.chi.heat.loss.app.utils.loadImageFromRes
import java.lang.IllegalStateException

class RoomAdapter(val onDeleteClicked: (String) -> Unit): RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<RoomPresentation>() {
        override fun areItemsTheSame(oldItem: RoomPresentation, newItem: RoomPresentation): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RoomPresentation, newItem: RoomPresentation): Boolean {
            return oldItem == newItem
        }
    }
    private val rooms = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = rooms.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rooms.currentList[position])
    }

    fun submitList(list: List<RoomPresentation>) {
        rooms.submitList(list)
    }

    inner class ViewHolder(private val binding: ItemRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                deleteMaterialButton.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val room = rooms.currentList[position]
                        onDeleteClicked.invoke(room.id)
                    }
                }
            }
        }

        fun bind(room: RoomPresentation) {
            with(binding) {
                val roomType = getRoomType(room.name)
                roomTypeMaterialTextView.text = binding.root.context.getString(roomType.type)
                roomAvatarImageView.loadImageFromRes(roomType.icon)
            }
        }

        private fun getRoomType(name: String): RoomType {
            val context = binding.root.context
            return when(name) {
                context.getString(RoomType.LIVING_ROOM.type) -> RoomType.LIVING_ROOM
                context.getString(RoomType.FAMILY_ROOM.type) -> RoomType.FAMILY_ROOM
                context.getString(RoomType.DINING_ROOM.type) -> RoomType.DINING_ROOM
                context.getString(RoomType.KITCHEN.type) -> RoomType.KITCHEN
                context.getString(RoomType.GAME_ROOM.type) -> RoomType.GAME_ROOM
                context.getString(RoomType.BATHROOM.type) -> RoomType.BATHROOM
                context.getString(RoomType.BEDROOM.type) -> RoomType.BEDROOM
                context.getString(RoomType.LAUNDRY_ROOM.type) -> RoomType.LAUNDRY_ROOM
                context.getString(RoomType.SUN_ROOM.type) -> RoomType.SUN_ROOM
                context.getString(RoomType.STUDY_ROOM.type) -> RoomType.STUDY_ROOM
                context.getString(RoomType.UTILITY_ROOM.type) -> RoomType.UTILITY_ROOM
                else -> throw IllegalStateException("Don`t exist this room type")
            }
        }
    }
}