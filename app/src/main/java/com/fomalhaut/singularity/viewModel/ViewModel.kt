package com.fomalhaut.singularity.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fomalhaut.singularity.dev.data.dao.PasswordDao
import com.fomalhaut.singularity.dev.data.entity.PasswordEntity
import kotlinx.coroutines.launch

class ViewModel(private val dao: PasswordDao): ViewModel() {
    val allPasswords = dao.getAllPasswords()
    fun addPassword(entity: PasswordEntity) {
        viewModelScope.launch {
            Log.d("MY_LOG", "Пытаюсь сохранить: ${entity.name}")
            dao.insert(entity)
            Log.d("MY_LOG", "Кажется, сохранил!")
        }
    }
    fun deletePassword(entity: PasswordEntity){
        viewModelScope.launch {
            dao.delete(entity)
        }

    }
    fun updatePassword(entity: PasswordEntity){
        viewModelScope.launch {
            dao.update(entity)
        }

    }
}