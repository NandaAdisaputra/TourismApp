package com.nandaadisaputra.tourismapp.home

import androidx.lifecycle.ViewModel
import com.nandaadisaputra.tourismapp.core.data.TourismRepository
import com.nandaadisaputra.tourismapp.core.domain.usecase.TourismUseCase


//class HomeViewModel(tourismRepository: TourismRepository) : ViewModel() {
class HomeViewModel(tourismUseCase: TourismUseCase) : ViewModel() {
    //    val tourism = tourismRepository.getAllTourism()
    val tourism = tourismUseCase.getAllTourism()

}
