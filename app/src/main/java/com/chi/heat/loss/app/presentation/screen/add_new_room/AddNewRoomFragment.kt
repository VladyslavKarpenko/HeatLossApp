package com.chi.heat.loss.app.presentation.screen.add_new_room

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.navArgs
import com.chi.heat.loss.app.R
import com.chi.heat.loss.app.base.BaseFragment
import com.chi.heat.loss.app.databinding.FragmentAddNewRoomBinding
import com.chi.heat.loss.app.presentation.model.RoomComponent
import com.chi.heat.loss.app.utils.Constants
import com.chi.heat.loss.app.utils.loadCircleImageFromRes
import com.chi.heat.loss.app.utils.viewBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewRoomFragment : BaseFragment<FragmentAddNewRoomBinding, AddNewRoomViewModel>() {
    override val binding by viewBinding<FragmentAddNewRoomBinding>()
    override val viewModel by viewModel<AddNewRoomViewModel>()
    private val args by navArgs<AddNewRoomFragmentArgs>()

    private val items by lazy { arrayOf(INTERNAL, EXTERNAL) }
    private val itemsAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            R.layout.item_exposed_dropdown_menu,
            items
        )
    }

    override fun setUpView(): Unit = with(binding) {
        avatarRoomImageView.loadCircleImageFromRes(args.roomType.icon)
        nameOfRoomMaterialTextView.text = getString(args.roomType.type)
        addButton.isEnabled = false
        cancelButton.setOnClickListener { pop() }
        addButton.setOnClickListener {
            val ceilingType =
                (ceilingMenu.editText as? MaterialAutoCompleteTextView)?.text.toString()
            val floorType = (floorMenu.editText as? MaterialAutoCompleteTextView)?.text.toString()
            val wall1Type = (wall1Menu.editText as? MaterialAutoCompleteTextView)?.text.toString()
            val wall2Type = (wall2Menu.editText as? MaterialAutoCompleteTextView)?.text.toString()
            val wall3Type = (wall3Menu.editText as? MaterialAutoCompleteTextView)?.text.toString()
            val wall4Type = (wall4Menu.editText as? MaterialAutoCompleteTextView)?.text.toString()
            val ceilingArea = if(ceilingType == EXTERNAL && ceilingAreaTextInputLayout.editText?.text.toString().isNotBlank()) ceilingAreaTextInputLayout.editText?.text.toString().toDouble() else 0.0
            val floorArea = if (floorType == EXTERNAL && floorAreaTextInputLayout.editText?.text.toString().isNotBlank()) floorAreaTextInputLayout.editText?.text.toString().toDouble() else 0.0
            val wall1Area = if (wall1Type == EXTERNAL && wall1AreaTextInputLayout.editText?.text.toString().isNotBlank()) wall1AreaTextInputLayout.editText?.text.toString().toDouble() else 0.0
            val wall2Area = if (wall2Type == EXTERNAL && wall2AreaTextInputLayout.editText?.text.toString().isNotBlank()) wall2AreaTextInputLayout.editText?.text.toString().toDouble() else 0.0
            val wall3Area = if (wall3Type == EXTERNAL && wall3AreaTextInputLayout.editText?.text.toString().isNotBlank()) wall3AreaTextInputLayout.editText?.text.toString().toDouble() else 0.0
            val wall4Area = if (wall4Type == EXTERNAL && wall4AreaTextInputLayout.editText?.text.toString().isNotBlank()) wall4AreaTextInputLayout.editText?.text.toString().toDouble() else 0.0
            if (ceilingType == EXTERNAL && ceilingArea == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "Ceiling area can`t be 0.0",
                    Toast.LENGTH_LONG
                ).show()
                ceilingAreaTextInputLayout.error = "Input ceiling area!"
            } else if (floorType == EXTERNAL && floorArea == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "Floor area can`t be 0.0",
                    Toast.LENGTH_LONG
                ).show()
                floorAreaTextInputLayout.error = "Input floor area!"
            } else if (wall1Type == EXTERNAL && wall1Area == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "Wall1 area can`t be 0.0",
                    Toast.LENGTH_LONG
                ).show()
                wall1AreaTextInputLayout.error = "Input wall1 area!"
            } else if (wall2Type == EXTERNAL && wall2Area == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "Wall2 area can`t be 0.0",
                    Toast.LENGTH_LONG
                ).show()
                wall2AreaTextInputLayout.error = "Input wall2 area!"
            } else if (wall3Type == EXTERNAL && wall3Area == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "Wall3 area can`t be 0.0",
                    Toast.LENGTH_LONG
                ).show()
                wall3AreaTextInputLayout.error = "Input wall3 area!"
            } else if (wall4Type == EXTERNAL && wall4Area == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "Wall4 area can`t be 0.0",
                    Toast.LENGTH_LONG
                ).show()
                wall4AreaTextInputLayout.error = "Input wall4 area!"
            } else if (ceilingType == EXTERNAL && floorType == EXTERNAL && ceilingArea != floorArea) {
                Toast.makeText(
                    requireContext(),
                    "Ceiling area and floor area must be equal",
                    Toast.LENGTH_LONG
                ).show()
                ceilingAreaTextInputLayout.error = "Ceiling area must be equal floor area"
                floorAreaTextInputLayout.error = "Floor area must be equal ceiling area"
            } else if (wall1Type == EXTERNAL && wall3Type == EXTERNAL && wall1Area != wall3Area) {
                Toast.makeText(
                    requireContext(),
                    "Wall1 area and wall3 area must be equal",
                    Toast.LENGTH_LONG
                ).show()
                wall1AreaTextInputLayout.error = "Wall1 area must be equal wall3 area"
                wall3AreaTextInputLayout.error = "Wall3 area must be equal wall1 area"
            } else if (wall2Type == EXTERNAL && wall4Type == EXTERNAL && wall2Area != wall4Area) {
                Toast.makeText(
                    requireContext(),
                    "Wall2 area and wall4 area must be equal",
                    Toast.LENGTH_LONG
                ).show()
                wall1AreaTextInputLayout.error = "Wall2 area must be equal wall4 area"
                wall3AreaTextInputLayout.error = "Wall4 area must be equal wall2 area"
            } else {
                val list = mutableListOf<RoomComponent>()
                if (ceilingType == EXTERNAL) {
                    list.add(
                        RoomComponent(
                            area = ceilingArea,
                            insulated = if (ceilingMaterialSwitch.isChecked) Constants.CEILING_INSULATED else Constants.CEILING_UNINSULATED
                        )
                    )
                }
                if (floorType == EXTERNAL) {
                    list.add(
                        RoomComponent(
                            area = floorArea,
                            insulated = if (floorMaterialSwitch.isChecked) Constants.FLOOR_INSULATED else Constants.FLOOR_UNINSULATED
                        )
                    )
                }
                if (wall1Type == EXTERNAL) {
                    list.add(
                        RoomComponent(
                            area = wall1Area,
                            insulated = if (wall1MaterialSwitch.isChecked) Constants.WALL_INSULATED else Constants.WALL_UNINSULATED
                        )
                    )
                }
                if (wall2Type == EXTERNAL) {
                    list.add(
                        RoomComponent(
                            area = wall2Area,
                            insulated = if (wall2MaterialSwitch.isChecked) Constants.WALL_INSULATED else Constants.WALL_UNINSULATED
                        )
                    )
                }
                if (wall3Type == EXTERNAL) {
                    list.add(
                        RoomComponent(
                            area = wall3Area,
                            insulated = if (wall3MaterialSwitch.isChecked) Constants.WALL_INSULATED else Constants.WALL_UNINSULATED
                        )
                    )
                }
                if (wall4Type == EXTERNAL) {
                    list.add(
                        RoomComponent(
                            area = wall4Area,
                            insulated = if (wall4MaterialSwitch.isChecked) Constants.WALL_INSULATED else Constants.WALL_UNINSULATED
                        )
                    )
                }

                viewModel.setEvent(
                    AddNewRoomContract.Event.AddNewHouse(
                        name = getString(args.roomType.type),
                        list = list,
                        houseId = args.houseId
                    )
                )
            }
        }
        setUpCeilingExposedDropdownMenu()
        setUpFloorExposedDropdownMenu()
        setUpWall1ExposedDropdownMenu()
        setUpWall2ExposedDropdownMenu()
        setUpWall3ExposedDropdownMenu()
        setUpWall4ExposedDropdownMenu()
    }

    override fun observeViewModel() = with(viewModel) {
        effect.observe {
            when (it) {
                AddNewRoomContract.Effect.NavigateToHouseDetails -> pop()
            }
        }
    }

    private fun setUpCeilingExposedDropdownMenu() = with(binding) {
        (ceilingMenu.editText as? MaterialAutoCompleteTextView)?.apply {
            setText("None")
            setAdapter(itemsAdapter)
            setSimpleItems(items)
            setOnItemClickListener { _, _, i, _ ->
                when (i) {
                    0 -> ceilingDetails.isVisible = false
                    1 -> ceilingDetails.isVisible = true
                }
                addButton.isEnabled = isAddButtonEnabled()
            }
        }
        ceilingAreaTextInputLayout.editText?.doOnTextChanged { _, _, _, _ ->
            ceilingAreaTextInputLayout.error = null
            floorAreaTextInputLayout.error = null
        }
    }

    private fun setUpFloorExposedDropdownMenu() = with(binding) {
        (floorMenu.editText as? MaterialAutoCompleteTextView)?.apply {
            setText("None")
            setAdapter(itemsAdapter)
            setSimpleItems(items)
            setOnItemClickListener { _, _, i, _ ->
                when (i) {
                    0 -> floorDetails.isVisible = false
                    1 -> floorDetails.isVisible = true
                }
                addButton.isEnabled = isAddButtonEnabled()
            }
        }
        floorAreaTextInputLayout.editText?.doOnTextChanged { _, _, _, _ ->
            ceilingAreaTextInputLayout.error = null
            floorAreaTextInputLayout.error = null
        }
    }

    private fun setUpWall1ExposedDropdownMenu() = with(binding) {
        (wall1Menu.editText as? MaterialAutoCompleteTextView)?.apply {
            setText("None")
            setAdapter(itemsAdapter)
            setSimpleItems(items)
            setOnItemClickListener { _, _, i, _ ->
                when (i) {
                    0 -> wall1Details.isVisible = false
                    1 -> wall1Details.isVisible = true
                }
                addButton.isEnabled = isAddButtonEnabled()
            }
        }
        wall1AreaTextInputLayout.editText?.doOnTextChanged { _, _, _, _ ->
            wall1AreaTextInputLayout.error = null
            wall3AreaTextInputLayout.error = null
        }
    }

    private fun setUpWall2ExposedDropdownMenu() = with(binding) {
        (wall2Menu.editText as? MaterialAutoCompleteTextView)?.apply {
            setText("None")
            setAdapter(itemsAdapter)
            setSimpleItems(items)
            setOnItemClickListener { _, _, i, _ ->
                when (i) {
                    0 -> wall2Details.isVisible = false
                    1 -> wall2Details.isVisible = true
                }
                addButton.isEnabled = isAddButtonEnabled()
            }
        }
        wall2AreaTextInputLayout.editText?.doOnTextChanged { _, _, _, _ ->
            wall2AreaTextInputLayout.error = null
            wall4AreaTextInputLayout.error = null
        }
    }

    private fun setUpWall3ExposedDropdownMenu() = with(binding) {
        (wall3Menu.editText as? MaterialAutoCompleteTextView)?.apply {
            setText("None")
            setAdapter(itemsAdapter)
            setSimpleItems(items)
            setOnItemClickListener { _, _, i, _ ->
                when (i) {
                    0 -> wall3Details.isVisible = false
                    1 -> wall3Details.isVisible = true
                }
                addButton.isEnabled = isAddButtonEnabled()
            }
        }
        wall3AreaTextInputLayout.editText?.doOnTextChanged { _, _, _, _ ->
            wall1AreaTextInputLayout.error = null
            wall3AreaTextInputLayout.error = null
        }
    }

    private fun setUpWall4ExposedDropdownMenu() = with(binding) {
        (wall4Menu.editText as? MaterialAutoCompleteTextView)?.apply {
            setText("None")
            setAdapter(itemsAdapter)
            setSimpleItems(items)
            setOnItemClickListener { _, _, i, _ ->
                when (i) {
                    0 -> wall4Details.isVisible = false
                    1 -> wall4Details.isVisible = true
                }
                addButton.isEnabled = isAddButtonEnabled()
            }
        }
        wall4AreaTextInputLayout.editText?.doOnTextChanged { _, _, _, _ ->
            wall2AreaTextInputLayout.error = null
            wall4AreaTextInputLayout.error = null
        }
    }

    private fun isAddButtonEnabled() =
        binding.ceilingMenu.editText?.text.toString() != NONE &&
                binding.floorMenu.editText?.text.toString() != NONE &&
                binding.wall1Menu.editText?.text.toString() != NONE &&
                binding.wall2Menu.editText?.text.toString() != NONE &&
                binding.wall3Menu.editText?.text.toString() != NONE &&
                binding.wall4Menu.editText?.text.toString() != NONE

    private companion object {
        const val NONE = "None"
        const val INTERNAL = "Internal"
        const val EXTERNAL = "External"
    }
}