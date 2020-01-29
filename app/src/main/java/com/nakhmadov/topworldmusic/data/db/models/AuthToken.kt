package com.nakhmadov.topworldmusic.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_token")
data class AuthToken(@PrimaryKey val token: String = "")