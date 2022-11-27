package com.example.myvk.adapters

import android.graphics.Color
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.*
import android.view.MenuItem.SHOW_AS_ACTION_NEVER
import android.widget.TextView
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.UserModel
import com.example.myvk.GetUserViewModel
import com.example.myvk.R
import com.example.myvk.screens.MainActivity

class UserRecyclerAdapter(
    private val dataSet: List<UserModel.Params>,
    _getUserViewModel: GetUserViewModel,
    _mainActivity: MainActivity
) : RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>() {

    private val getUserViewModel = _getUserViewModel
    private val mainActivity = _mainActivity

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        val txtUser: TextView = view.findViewById(R.id.user)
        val txtUsed: TextView = view.findViewById(R.id.used)

        init {
            txtUser.movementMethod = LinkMovementMethod.getInstance()
            txtUser.isClickable = true

            view.setOnLongClickListener {
                val l_position = adapterPosition
                val item: UserModel.Params = dataSet[l_position]

                mainActivity.actionMode = mainActivity.startSupportActionMode(object : ActionMode.Callback {

                    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        menu?.add(Menu.NONE, Menu.NONE, 0, "RED")?.setShowAsActionFlags(SHOW_AS_ACTION_NEVER)
                        menu?.add(Menu.NONE, Menu.NONE, 1, "YELLOW")?.setShowAsActionFlags(SHOW_AS_ACTION_NEVER)
                        menu?.add(Menu.NONE, Menu.NONE, 2, "GREEN")?.setShowAsActionFlags(SHOW_AS_ACTION_NEVER)
                        menu?.add(Menu.NONE, Menu.NONE, 3, "NONE")?.setShowAsActionFlags(SHOW_AS_ACTION_NEVER)
                        return true
                    }

                    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        return false
                    }

                    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                        val userItem: UserModel.Params = dataSet[l_position]
                        mainActivity.actionMode?.finish()

                        return when (item?.order) {
                            0 -> {
                                userItem.used = UserModel.Used.RED
                                getUserViewModel.updateUserDao(userItem)
                                notifyItemChanged(l_position)

                                true
                            }
                            1 -> {
                                userItem.used = UserModel.Used.YELLOW
                                getUserViewModel.updateUserDao(userItem)
                                notifyItemChanged(l_position)

                                true
                            }
                            2 -> {
                                userItem.used = UserModel.Used.GREEN
                                getUserViewModel.updateUserDao(userItem)
                                notifyItemChanged(l_position)

                                true
                            }
                            3 -> {
                                userItem.used = UserModel.Used.NONE
                                getUserViewModel.deleteUserDao(userItem.id)
                                notifyItemChanged(l_position)

                                true
                            }
                            else -> false
                        }
                    }

                    override fun onDestroyActionMode(mode: ActionMode?) { }
                })
                mainActivity.actionMode?.title = item.firstName

                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]

        val str:String = "<a href=\"https://vk.com/id" + item.id + "\">"+ item.firstName +
                " " + item.lastName + "</a>" + "<br> Был " + item.lastSeen + " дней назад"
        holder.txtUser.text = Html.fromHtml(str)
        //holder.txtUser.text = "${item.firstName} ${item.lastName}"

        when (item.used) {
            UserModel.Used.NONE -> holder.txtUsed.setTextColor(Color.parseColor("#FFFFFF"))
            UserModel.Used.RED -> holder.txtUsed.setTextColor(Color.parseColor("#E80606"))
            UserModel.Used.YELLOW -> holder.txtUsed.setTextColor(Color.parseColor("#FACC16"))
            UserModel.Used.GREEN -> holder.txtUsed.setTextColor(Color.parseColor("#21C407"))
        }
    }

    override fun getItemCount(): Int = dataSet.size
}