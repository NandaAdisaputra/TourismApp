package com.nandaadisaputra.tourismapp.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nandaadisaputra.tourismapp.core.data.source.local.entity.TourismEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

@Dao
interface TourismDao {

    @Query("SELECT * FROM tourism")
//    fun getAllTourism(): LiveData<List<TourismEntity>>
//    fun getAllTourism(): Flowable<List<TourismEntity>>
    fun getAllTourism(): Flow<List<TourismEntity>>

    @Query("SELECT * FROM tourism where isFavorite = 1")
    fun getFavoriteTourism(): Flow<List<TourismEntity>>
//    fun getFavoriteTourism(): Flowable<List<TourismEntity>>
//    fun getFavoriteTourism(): LiveData<List<TourismEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertTourism(tourism: List<TourismEntity>): Completable
//    fun insertTourism(tourism: List<TourismEntity>)
    suspend fun insertTourism(tourism: List<TourismEntity>)

    @Update
    fun updateFavoriteTourism(tourism: TourismEntity)
}