package pl.paullettuce.android_astarium_interview_app.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

@Dao
interface StationDataDao {

    @Query("SELECT * FROM station_data_entity ORDER BY hits DESC")
    fun getStationsData(): LiveData<List<StationDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg stations: StationDataEntity): Completable

    @Query("SELECT * FROM station_data_entity WHERE id=:id")
    fun getStation(id: Long): Single<StationDataEntity>

}