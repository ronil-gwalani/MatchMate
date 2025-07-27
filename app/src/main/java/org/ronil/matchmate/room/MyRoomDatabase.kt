package org.ronil.matchmate.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.ronil.matchmate.models.UserEntity
import org.ronil.matchmate.utils.AppConstants
import androidx.room.TypeConverters


@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = true
)


@TypeConverters(Converters::class)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract val myRoomDatabaseDao: MyRoomDatabaseDao
}





internal fun getAndroidPreferencesPath(context: Context): String {
    return context.filesDir.resolve(AppConstants.Preferences.APP_PREFERENCES).absolutePath
}

