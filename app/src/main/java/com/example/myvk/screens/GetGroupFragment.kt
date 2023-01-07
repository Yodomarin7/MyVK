package com.example.myvk.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvk.*
import com.example.myvk.adapters.GroupRecyclerAdapter
import com.example.myvk.databinding.FragmentGetGroupsBinding
import com.example.myvk.viewmodels.GetGroupViewModel

class GetGroupFragment: Fragment(R.layout.fragment_get_groups) {
    private lateinit var binding: FragmentGetGroupsBinding
    private lateinit var getGroupViewModel: GetGroupViewModel
    private lateinit var navController: NavController
    private lateinit var mainActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity
        getGroupViewModel = mainActivity.vm
        navController = findNavController()

        binding = FragmentGetGroupsBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            getGroupViewModel = getGroupViewModel
            getGroupFragment = this@GetGroupFragment
        }

        mainActivity.toolBar.title = "Get groups"
        mainActivity.toolBar.navigationIcon = null
        mainActivity.toolBar.setNavigationOnClickListener(null)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvGroups)
        val lm = LinearLayoutManager(mainActivity)
        val adapter = GroupRecyclerAdapter(getGroupViewModel, navController, mainActivity)

        recyclerView.layoutManager = lm
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, lm.orientation))
        recyclerView.adapter = adapter

        getGroupViewModel.allGroups.observe(viewLifecycleOwner) {
            adapter.submitList(it)

            Log.e("NEBUR", "New list: ${it.count()} items")
        }

        getGroupViewModel.getGroups()
    }

    override fun onPause() {
        super.onPause()

        mainActivity.actionMode?.finish()
    }

    //fab add_group
    fun addGroup(view: View) {
        Log.e("NEBUR", "addGroup")

        val bundle = Bundle()
        navController.navigate(R.id.action_getGroupsFragment_to_addGroupFragment)
    }
}