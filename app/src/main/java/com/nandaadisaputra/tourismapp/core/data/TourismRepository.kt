package com.nandaadisaputra.tourismapp.core.data

import com.nandaadisaputra.tourismapp.core.data.source.local.LocalDataSource
import com.nandaadisaputra.tourismapp.core.data.source.remote.RemoteDataSource
import com.nandaadisaputra.tourismapp.core.data.source.remote.network.ApiResponse
import com.nandaadisaputra.tourismapp.core.data.source.remote.response.TourismResponse
import com.nandaadisaputra.tourismapp.core.domain.model.Tourism
import com.nandaadisaputra.tourismapp.core.domain.repository.ITourismRepository
import com.nandaadisaputra.tourismapp.core.utils.AppExecutors
import com.nandaadisaputra.tourismapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
//    override fun getAllTourism(): LiveData<Resource<List<Tourism>>> =
//        object : NetworkBoundResource<List<Tourism>, List<TourismResponse>>(appExecutors) {
//            override fun loadFromDB(): LiveData<List<Tourism>> {
//                return Transformations.map(localDataSource.getAllTourism()) {
//                    DataMapper.mapEntitiesToDomain(it)
//                }
//            }
//    override fun getAllTourism(): Flowable<Resource<List<Tourism>>> =
//        object : NetworkBoundResource<List<Tourism>, List<TourismResponse>>(appExecutors) {
//            override fun loadFromDB(): Flowable<List<Tourism>> {
//                return localDataSource.getAllTourism().map { DataMapper.mapEntitiesToDomain(it) }
//            }
    override fun getAllTourism(): Flow<Resource<List<Tourism>>> =
        object : NetworkBoundResource<List<Tourism>, List<TourismResponse>>() {
            override fun loadFromDB(): Flow<List<Tourism>> {
                return localDataSource.getAllTourism().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            //            override fun shouldFetch(data: Single<List<Tourism>>): Boolean =
            override fun shouldFetch(data: List<Tourism>?): Boolean =
//                data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            //            override fun createCall(): LiveData<ApiResponse<List<TourismResponse>>> =
//                remoteDataSource.getAllTourism()
//            override fun createCall(): Flowable<ApiResponse<List<TourismResponse>>> =
//                remoteDataSource.getAllTourism()
            override suspend fun createCall(): Flow<ApiResponse<List<TourismResponse>>> =
                remoteDataSource.getAllTourism()

            //
//            override fun saveCallResult(data: List<TourismResponse>) {
//                val tourismList = DataMapper.mapResponsesToEntities(data)
//                localDataSource.insertTourism(tourismList)
//            }
//        }.asLiveData()
//                localDataSource.insertTourism(tourismList)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe()
//            }
//        }.asFlowable()
            override suspend fun saveCallResult(data: List<TourismResponse>) {
                val tourismList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertTourism(tourismList)
            }
        }.asFlow()

    //    fun getFavoriteTourism(): LiveData<List<TourismEntity>> {
//        return localDataSource.getFavoriteTourism()
//    }
//    override fun getFavoriteTourism(): LiveData<List<Tourism>> {
//        return Transformations.map(localDataSource.getFavoriteTourism()) {
//            DataMapper.mapEntitiesToDomain(it)
//        }
//    }
//    override fun getFavoriteTourism(): Flowable<List<Tourism>> {
//        return localDataSource.getFavoriteTourism().map { DataMapper.mapEntitiesToDomain(it) }
//    }
    override fun getFavoriteTourism(): Flow<List<Tourism>> {
        return localDataSource.getFavoriteTourism().map { DataMapper.mapEntitiesToDomain(it) }

    }
    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTourism(tourismEntity, state) }
    }
    //    fun setFavoriteTourism(tourism: TourismEntity, state: Boolean) {
//        appExecutors.diskIO().execute { localDataSource.setFavoriteTourism(tourism, state) }
//    }
//    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) {
//        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
//        appExecutors.diskIO().execute { localDataSource.setFavoriteTourism(tourismEntity, state) }
//    }
}