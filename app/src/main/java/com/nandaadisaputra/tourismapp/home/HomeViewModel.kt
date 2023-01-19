package com.nandaadisaputra.tourismapp.home

import androidx.lifecycle.ViewModel
import com.nandaadisaputra.tourismapp.core.data.TourismRepository

class HomeViewModel(tourismRepository: TourismRepository) : ViewModel() {

    val tourism = tourismRepository.getAllTourism()

}
