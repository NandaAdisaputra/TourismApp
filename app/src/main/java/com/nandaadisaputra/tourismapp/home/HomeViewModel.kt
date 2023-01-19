package com.nandaadisaputra.tourismapp.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nandaadisaputra.tourismapp.core.data.TourismRepository
import com.nandaadisaputra.tourismapp.core.domain.usecase.TourismUseCase


//class HomeViewModel(tourismRepository: TourismRepository) : ViewModel() {
class HomeViewModel(tourismUseCase: TourismUseCase) : ViewModel() {
    //    val tourism = tourismRepository.getAllTourism()
//    val tourism = tourismUseCase.getAllTourism()
//    val tourism = LiveDataReactiveStreams.fromPublisher(tourismUseCase.getAllTourism())
    val tourism = tourismUseCase.getAllTourism().asLiveData()
}
