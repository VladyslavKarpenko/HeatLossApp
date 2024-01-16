package com.chi.heat.loss.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chi.heat.loss.app.data.database.dao.HeatLossDao
import com.chi.heat.loss.app.data.database.model.HouseEntity
import com.chi.heat.loss.app.data.database.model.RoomEntity

@Database(
    entities = [
        HouseEntity::class,
        RoomEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HeatLossDatabase : RoomDatabase() {

    abstract fun houseDao(): HeatLossDao

    companion object {
        @Volatile
        private var INSTANCE: HeatLossDatabase? = null

        fun getDatabase(context: Context): HeatLossDatabase = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                HeatLossDatabase::class.java,
                "heat_loss_database"
            )
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = instance

            instance
        }
    }
}