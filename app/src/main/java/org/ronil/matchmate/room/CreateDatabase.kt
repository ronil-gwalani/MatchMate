package org.ronil.matchmate.room

import android.content.Context
import androidx.room.Room

class CreateDatabase( private val context: Context,) {
    fun getDatabase(): MyRoomDatabase {
        return   Room.databaseBuilder(
            context,
            MyRoomDatabase::class.java,
            "ron.db"
        ).build()


    }
}