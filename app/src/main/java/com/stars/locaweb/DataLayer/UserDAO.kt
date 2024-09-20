package com.stars.locaweb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stars.locaweb.DataLayer.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun getUser(email: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertUser(user: User)
}

