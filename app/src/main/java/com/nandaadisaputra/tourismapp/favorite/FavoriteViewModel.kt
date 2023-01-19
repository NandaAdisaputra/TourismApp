package com.nandaadisaputra.tourismapp.favorite

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nandaadisaputra.tourismapp.core.data.TourismRepository
import com.nandaadisaputra.tourismapp.core.domain.usecase.TourismUseCase

//class FavoriteViewModel(tourismRepository: TourismRepository) : ViewModel() {
class FavoriteViewModel(tourismUseCase: TourismUseCase) : ViewModel() {
//    val favoriteTourism = tourismRepository.getFavoriteTourism()
//val favoriteTourism = tourismUseCase.getFavoriteTourism()
//val favoriteTourism = LiveDataReactiveStreams.fromPublisher(tourismUseCase.getFavoriteTourism())
val favoriteTourism = tourismUseCase.getFavoriteTourism().asLiveData()
}