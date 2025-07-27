package org.ronil.matchmate.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


data class UsersModel(
    val info: Info? = null,
    val results: List<Result?>? = null
)


@Serializable
data class Result(
    val cell: String? = null,
    val dob: Dob? = null,
    val email: String? = null,
    val gender: String? = null,
    val id: Id? = null,
    val location: Location? = null,
    val login: Login? = null,
    val name: Name? = null,
    val nat: String? = null,
    val phone: String? = null,
    val picture: Picture? = null,
    val registered: Registered? = null
)

@Serializable

data class Coordinates(
    val latitude: String? = null,
    val longitude: String? = null
)
@Serializable

data class Dob(
    val age: Int? = null,
    val date: String? = null
)
@Serializable

data class Id(
    val name: String? = null,
    val value: String? = null
)
@Serializable

data class Info(
    val page: Int? = null,
    val results: Int? = null,
    val seed: String? = null,
    val version: String? = null
)
@Serializable

data class Location(
    val city: String? = null,
    val coordinates: Coordinates? = null,
    val country: String? = null,
    val postcode: String? = null,
    val state: String? = null,
    val street: Street? = null,
    val timezone: Timezone? = null
)
@Serializable

data class Login(
    val md5: String? = null,
    val password: String? = null,
    val salt: String? = null,
    val sha1: String? = null,
    val sha256: String? = null,
    val username: String? = null,
    val uuid: String? = null
)
@Serializable

data class Name(
    val first: String? = null,
    val last: String? = null,
    val title: String? = null
)
@Serializable

data class Picture(
    val large: String? = null,
    val medium: String? = null,
    val thumbnail: String? = null
)

@Serializable

data class Registered(
    val age: Int? = null,
    val date: String? = null
)
@Serializable

data class Street(
    val name: String? = null,
    val number: Int? = null
)
@Serializable

data class Timezone(
    val description: String? = null,
    val offset: String? = null
)


enum class Status{
    Accepted,Rejected,NoAction
}

@Serializable
@Entity
data class UserEntity(
//    @PrimaryKey(autoGenerate = true)
//    val uid: Int = 0,  // Room's primary key
    @PrimaryKey
    val email: String,

    val gender: String?,
    val status: Status?=Status.NoAction,
    val nat: String?,
    val phone: String?,
    val cell: String?,

    @Embedded(prefix = "name_")
    val name: Name?,

    @Embedded(prefix = "dob_")
    val dob: Dob?,

    @Embedded(prefix = "location_")
    val location: Location?,

    @Embedded(prefix = "picture_")
    val picture: Picture?,

    @Embedded(prefix = "login_")
    val login: Login?,

    @Embedded(prefix = "id_")
    val id: Id?,

    @Embedded(prefix = "registered_")
    val registered: Registered?
)


fun Result.toUserEntity(): UserEntity {
    return UserEntity(
        email = this.email.toString(),
        gender = this.gender,
        nat = this.nat,
        phone = this.phone,
        cell = this.cell,
        name = this.name,
        dob = this.dob,
        location = this.location,
        picture = this.picture,
        login = this.login,
        id = this.id,
        registered = this.registered
    )
}