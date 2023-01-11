package com.example.myvk.adapters

import android.graphics.Color
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.*
import android.view.MenuItem.SHOW_AS_ACTION_NEVER
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.PopupMenu
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
        private val moreBtn: ImageButton = view.findViewById(R.id.imageButton)
        val txtUser: TextView = view.findViewById(R.id.user)
        val txtUsed: TextView = view.findViewById(R.id.used)

        init {
            txtUser.movementMethod = LinkMovementMethod.getInstance()
            txtUser.isClickable = true

            moreBtn.setOnClickListener { v->
                val l_position = adapterPosition
                val userItem: UserModel.Params = dataSet[l_position]

                val popup = PopupMenu(mainActivity, v)
                popup.menu.add(Menu.NONE, Menu.NONE, 0, "RED")
                popup.menu.add(Menu.NONE, Menu.NONE, 1, "YELLOW")
                popup.menu.add(Menu.NONE, Menu.NONE, 2, "GREEN")
                popup.menu.add(Menu.NONE, Menu.NONE, 3, "NONE")
                popup.menu.add(Menu.NONE, Menu.NONE, 4, "BLACK")

                popup.setOnMenuItemClickListener { menuItem ->
                    when (menuItem?.order) {
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
                        4 -> {
                            userItem.used = UserModel.Used.BLACK
                            getUserViewModel.updateUserDao(userItem)
                            notifyItemChanged(l_position)

                            true
                        }
                        else -> false
                    }
                }
                popup.show()
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
                " " + item.lastName + "</a>" + "<br> Был " + item.lastSeen + " минут назад"
        holder.txtUser.text = Html.fromHtml(str)
        //holder.txtUser.text = "${item.firstName} ${item.lastName}"

        when (item.used) {
            UserModel.Used.NONE -> holder.txtUsed.setTextColor(Color.parseColor("#FFFFFF"))
            UserModel.Used.RED -> holder.txtUsed.setTextColor(Color.parseColor("#E80606"))
            UserModel.Used.YELLOW -> holder.txtUsed.setTextColor(Color.parseColor("#FACC16"))
            UserModel.Used.GREEN -> holder.txtUsed.setTextColor(Color.parseColor("#21C407"))
            UserModel.Used.BLACK -> holder.txtUsed.setTextColor(Color.parseColor("#000000"))
        }
    }

    override fun getItemCount(): Int = dataSet.size
}