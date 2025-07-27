package org.ronil.matchmate.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ronil.matchmate.models.Coordinates
import org.ronil.matchmate.models.Dob
import org.ronil.matchmate.models.Id
import org.ronil.matchmate.models.Location
import org.ronil.matchmate.models.Login
import org.ronil.matchmate.models.Name
import org.ronil.matchmate.models.Picture
import org.ronil.matchmate.models.Registered
import org.ronil.matchmate.models.Street
import org.ronil.matchmate.models.Timezone
import java.lang.reflect.Type

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates?): String? {
        return gson.toJson(coordinates)
    }

    @TypeConverter
    fun toCoordinates(data: String?): Coordinates? {
        return data?.let {
            gson.fromJson(it, Coordinates::class.java)
        }
    }

    @TypeConverter
    fun fromStreet(street: Street?): String? {
        return gson.toJson(street)
    }

    @TypeConverter
    fun toStreet(data: String?): Street? {
        return data?.let {
            gson.fromJson(it, Street::class.java)
        }
    }

    @TypeConverter
    fun fromTimezone(timezone: Timezone?): String? {
        return gson.toJson(timezone)
    }

    @TypeConverter
    fun toTimezone(data: String?): Timezone? {
        return data?.let {
            gson.fromJson(it, Timezone::class.java)
        }
    }

    @TypeConverter
    fun fromLocation(location: Location?): String? {
        return gson.toJson(location)
    }

    @TypeConverter
    fun toLocation(data: String?): Location? {
        return data?.let {
            gson.fromJson(it, Location::class.java)
        }
    }

    @TypeConverter
    fun fromName(name: Name?): String? {
        return gson.toJson(name)
    }

    @TypeConverter
    fun toName(data: String?): Name? {
        return data?.let {
            gson.fromJson(it, Name::class.java)
        }
    }

    @TypeConverter
    fun fromDob(dob: Dob?): String? {
        return gson.toJson(dob)
    }

    @TypeConverter
    fun toDob(data: String?): Dob? {
        return data?.let {
            gson.fromJson(it, Dob::class.java)
        }
    }

    @TypeConverter
    fun fromId(id: Id?): String? {
        return gson.toJson(id)
    }

    @TypeConverter
    fun toId(data: String?): Id? {
        return data?.let {
            gson.fromJson(it, Id::class.java)
        }
    }

    @TypeConverter
    fun fromLogin(login: Login?): String? {
        return gson.toJson(login)
    }

    @TypeConverter
    fun toLogin(data: String?): Login? {
        return data?.let {
            gson.fromJson(it, Login::class.java)
        }
    }

    @TypeConverter
    fun fromPicture(picture: Picture?): String? {
        return gson.toJson(picture)
    }

    @TypeConverter
    fun toPicture(data: String?): Picture? {
        return data?.let {
            gson.fromJson(it, Picture::class.java)
        }
    }

    @TypeConverter
    fun fromRegistered(registered: Registered?): String? {
        return gson.toJson(registered)
    }

    @TypeConverter
    fun toRegistered(data: String?): Registered? {
        return data?.let {
            gson.fromJson(it, Registered::class.java)
        }
    }
}
