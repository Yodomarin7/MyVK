package com.example.myvk.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myvk.GetGroupViewModel
import com.example.myvk.GetViewModelFactory
import com.example.myvk.MyApp
import com.example.myvk.R
import com.google.android.material.appbar.MaterialToolbar
import com.vk.api.sdk.VK
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var vmf: GetViewModelFactory

    lateinit var vm: GetGroupViewModel
    private lateinit var navController: NavController
    lateinit var toolBar: MaterialToolbar
    var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (applicationContext as MyApp).appComponent.inject(this)

        toolBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolBar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        vm = ViewModelProvider(
            this,
            vmf)[GetGroupViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Loading...")
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.getItem(0)

        if(VK.isLoggedIn()) { item?.title = "Log out" }
        else { item?.title = "Log in" }

        return true
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.order)  {
            0 -> {
                if(VK.isLoggedIn()) { VK.logout() }
                else {
                    navController.navigate(R.id.loginVkActivity)
                }

                return true
            }
        }

        return false
    }




    /*
    fun first() {
        val items = mutableListOf<MyItem>()

        val recyclerView: RecyclerView = findViewById(R.id.rvGroups)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.HORIZONTAL))
        recyclerView.layoutManager = LinearLayoutManager(this)

        val isLoggedIn = VK.isLoggedIn()
        Log.e("NEBUR", "isLoggedIn:" + isLoggedIn.toString())

        if(!isLoggedIn) {
            val authLauncher = VK.login(this) { result: VKAuthenticationResult ->
                when (result) {
                    is VKAuthenticationResult.Success -> {
                        result.token.accessToken
                        Log.e("NEBUR", "SUC");
                    }
                    is VKAuthenticationResult.Failed -> {
                        Log.e("NEBUR", "ERR");
                    }
                }
            }
            authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS))
        }
        //VK.logout()

        val btn = findViewById<Button>(R.id.btn)
        val edtTxt = findViewById<EditText>(R.id.edtTxt)

        btn.setOnClickListener {
            recyclerView.adapter = null
            val grouId: String = edtTxt.text.toString()
            Log.e("NEBUR", "grouId: " + grouId)

            val fields: List<UsersFields>? = listOf(UsersFields.CAN_WRITE_PRIVATE_MESSAGE, UsersFields.SEX,
                UsersFields.LAST_SEEN, UsersFields.BDATE, UsersFields.FIRST_NAME_ABL, UsersFields.LAST_NAME_ABL)

            VK.execute(GroupsService().groupsGetMembers(grouId,
                GroupsGetMembersSort.ID_DESC, 0, 1000, fields = fields),

                object: VKApiCallback<GroupsGetMembersFieldsResponse> {
                    override fun success(result: GroupsGetMembersFieldsResponse) {
                        val itms: List<GroupsUserXtrRole> = result.items

                        val unixTime = (System.currentTimeMillis() / 1000L).toInt()

                        itms.forEach {
                            val sex = it.sex?.value ?: 0
                            val lastSeen = it.lastSeen?.time ?: 0
                            val tme: Int = (unixTime - lastSeen!!)/60/60/24
                            val canWriteMsg = it.canWritePrivateMessage?.value ?: 0

                            if(sex == 1 && tme < 10 && canWriteMsg == 1) {
                                items.add(MyItem(it.lastNameAbl, it.firstNameAbl, tme, it.id.value.toString()))
                            }
                        }
                    }

                    override fun fail(error: Exception) {
                        Log.e("NEBUR", "groupsGetMembers: ERR, " + error.message);
                    }
                }
            )

            recyclerView.adapter = CustomRecyclerAdapter(items)
        }


    }

    */
}