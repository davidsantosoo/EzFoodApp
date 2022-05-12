package com.hfad.ezfoodapp.db

import android.content.Context
import androidx.room.*
import com.hfad.ezfoodapp.pojo.Meal

@Database(entities = [Meal::class], version = 2)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase()  {
    abstract fun mealDao():MealDao

    companion object{
        @Volatile
        var INSTANCE:MealDatabase? = null

        fun getInstance(context:Context):MealDatabase{
            if (INSTANCE == null){
                INSTANCE  = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }


}