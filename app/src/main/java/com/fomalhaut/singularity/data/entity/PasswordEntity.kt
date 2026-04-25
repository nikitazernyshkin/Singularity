package com.fomalhaut.singularity.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var login: String,
    var password: String
)