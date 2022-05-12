package com.hfad.ezfoodapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.jar.Attributes

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun fromAnyToString(attributes: Any?) : String{
        if (attributes == null){
            return ""
        }
        return attributes as String

    }
    @TypeConverter
    fun fromStringtoAny(attributes: String?) : Any{
        if (attributes == null)
            return ""
        return attributes

    }
}