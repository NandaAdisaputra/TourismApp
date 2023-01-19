package com.nandaadisaputra.tourismapp.favorite

import androidx.lifecycle.ViewModel
import com.nandaadisaputra.tourismapp.core.data.TourismRepository

class FavoriteViewModel(tourismRepository: TourismRepository) : ViewModel() {

    val favoriteTourism = tourismRepository.getFavoriteTourism()

}