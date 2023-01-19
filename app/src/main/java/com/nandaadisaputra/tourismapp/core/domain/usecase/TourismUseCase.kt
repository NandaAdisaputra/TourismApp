package com.nandaadisaputra.tourismapp.core.domain.usecase

import androidx.lifecycle.LiveData
import com.nandaadisaputra.tourismapp.core.data.Resource
import com.nandaadisaputra.tourismapp.core.domain.model.Tourism
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

interface TourismUseCase {
    //    fun getAllTourism(): LiveData<Resource<List<Tourism>>>
//    fun getFavoriteTourism(): LiveData<List<Tourism>>
//    fun getAllTourism(): Flowable<Resource<List<Tourism>>>
    fun getAllTourism(): Flow<Resource<List<Tourism>>>

    //    fun getFavoriteTourism(): Flowable<List<Tourism>>
    fun getFavoriteTourism(): Flow<List<Tourism>>

    //    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}