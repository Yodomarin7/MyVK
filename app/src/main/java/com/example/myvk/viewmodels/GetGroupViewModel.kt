package com.example.myvk.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.domain.models.GroupModel
import com.example.domain.usecase.GetGroupsUseCase
import com.example.domain.usecase.RemoveGroupUseCase
import kotlinx.coroutines.*

class GetGroupViewModel(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val removeGroupUseCase: RemoveGroupUseCase
) : ViewModel() {

    //groups list
    private val _allGroups: MutableLiveData<List<GroupModel.Params>> = MutableLiveData<List<GroupModel.Params>>()
    val allGroups: LiveData<List<GroupModel.Params>> = _allGroups

    //selected group
    private val _group: MutableLiveData<GroupModel.Params> = MutableLiveData<GroupModel.Params>()
    val group: LiveData<GroupModel.Params> = _group
    fun setGroup(group: GroupModel.Params) { _group.value = group }

    //go to getGroupsUC
    fun getGroups() {
        viewModelScope.launch {
            getGroupsUseCase.run().collect {
                _allGroups.value = it
            }
        }
    }

    //delete dao
    fun deleteGroup(groupId: Int) {
        viewModelScope.launch {
            removeGroupUseCase.run(groupId)
        }
    }

    override fun onCleared() {
        Log.e("NEBUR", "MVonCleared")
        super.onCleared()
    }
}

class GetViewModelFactory(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val removeGroupUseCase: RemoveGroupUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetGroupViewModel::class.java)) {
            return GetGroupViewModel(
                getGroupsUseCase,
                removeGroupUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//class GetViewModelFactory(context: Context) : ViewModelProvider.Factory {
//    //data source
//    private val db by lazy { AppDatabase.getDatabase(context.applicationContext) }
//    private val groupRetrofit = GroupRetrofit()
//
//    //repository
//    private val groupRepository: GroupRepository by lazy {  GroupRepository(db.groupDao(), groupRetrofit) }
//
//    //usecase
//    private val getGroupsUseCase: GetGroupsUseCase by lazy {  GetGroupsUseCase(groupRepository) }
//    private val removeGroupUseCase by lazy {  RemoveGroupUseCase(groupRepository) }
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(GetGroupViewModel::class.java)) {
//            return GetGroupViewModel(
//                getGroupsUseCase,
//                removeGroupUseCase
//            ) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}