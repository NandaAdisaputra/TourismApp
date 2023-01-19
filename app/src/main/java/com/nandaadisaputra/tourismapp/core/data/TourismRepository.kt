package com.nandaadisaputra.tourismapp.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.nandaadisaputra.tourismapp.core.data.source.local.LocalDataSource
import com.nandaadisaputra.tourismapp.core.data.source.remote.RemoteDataSource
import com.nandaadisaputra.tourismapp.core.data.source.remote.network.ApiResponse
import com.nandaadisaputra.tourismapp.core.data.source.remote.response.TourismResponse
import com.nandaadisaputra.tourismapp.core.domain.model.Tourism
import com.nandaadisaputra.tourismapp.core.domain.repository.ITourismRepository
import com.nandaadisaputra.tourismapp.core.utils.AppExecutors
import com.nandaadisaputra.tourismapp.core.utils.DataMapper

class TourismRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
//) {
) : ITourismRepository {

    companion object {
        @Volatile
        private var instance: TourismRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): TourismRepository =
            instance ?: synchronized(this) {
                instance ?: TourismRepository(remoteData, localData, appExecutors)
            }
    }


    //    fun getAllTourism(): LiveData<Resource<List<TourismEntity>>> =
//        object : NetworkBoundResource<List<TourismEntity>, List<TourismResponse>>(appExecutors) {
//            override fun loadFromDB(): LiveData<List<TourismEntity>> {
//                return localDataSource.getAllTourism()
//            }
//
//            override fun shouldFetch(data: List<TourismEntity>?): Boolean =
//                data == null || data.isEmpty()
    override fun getAllTourism(): LiveData<Resource<List<Tourism>>> =
        object : NetworkBoundResource<List<Tourism>, List<TourismResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Tourism>> {
                return Transformations.map(localDataSource.getAllTourism()) {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Tourism>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TourismResponse>>> =
                remoteDataSource.getAllTourism()

            override fun saveCallResult(data: List<TourismResponse>) {
                val tourismList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertTourism(tourismList)
            }
        }.asLiveData()

    //    fun getFavoriteTourism(): LiveData<List<TourismEntity>> {
//        return localDataSource.getFavoriteTourism()
//    }
    override fun getFavoriteTourism(): LiveData<List<Tourism>> {
        return Transformations.map(localDataSource.getFavoriteTourism()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    //    fun setFavoriteTourism(tourism: TourismEntity, state: Boolean) {
//        appExecutors.diskIO().execute { localDataSource.setFavoriteTourism(tourism, state) }
//    }
    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTourism(tourismEntity, state) }
    }
}