package com.chi.heat.loss.app.data.database.dao

import androidx.room.*
import com.chi.heat.loss.app.data.database.model.HouseEntity
import com.chi.heat.loss.app.data.database.model.RoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeatLossDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHouses(vararg houseEntities: HouseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRooms(vararg roomEntities: RoomEntity)

    @Query("DELETE FROM houses WHERE id == :houseId")
    suspend fun deleteHouseById(houseId: String)

    @Query("DELETE FROM rooms WHERE id == :roomId")
    suspend fun deleteRoomById(roomId: String)

    @Query("DELETE FROM rooms WHERE houseId == :houseId")
    suspend fun deleteAllRoomsByHouseId(houseId: String)

    @Transaction
    suspend fun deleteHouseWithRoomsByHouseId(houseId: String) {
        deleteAllRoomsByHouseId(houseId)
        deleteHouseById(houseId)
    }

    @Query("SELECT * FROM houses")
    fun getAllHouses(): Flow<List<HouseEntity>>

    @Query("SELECT * FROM houses WHERE id = :houseId")
    fun getHouseById(houseId: String): Flow<HouseEntity>

    @Query("SELECT * FROM rooms WHERE houseId = :houseId")
    fun getRoomsByHouseId(houseId: String): Flow<List<RoomEntity>>

}