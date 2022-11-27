package com.example.myvk.screens

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.domain.models.GroupModel
import com.example.myvk.*
import com.example.myvk.databinding.FragmentAddGroupBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.vk.api.sdk.VK
import javax.inject.Inject


class AddGroupFragment : Fragment(R.layout.fragment_add_group) {
    @Inject
    lateinit var addGroupVMF: AddViewModelFactory

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentAddGroupBinding
    private lateinit var addGroupViewModel: AddGroupViewModel
    private lateinit var navController: NavController

    private lateinit var btnAdd: Button
    private lateinit var edtTxtGroupId: TextInputEditText
    private lateinit var edtTxtDescription: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        (mainActivity.applicationContext as MyApp).appComponent.inject(this)

        navController = findNavController()
        addGroupViewModel = ViewModelProvider(
            this,
            addGroupVMF)[AddGroupViewModel::class.java]

        binding = FragmentAddGroupBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            addGroupVM = addGroupViewModel
            addGroupFragment = this@AddGroupFragment
        }

        mainActivity.supportActionBar?.title = "Add group"

        btnAdd = view.findViewById(R.id.btnAdd)
        btnAdd.isEnabled = true

        edtTxtGroupId = view.findViewById<TextInputLayout>(R.id.edtTxtGroupId).editText as TextInputEditText
        edtTxtDescription = view.findViewById<TextInputLayout>(R.id.edtTxtDescription).editText as TextInputEditText

        edtTxtGroupId.setText("")
        edtTxtDescription.setText("")

        addGroupViewModel.myResponse.observe(viewLifecycleOwner) { result ->
            btnAdd.isEnabled = true

            when (result) {
                is GroupModel.ResponsParams.Success -> {
                    //navController.navigate(R.id.action_addGroupFragment_to_getGroupsFragment)
                    navController.popBackStack()
                }
                is GroupModel.ResponsParams.Failed -> {
                    if (result.code == 0) { //ошибка == не залогинен
                        addGroupViewModel.setErrAdd("Need to login")
                    } else { // др ошибка
                        addGroupViewModel.setErrAdd("Error " + result.message)
                    }

                }
            }
        }
    }

    //var token: String = ""
    //btnAdd
    fun addGroup(view: View) {
        if (!VK.isLoggedIn()) {
            addGroupViewModel.setErrAdd("Need to login")
        } else {
            btnAdd.isEnabled = false
            addGroupViewModel.setErrAdd("")

            addGroupViewModel.addGroup(
                GroupModel.Params(
                    screenName = edtTxtGroupId.text.toString(),
                    description = edtTxtDescription.text.toString()
                )
            )
        }
    }

    //btnAddCancel
    fun cancelGroup() {
        navController.popBackStack()
    }
}