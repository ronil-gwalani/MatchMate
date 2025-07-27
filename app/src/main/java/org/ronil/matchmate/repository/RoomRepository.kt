package org.ronil.matchmate.repository

import org.ronil.matchmate.models.Status
import org.ronil.matchmate.models.UserEntity
import org.ronil.matchmate.room.MyRoomDatabaseDao

class RoomRepository(private val dao: MyRoomDatabaseDao) {
    suspend fun insertUser(user: List<UserEntity>) {
        dao.insertOrUpdateUsers(user)
    }

    suspend fun changeStatus(id: String, status: Status) {
        dao.changeStatus(id, status)
    }

    val allUsers = dao.getAllUsers()

  suspend fun getAvailableUserCount(): Int =dao.getAvailableUserCount()

}