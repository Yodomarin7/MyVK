package com.example.myvk

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.data.repository.GroupRepository
import com.example.data.repository.UserRepository
import com.example.data.sources.group.room.AppDatabase
import com.example.data.sources.network.UserVK
import com.example.data.sources.preference.UserParamsSharedPreferences
import com.example.domain.models.UserModel
import com.example.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.launch

class GetUserViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    //users list
    private val _allUsers: MutableLiveData<UserModel.ResponsParams> = MutableLiveData<UserModel.ResponsParams>()
    val allUsers: LiveData<UserModel.ResponsParams> = _allUsers

    //add error text
    private val _errAdd = MutableLiveData<String>()
    val errAdd: LiveData<String> = _errAdd
    fun setErrAdd(str: String) { _errAdd.value = str }

    //date edttet
    private val _lastSeen = MutableLiveData<Int>()
    val lastSeen: LiveData<Int> = _lastSeen

    //dropdown sex text
    private val _sex = MutableLiveData<Int>()
    val sex: LiveData<Int> = _sex

    //showRed checkBox
    private val _showRed = MutableLiveData<Boolean>()
    val showRed: LiveData<Boolean> = _showRed

    //showRed checkBox
    private val _showYellow = MutableLiveData<Boolean>()
    val showYellow: LiveData<Boolean> = _showYellow

    //showRed checkBox
    private val _showGreen = MutableLiveData<Boolean>()
    val showGreen: LiveData<Boolean> = _showGreen

    //go to getusersUC
    fun getUsers(grouId: String, params: UserModel.Params, showRed: Boolean, showYellow: Boolean, showGreen: Boolean) {

        Log.e("NEBUR", "")

        viewModelScope.launch {
            val result = getUsersUseCase.run(grouId, params, showRed, showYellow, showGreen)
            _allUsers.value = result
        }
    }

    //dao
    fun updateUserDao(userParams: UserModel.Params) {
        viewModelScope.launch {
            userRepository.updateOneDao(userParams)
        }
    }
    fun deleteUserDao(userId: Int) {
        viewModelScope.launch {
            userRepository.deleteOneDao(userId)
        }
    }

    //params
    fun saveSex(i: Int) {
        viewModelScope.launch {
            userRepository.saveSex(i)
        }
    }
    fun saveLastSeen(i: Int) {
        viewModelScope.launch {
            userRepository.saveLastSeen(i)
        }
    }
    fun saveShowRed(b: Boolean) {
        viewModelScope.launch {
            userRepository.saveShowRed(b)
        }
    }
    fun saveShowYellow(b: Boolean) {
        viewModelScope.launch {
            userRepository.saveShowYellow(b)
        }
    }
    fun saveShowGreen(b: Boolean) {
        viewModelScope.launch {
            userRepository.saveShowGreen(b)
        }
    }
    fun getParams() {
        viewModelScope.launch {
            _sex.value = userRepository.getSex()
            _lastSeen.value = userRepository.getLastSeen()
            _showRed.value = userRepository.getShowRed()
            _showYellow.value = userRepository.getShowYellow()
            _showGreen.value = userRepository.getShowGreen()
        }
    }

}

class GetUserViewModelFactory(
    private val getUsersUC: GetUsersUseCase,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetUserViewModel::class.java)) {
            return GetUserViewModel(
                getUsersUC, userRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//class GetUserViewModelFactory(context: Context) : ViewModelProvider.Factory {
//    //data source
//    private val db by lazy { AppDatabase.getDatabase(context.applicationContext) }
//    private val vk = UserVK()
//    private val userParamsSharedPreferences: UserParamsSharedPreferences =
//        UserParamsSharedPreferences(context.applicationContext)
//    //private val groupRetrofit = GroupRetrofit()
//
//    //repository
//    private val userRepository: UserRepository by lazy {
//        UserRepository(db.usersDao(), vk, userParamsSharedPreferences) }
//
//    //usecase
//    private val getUsersUC: GetUsersUseCase by lazy {  GetUsersUseCase(userRepository) }
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(GetUserViewModel::class.java)) {
//            return GetUserViewModel(
//                getUsersUC, userRepository
//            ) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}