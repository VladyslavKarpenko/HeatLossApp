package com.chi.heat.loss.app.presentation.screen.add_new_house

import android.content.DialogInterface
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.chi.heat.loss.app.R
import com.chi.heat.loss.app.base.BaseDialogFragment
import com.chi.heat.loss.app.databinding.AddNewHouseDialogBinding
import com.chi.heat.loss.app.presentation.model.HousePresentation
import com.chi.heat.loss.app.presentation.screen.add_new_house.adapter.HouseAvatarAdapter
import com.chi.heat.loss.app.utils.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewHouseMaterialDialog :
    BaseDialogFragment<AddNewHouseDialogBinding, AddNewHouseViewModel>() {

    override val binding by viewBinding<AddNewHouseDialogBinding>()
    override val viewModel by viewModel<AddNewHouseViewModel>()
    override val dialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.add_new_house_dialog_title)
            .setPositiveButton(R.string.ok) { _, _ ->
                viewModel.setEvent(
                    AddNewHouseContract.Event.AddNewHouse(
                        HousePresentation(
                            name = binding.homeNameTextInputEditText.text.toString().trim(),
                            picture = avatarAdapter.getCurrentSelectedAvatar()
                        )
                    )
                )
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }

    private val avatarAdapter by lazy { HouseAvatarAdapter() }

    override fun setUpView() = with(binding) {
        setUpDialogRecyclerView()
        dialog.setOnShowListener {
            val positiveButton =
                dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.isEnabled = false
            homeNameTextInputEditText.doOnTextChanged { text, _, _, _ ->
                positiveButton.isEnabled = text.toString().isNotBlank()
            }
        }
    }

    override fun observeViewModel() = with(viewModel) {
        uiState.observe {
            avatarAdapter.submitList(it.listOfAvatar)
        }
        effect.observe {
            when(it) {
                is AddNewHouseContract.Effect.NavigateToHouseDetailsScreen -> goToHouseDetailsScreen(it.houseId)
            }
        }
    }

    private fun setUpDialogRecyclerView() = with(binding.avatarRecyclerView) {
        layoutManager = object : LinearLayoutManager(
            requireContext(),
            androidx.recyclerview.widget.RecyclerView.HORIZONTAL,
            false
        ) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        adapter = avatarAdapter
        itemAnimator = null
    }

    private fun goToHouseDetailsScreen(houseId: String) =
        AddNewHouseMaterialDialogDirections.actionAddNewHouseMaterialDialogToHouseDetailsFragment(houseId).go()

}