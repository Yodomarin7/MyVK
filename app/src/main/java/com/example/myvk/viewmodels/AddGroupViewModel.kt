package com.example.myvk

import android.content.Context
import androidx.lifecycle.*
import com.example.data.repository.CommonRepository
import com.example.data.repository.GroupRepository
import com.example.data.sources.group.room.AppDatabase
import com.example.data.sources.network.GroupRetrofit
import com.example.data.sources.preference.MainSharedPreferences
import com.example.domain.models.GroupModel
import com.example.domain.usecase.AddGroupUseCase
import kotlinx.coroutines.launch

class AddGroupViewModel(
    private val addGroupUseCase: AddGroupUseCase
) : ViewModel()  {

    //add error text
    private val _errAdd = MutableLiveData<String>()
    val errAdd: LiveData<String> = _errAdd
    fun setErrAdd(str: String) { _errAdd.value = str }

    //name group text
    private val _nameGroup = MutableLiveData<String>()
    val nameGroup: LiveData<String> = _nameGroup

    //myResponse
    private val _myResponse = MutableLiveData<GroupModel.ResponsParams>()
    val myResponse: LiveData<GroupModel.ResponsParams> = _myResponse

    //go to addGroupUC
    fun addGroup(groupParams: GroupModel.Params) {
        viewModelScope.launch {
            val result = addGroupUseCase.run(groupParams)
            _myResponse.value = result
        }
    }

}

class AddViewModelFactory(
    private val addGroupUC: AddGroupUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddGroupViewModel::class.java)) {
            return AddGroupViewModel(
                addGroupUC
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//class AddViewModelFactory(context: Context) : ViewModelProvider.Factory {
//    //data source
//    private val db by lazy { AppDatabase.getDatabase(context.applicationContext) }
//    private val sharedPreferences = MainSharedPreferences(context)
//    private val groupRetrofit = GroupRetrofit()
//
//    //repository
//    private val groupRepository: GroupRepository by lazy {  GroupRepository(db.groupDao(), groupRetrofit) }
//    private val commonRepository: CommonRepository by lazy {  CommonRepository(sharedPreferences) }
//
//    //usecase
//    private val addGroupUC: AddGroupUseCase by lazy {  AddGroupUseCase(groupRepository, commonRepository) }
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(AddGroupViewModel::class.java)) {
//            return AddGroupViewModel(
//                addGroupUC
//            ) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}