package com.nandaadisaputra.tourismapp.favorite

import androidx.lifecycle.ViewModel
import com.nandaadisaputra.tourismapp.core.data.TourismRepository
import com.nandaadisaputra.tourismapp.core.domain.usecase.TourismUseCase

//class FavoriteViewModel(tourismRepository: TourismRepository) : ViewModel() {
class FavoriteViewModel(tourismUseCase: TourismUseCase) : ViewModel() {
//    val favoriteTourism = tourismRepository.getFavoriteTourism()
val favoriteTourism = tourismUseCase.getFavoriteTourism()
}