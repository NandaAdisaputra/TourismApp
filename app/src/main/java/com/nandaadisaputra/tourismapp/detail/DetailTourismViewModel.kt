package com.nandaadisaputra.tourismapp.detail

import androidx.lifecycle.ViewModel
import com.nandaadisaputra.tourismapp.core.data.TourismRepository
import com.nandaadisaputra.tourismapp.core.data.source.local.entity.TourismEntity
import com.nandaadisaputra.tourismapp.core.domain.model.Tourism
import com.nandaadisaputra.tourismapp.core.domain.usecase.TourismUseCase


//class DetailTourismViewModel(private val tourismRepository: TourismRepository) : ViewModel() {
//    fun setFavoriteTourism(tourism: TourismEntity, newStatus:Boolean) = tourismRepository.setFavoriteTourism(tourism, newStatus)
//}
//class DetailTourismViewModel(private val tourismRepository: TourismRepository) : ViewModel() {
//    fun setFavoriteTourism(tourism: Tourism, newStatus:Boolean) = tourismRepository.setFavoriteTourism(tourism, newStatus)
//}
class DetailTourismViewModel(private val tourismUseCase: TourismUseCase) : ViewModel() {
    fun setFavoriteTourism(tourism: Tourism, newStatus:Boolean) =
        tourismUseCase.setFavoriteTourism(tourism, newStatus)
}