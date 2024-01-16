package com.chi.heat.loss.app.presentation.screen.house_details

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.chi.heat.loss.app.R
import com.chi.heat.loss.app.base.BaseFragment
import com.chi.heat.loss.app.databinding.FragmentHouseDetailsBinding
import com.chi.heat.loss.app.presentation.screen.house_details.adapter.RoomAdapter
import com.chi.heat.loss.app.utils.RoomType
import com.chi.heat.loss.app.utils.loadCircleImageFromRes
import com.chi.heat.loss.app.utils.viewBinding
import com.faskn.lib.Slice
import com.faskn.lib.buildChart
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.ceil

class HouseDetailsFragment : BaseFragment<FragmentHouseDetailsBinding, HouseDetailsViewModel>() {
    override val binding by viewBinding<FragmentHouseDetailsBinding>()
    override val viewModel by viewModel<HouseDetailsViewModel>()
    private val args by navArgs<HouseDetailsFragmentArgs>()
    private val roomAdapter by lazy {
        RoomAdapter {
            viewModel.setEvent(
                HouseDetailsContract.Event.OnDeleteRoomButtonClick(
                    it
                )
            )
        }
    }

    override fun setUpView() {
        with(binding) {
            setUpRecyclerView()
            backButton.setOnClickListener {
                pop()
            }
            addNewRoomMaterialButton.setOnClickListener {
                showMenu(it, R.menu.rooms_popup_menu)
            }
            ambientTemperatureSlider.addOnChangeListener { _, value, fromUser ->
                if (fromUser) {
                    ambientTemperatureValueMaterialTextView.text =
                        if (value > 0) "+${value.toInt()}" else "${value.toInt()}"
                    viewModel.setEvent(HouseDetailsContract.Event.OnAmbientTemperatureChange(value.toDouble()))
                }
            }
            internalTemperatureSlider.addOnChangeListener { _, value, fromUser ->
                if (fromUser) {
                    internalTemperatureValueMaterialTextView.text =
                        if (value > 0) "+${value.toInt()}" else "${value.toInt()}"
                    viewModel.setEvent(HouseDetailsContract.Event.OnInternalTemperatureChange(value.toDouble()))
                }
            }

            loadRecommendationsMaterialButton.setOnClickListener {
                if (wattsValueMaterialTextView.text != "0.0") {
                    val btu = ceil(btuValueMaterialTextView.text.toString().toDouble()).toInt()
                    val request =
                        "Give me list of house heating system with $btu BTU power with such information like name of brand, name of model, price, description"
                    viewModel.setEvent(
                        HouseDetailsContract.Event.GetRecommendationsFromChatGPT(
                            request
                        )
                    )
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Your power is 0.0 Watt! Add at least 1 room and enter valid temperatures",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            viewModel.setEvent(HouseDetailsContract.Event.GetInfoAboutHouse(args.houseId))
        }

    }

    override fun observeViewModel() = with(viewModel) {
        uiState.observe { state ->
            with(binding) {
                state.house?.let {
                    avatarHouseImageView.loadCircleImageFromRes(it.picture)
                    nameOfHouseMaterialTextView.text = it.name
                }
                emptyScreenMaterialTextView.isVisible = state.listOfRooms.isEmpty()
                roomAdapter.submitList(state.listOfRooms)
                internalTemperatureSlider.value = state.internalTemperature.toFloat()
                internalTemperatureValueMaterialTextView.text = state.internalTemperature.toString()
                ambientTemperatureSlider.value = state.ambientTemperature.toFloat()
                ambientTemperatureValueMaterialTextView.text = state.ambientTemperature.toString()
                val houseHeatLoss = state.listOfRooms.sumOf { it.heatLoss }
                val deltaTemp =
                    (state.internalTemperature + 273) - (state.ambientTemperature + 273)
                val watts = houseHeatLoss * deltaTemp
                wattsValueMaterialTextView.text =
                    if (watts > 0.0) String.format("%.1f", watts) else "0.0"
                btuValueMaterialTextView.text =
                    if (watts > 0.0) String.format("%.1f", watts * 3.4) else "0.0"
                if (state.listOfRooms.isNotEmpty()) {

                    val pieChartDSL = buildChart {
                        slices {
                            val arrayList = arrayListOf<Slice>()
                            val colors = generateUniqueColors(state.listOfRooms.size)
                            repeat(colors.size) {
                                arrayList.add(
                                    Slice(
                                        state.listOfRooms[it].heatLoss.toFloat(),
                                        colors[it],
                                        state.listOfRooms[it].name
                                    )
                                )
                            }
                            arrayList
                        }
                        sliceWidth { 100f }
                        sliceStartPoint { 0f }
                        clickListener { _, _ -> }
                    }
                    chart.setPieChart(pieChartDSL)
                    chart.showLegend(legendLayout)
                }

                loadRecommendationsMaterialButton.isEnabled = !state.isRecommendationsLoading
                loadRecommendationsCircularProgressIndicator.isVisible =
                    state.isRecommendationsLoading
                if (!state.recommendations.contains("ERROR:") && state.recommendations.isNotBlank()) {
                    recommendationsMaterialTextView.text =
                        "1.${state.recommendations.substringAfter("1.")}"
                } else {
                    recommendationsMaterialTextView.text = state.recommendations
                }
                Log.d("GG", "Recommendations: ${state.recommendations}")
            }
        }
        effect.observe {
            when (it) {
                is HouseDetailsContract.Effect.OpenDeleteDialog -> openDeleteRoomMaterialDialog(it.id)
                is HouseDetailsContract.Effect.NavigateToAddNewRoomScreen -> goToAddNewRoomScreen(it.roomType)
            }
        }
    }

    private fun setUpRecyclerView() = with(binding.roomsRecyclerView) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = roomAdapter
        itemAnimator = null
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.living_room -> {
                    viewModel.setEvent(HouseDetailsContract.Event.OnRoomClick(RoomType.LIVING_ROOM))
                    true
                }

                R.id.family_room -> {
                    viewModel.setEvent(HouseDetailsContract.Event.OnRoomClick(RoomType.FAMILY_ROOM))
                    true
                }

                R.id.dining_room -> {
                    viewModel.setEvent(HouseDetailsContract.Event.OnRoomClick(RoomType.DINING_ROOM))
                    true
                }

                R.id.kitchen -> {
                    viewModel.setEvent(HouseDetailsContract.Event.OnRoomClick(RoomType.KITCHEN))
                    true
                }

                R.id.game_room -> {
                    viewModel.setEvent(HouseDetailsContract.Event.OnRoomClick(RoomType.GAME_ROOM))
                    true
                }

                R.id.bathroom -> {
                    viewModel.setEvent(HouseDetailsContract.Event.OnRoomClick(RoomType.BATHROOM))
                    true
                }

                R.id.bedroom -> {
                    viewModel.setEvent(HouseDetailsContract.Event.OnRoomClick(RoomType.BEDROOM))
                    true
                }

                R.id.laundry_room -> {
                    viewModel.setEvent(HouseDetailsContract.Event.OnRoomClick(RoomType.LAUNDRY_ROOM))
                    true
                }

                R.id.sun_room -> {
                    viewModel.setEvent(HouseDetailsContract.Event.OnRoomClick(RoomType.SUN_ROOM))
                    true
                }

                R.id.study_room -> {
                    viewModel.setEvent(HouseDetailsContract.Event.OnRoomClick(RoomType.STUDY_ROOM))
                    true
                }

                R.id.utility_room -> {
                    viewModel.setEvent(HouseDetailsContract.Event.OnRoomClick(RoomType.UTILITY_ROOM))
                    true
                }

                else -> false
            }
        }
        popup.show()
    }

    private fun openDeleteRoomMaterialDialog(roomId: String) =
        MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.delete_room_dialog_title)
            .setMessage(R.string.delete_room_dialog_message)
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.setEvent(HouseDetailsContract.Event.DeleteRoom(roomId))
            }.setNegativeButton(R.string.no) { dialog, _ ->
                dialog.cancel()
            }.show()

    private fun goToAddNewRoomScreen(room: RoomType) =
        HouseDetailsFragmentDirections.actionHouseDetailsFragmentToAddNewRoomFragment(
            room,
            args.houseId
        ).go()

    private fun generateUniqueColors(count: Int): List<Int> {
        val uniqueColors = HashSet<Int>()

        while (uniqueColors.size < count) {
            val colorId = resources.getIdentifier(
                "color_${uniqueColors.size + 1}",
                "color",
                requireContext().packageName
            )

            if (colorId != 0) {
                uniqueColors.add(colorId)
            }
        }
        return uniqueColors.toList()
    }
}