package com.nandaadisaputra.tourismapp.core.domain.usecase

import androidx.lifecycle.LiveData
import com.nandaadisaputra.tourismapp.core.data.Resource
import com.nandaadisaputra.tourismapp.core.domain.model.Tourism

interface TourismUseCase {
    fun getAllTourism(): LiveData<Resource<List<Tourism>>>
    fun getFavoriteTourism(): LiveData<List<Tourism>>
    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}