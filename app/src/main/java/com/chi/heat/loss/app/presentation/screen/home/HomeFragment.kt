package com.chi.heat.loss.app.presentation.screen.home

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.chi.heat.loss.app.R
import com.chi.heat.loss.app.base.BaseFragment
import com.chi.heat.loss.app.databinding.FragmentHomeBinding
import com.chi.heat.loss.app.presentation.screen.home.adapter.HouseAdapter
import com.chi.heat.loss.app.utils.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val binding by viewBinding<FragmentHomeBinding>()
    override val viewModel by viewModel<HomeViewModel>()

    private val houseAdapter by lazy {
        HouseAdapter(
            onItemClicked = {
                viewModel.setEvent(HomeContract.Event.OnItemClick(it))
            },
            onDeleteClicked = {
                viewModel.setEvent(HomeContract.Event.OnDeleteHouseButtonClick(it))
            }
        )
    }

    override fun setUpView() = with(binding) {
        setUpRecyclerView()
        addNewHouseExtendedFloatingActionButton.setOnClickListener {
            viewModel.setEvent(HomeContract.Event.OnAddNewHouseButtonClick)
        }
    }

    override fun observeViewModel() = with(viewModel) {
        uiState.observe {
            houseAdapter.submitList(it.listOfHouses)
            binding.emptyScreenMaterialTextView.isVisible = it.listOfHouses.isEmpty()
        }
        effect.observe {
            when (it) {
                is HomeContract.Effect.OpenDeleteDialog -> openDeleteHouseMaterialDialog(it.id)
                is HomeContract.Effect.NavigateToAddNewHouseDialog -> goToAddNewHouseDialog()
                is HomeContract.Effect.NavigateToHouseDetailsScreen -> goToHouseDetailsScreen(it.houseId)
            }
        }
    }

    private fun setUpRecyclerView() = with(binding.houseRecyclerView) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = houseAdapter
        itemAnimator = null
    }

    private fun openDeleteHouseMaterialDialog(houseId: String) =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_house_dialog_title)
            .setMessage(R.string.delete_house_dialog_message)
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.setEvent(HomeContract.Event.DeleteHouse(houseId))
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.cancel()
            }
            .show()

    private fun goToHouseDetailsScreen(houseId: String) =
        HomeFragmentDirections.actionHomeFragmentToHouseDetailsFragment(houseId).go()

    private fun goToAddNewHouseDialog() =
        HomeFragmentDirections.actionHomeFragmentToAddNewHouseMaterialDialog().go()
}