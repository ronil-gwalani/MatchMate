package org.ronil.matchmate.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.ronil.matchmate.models.Id
import org.ronil.matchmate.models.Status
import org.ronil.matchmate.models.UserEntity

@Dao
interface MyRoomDatabaseDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUsers(users: List<UserEntity>)

    @Query("SELECT * FROM UserEntity")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("DELETE FROM UserEntity")
    suspend fun deleteAllUsers()


    @Query("UPDATE UserEntity SET status = :status WHERE email = :id")
    suspend fun changeStatus(id: String, status: Status)


    @Query("SELECT COUNT(*) FROM UserEntity WHERE status = :noActionStatus")
    suspend fun getAvailableUserCount(noActionStatus: Status = Status.NoAction): Int

}