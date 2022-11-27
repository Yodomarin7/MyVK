package com.example.myvk.adapters

import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.GroupModel
import com.example.myvk.GetGroupViewModel
import com.example.myvk.R
import com.example.myvk.screens.MainActivity
import java.util.*

class GroupRecyclerAdapter(
    _getGroupViewModel: GetGroupViewModel,
    _navController: NavController,
    _mainActivity: MainActivity
) : ListAdapter<GroupModel.Params, GroupRecyclerAdapter.GroupViewHolder>(GroupComparator()) {

    private val getGroupViewModel = _getGroupViewModel
    private val navController = _navController
    private val mainActivity = _mainActivity

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtDescr: TextView = itemView.findViewById(R.id.txtDescr)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                val item: GroupModel.Params = getItem(position)

                Log.e("NEBUR", "iId: ${item.id}, iSN: ${item.screenName}, iN: ${item.name}")

                getGroupViewModel.setGroup(item)
                navController.navigate(R.id.action_getGroupsFragment_to_userParamsFragment)
            }


            val callback = object : ActionMode.Callback {

                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    menu?.add(Menu.NONE, Menu.NONE, 0, "Delete")
                    menu?.getItem(0)?.icon = ContextCompat.getDrawable(mainActivity, R.drawable.ic_baseline_delete_24)
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    mainActivity.actionMode?.finish()

                    return when (item?.order) {
                        0 -> {
                            val position = adapterPosition
                            val item: GroupModel.Params = getItem(position)

                            //val currentList =  currentList.toMutableList()
                            //currentList.removeAt(position)
                            //notifyItemRemoved(position)
                            getGroupViewModel.deleteGroup(item.id)

                            true
                        }
                        else -> false
                    }
                }

                override fun onDestroyActionMode(mode: ActionMode?) {

                }
            }

            itemView.setOnLongClickListener {
                val position = adapterPosition
                val item: GroupModel.Params = getItem(position)

                mainActivity.actionMode = mainActivity.startSupportActionMode(callback)
                mainActivity.actionMode?.title = item.name

                return@setOnLongClickListener true
            }
        }

        //txt1.movementMethod = LinkMovementMethod.getInstance()
        //txt1.isClickable = true
    }

    class GroupComparator : DiffUtil.ItemCallback<GroupModel.Params>() {
        override fun areItemsTheSame(oldItem: GroupModel.Params, newItem: GroupModel.Params): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GroupModel.Params, newItem: GroupModel.Params): Boolean {
            return oldItem.name == newItem.name && oldItem.description == newItem.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.group_item, parent, false)
        return GroupViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        /*val str:String = "<a href=\"https://vk.com/id" + items[pos].id + "\">"+ items[pos].firstName +
                " " + items[pos].lastName + "</a>"
        holder.txt1.text = Html.fromHtml(str)*/
        val item: GroupModel.Params = getItem(position)
        holder.txtName.text = item.name
        holder.txtDescr.text = item.description
    }
}