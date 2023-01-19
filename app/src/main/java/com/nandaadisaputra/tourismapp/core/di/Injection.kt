package com.nandaadisaputra.tourismapp.core.di

import android.content.Context
import com.nandaadisaputra.tourismapp.core.data.TourismRepository
import com.nandaadisaputra.tourismapp.core.data.source.local.LocalDataSource
import com.nandaadisaputra.tourismapp.core.data.source.local.room.TourismDatabase
import com.nandaadisaputra.tourismapp.core.data.source.remote.RemoteDataSource
import com.nandaadisaputra.tourismapp.core.data.source.remote.network.ApiConfig
import com.nandaadisaputra.tourismapp.core.domain.repository.ITourismRepository
import com.nandaadisaputra.tourismapp.core.domain.usecase.TourismInteractor
import com.nandaadisaputra.tourismapp.core.domain.usecase.TourismUseCase
import com.nandaadisaputra.tourismapp.core.utils.AppExecutors
import com.nandaadisaputra.tourismapp.core.utils.JsonHelper

object Injection {
    //    fun provideRepository(context: Context): TourismRepository {
    private fun provideRepository(context: Context): ITourismRepository {
        val database = TourismDatabase.getInstance(context)

//        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.tourismDao())
        val appExecutors = AppExecutors()

        return TourismRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideTourismUseCase(context: Context): TourismUseCase {
        val repository = provideRepository(context)
        return TourismInteractor(repository)
    }
}