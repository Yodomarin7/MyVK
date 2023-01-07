package com.example.myvk.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.UserModel
import com.example.myvk.*
import com.example.myvk.adapters.UserRecyclerAdapter
import com.example.myvk.databinding.FragmentUsersParamsBinding
import com.example.myvk.viewmodels.GetGroupViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject

class UserParamsFragment : Fragment(R.layout.fragment_users_params) {
    @Inject
    lateinit var getUserVMF: GetUserViewModelFactory

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentUsersParamsBinding
    private lateinit var getUserViewModel: GetUserViewModel
    private lateinit var getGroupViewModel: GetGroupViewModel
    private lateinit var navController: NavController

    private lateinit var _paramsView: ConstraintLayout
    private lateinit var _rvView: FrameLayout

    private lateinit var _lastSeen: TextInputEditText
    private lateinit var _sex: AutoCompleteTextView
    private lateinit var _showRed: CheckBox
    private lateinit var _showYellow: CheckBox
    private lateinit var _showGreen: CheckBox
    private lateinit var _showBlack: CheckBox
    private lateinit var _btnGetUsers: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        (mainActivity.applicationContext as MyApp).appComponent.inject(this)

        getGroupViewModel = mainActivity.vm
        getUserViewModel = ViewModelProvider(
            this,
            getUserVMF
        )[GetUserViewModel::class.java]
        navController = findNavController()

        binding = FragmentUsersParamsBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            getUsersVM = getUserViewModel
            usersParamsFragment = this@UserParamsFragment
        }

        mainActivity.toolBar.title = getGroupViewModel.group.value?.name
        mainActivity.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_24)
        mainActivity.toolBar.setNavigationOnClickListener {
            navController.popBackStack()
        }

        _btnGetUsers = view.findViewById(R.id.button2)
        _btnGetUsers.isEnabled = true

        _paramsView = view.findViewById(R.id.paramsV)
        _rvView = view.findViewById(R.id.rvV)

        _paramsView.visibility = VISIBLE
        _rvView.visibility = GONE

        _showRed = view.findViewById(R.id.checkBox)
        _showRed.setOnCheckedChangeListener { buttonView, isChecked ->
            getUserViewModel.saveShowRed(isChecked)
        }

        _showYellow = view.findViewById(R.id.checkBox2)
        _showYellow.setOnCheckedChangeListener { buttonView, isChecked ->
            getUserViewModel.saveShowYellow(isChecked)
        }

        _showGreen = view.findViewById(R.id.checkBox3)
        _showGreen.setOnCheckedChangeListener { buttonView, isChecked ->
            getUserViewModel.saveShowGreen(isChecked)
        }

        _showBlack = view.findViewById(R.id.checkBox4)
        _showBlack.setOnCheckedChangeListener { buttonView, isChecked ->
            getUserViewModel.saveShowBlack(isChecked)
        }

        _lastSeen = view.findViewById<TextInputLayout>(R.id.editTextDate).editText as TextInputEditText
        _lastSeen.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus) {
                Log.e("NEBUR", "saveLastSeen")
                getUserViewModel.saveLastSeen(_lastSeen.text.toString().toInt())
            }
        }

        val items = listOf("Не выбран", "Женский", "Мужской")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)

        _sex = view.findViewById<TextInputLayout>(R.id.menu).editText as AutoCompleteTextView
        _sex.setAdapter(adapter)
        _sex.setOnItemClickListener { parent, view, position, id ->
            Log.e("NEBUR", "saveSex")
            getUserViewModel.saveSex(position)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.rvUsers)
        val lm = LinearLayoutManager(mainActivity)

        recyclerView.layoutManager = lm
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, lm.orientation))

        getUserViewModel.allUsers.observe(viewLifecycleOwner) { result ->
            _btnGetUsers.isEnabled = true

            when (result) {
                is UserModel.ResponsParams.Success -> {
                    _paramsView.visibility = GONE
                    _rvView.visibility = VISIBLE

                    val myAdapter = UserRecyclerAdapter(result.params, getUserViewModel, mainActivity)
                    recyclerView.adapter = myAdapter
                    myAdapter.notifyDataSetChanged()
                }
                is UserModel.ResponsParams.Failed -> {
                    getUserViewModel.setErrAdd(result.message)
                }
            }
        }

        getUserViewModel.showRed.observe(viewLifecycleOwner) { b ->
            _showRed.isChecked = b
        }
        getUserViewModel.showYellow.observe(viewLifecycleOwner) { b ->
            _showYellow.isChecked = b
        }
        getUserViewModel.showGreen.observe(viewLifecycleOwner) { b ->
            _showGreen.isChecked = b
        }
        getUserViewModel.showBlack.observe(viewLifecycleOwner) { b ->
            _showBlack.isChecked = b
        }

        getUserViewModel.sex.observe(viewLifecycleOwner) { i ->
            Log.e("NEBUR", "sex.observe")

            _sex.setText(_sex.adapter.getItem(i).toString(), false)
        }

        getUserViewModel.lastSeen.observe(viewLifecycleOwner) { i ->
            Log.e("NEBUR", "lastSeen.observe")

            _lastSeen.setText(i.toString())
        }

        getUserViewModel.getParams()
    }

    override fun onPause() {
        super.onPause()

        mainActivity.actionMode?.finish()
    }

    //btnGet
    fun btnGetUsers(view: View) {
        _btnGetUsers.isEnabled = false
        getUserViewModel.setErrAdd("")

        val sex = _sex.text.toString()
        var intSex = 0

        when (sex) {
            "Не выбран" -> {
                intSex = 0
            }
            "Женский" -> {
                intSex = 1
            }
            "Мужской" -> {
                intSex = 2
            }
        }

        val gId = getGroupViewModel.group.value?.id.toString()
        val gSN = getGroupViewModel.group.value?.screenName
        Log.e("NEBUR", "gId: $gId, gSN: $gSN")

        getUserViewModel.getUsers(gId,
            UserModel.Params(
                sex = intSex,
                lastSeen = _lastSeen.text.toString().toInt()
            ),
            _showRed.isChecked, _showYellow.isChecked, _showGreen.isChecked, _showBlack.isChecked)
    }

    //btnCancel
    fun btnCancel() {
        Log.e("NEBUR", "btnCancel")

        navController.popBackStack()
    }
}