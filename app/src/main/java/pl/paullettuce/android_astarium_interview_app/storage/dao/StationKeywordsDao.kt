package pl.paullettuce.android_astarium_interview_app.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import io.reactivex.rxjava3.core.Completable
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationKeywordEntity

@Dao
interface StationKeywordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insert(stations: List<StationKeywordEntity>): Completable
}