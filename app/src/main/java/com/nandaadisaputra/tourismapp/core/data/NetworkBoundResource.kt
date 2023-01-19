package com.nandaadisaputra.tourismapp.core.data

import com.nandaadisaputra.tourismapp.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

//abstract class NetworkBoundResource<ResultType, RequestType>(
//    private val mExecutors: AppExecutors) {
abstract class NetworkBoundResource<ResultType, RequestType> {

    //    private val result = MediatorLiveData<Resource<ResultType>>()
//    private val result = PublishSubject.create<Resource<ResultType>>()
//    private val mCompositeDisposable = CompositeDisposable()

    //    init {
//        result.value = Resource.Loading(null)
//
//        @Suppress("LeakingThis")
//        val dbSource = loadFromDB()
//
//        result.addSource(dbSource) { data ->
//            result.removeSource(dbSource)
//            if (shouldFetch(data)) {
//                fetchFromNetwork(dbSource)
//            } else {
//                result.addSource(dbSource) { newData ->
//                    result.value = Resource.Success(newData)
//                }
//            }
//        }
//    }
//    init {
//        @Suppress("LeakingThis")
//        val dbSource = loadFromDB()
//        val db = dbSource
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .take(1)
//            .subscribe { value ->
//                dbSource.unsubscribeOn(Schedulers.io())
//                if (shouldFetch(value)) {
//                    fetchFromNetwork()
//                } else {
//                    result.onNext(Resource.Success(value))
//                }
//            }
//        mCompositeDisposable.add(db)
//    }
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    //    protected abstract fun loadFromDB(): LiveData<ResultType>
//    protected abstract fun loadFromDB(): Flowable<ResultType>
    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    //    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
//    protected abstract suspend fun createCall(): Flowable<ApiResponse<RequestType>>
    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)
    fun asFlow(): Flow<Resource<ResultType>> = result
}
    //    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
//    @SuppressLint("CheckResult")
//    private fun fetchFromNetwork() {
//
//        val apiResponse = createCall()

//        result.addSource(dbSource) { newData ->
//            result.value = Resource.Loading(newData)
//        }
//        result.addSource(apiResponse) { response ->
//            result.removeSource(apiResponse)
//            result.removeSource(dbSource)
//            when (response) {
//                is ApiResponse.Success ->
//                    mExecutors.diskIO().execute {
//                        saveCallResult(response.data)
//                        mExecutors.mainThread().execute {
//                            result.addSource(loadFromDB()) { newData ->
//                                result.value = Resource.Success(newData)
//                            }
//                        }
//                    }
//                is ApiResponse.Empty -> mExecutors.mainThread().execute {
//                    result.addSource(loadFromDB()) { newData ->
//                        result.value = Resource.Success(newData)
//                    }
//                }
//                is ApiResponse.Error -> {
//                    onFetchFailed()
//                    result.addSource(dbSource) { newData ->
//                        result.value = Resource.Error(response.errorMessage, newData)
//                    }
//                }
//            }
//        }
//    }
//        result.onNext(Resource.Loading(null))
//        val response = apiResponse
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .take(1)
//            .doOnComplete {
//                mCompositeDisposable.dispose()
//            }
//            .subscribe { response ->
//                when (response) {
//                    is ApiResponse.Success -> {
//                        saveCallResult(response.data)
//                        val dbSource = loadFromDB()
//                        dbSource.subscribeOn(Schedulers.computation())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .take(1)
//                            .subscribe {
//                                dbSource.unsubscribeOn(Schedulers.io())
//                                result.onNext(Resource.Success(it))
//                            }
//                    }
//                    is ApiResponse.Empty -> {
//                        val dbSource = loadFromDB()
//                        dbSource.subscribeOn(Schedulers.computation())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .take(1)
//                            .subscribe {
//                                dbSource.unsubscribeOn(Schedulers.io())
//                                result.onNext(Resource.Success(it))
//                            }
//                    }
//                    is ApiResponse.Error -> {
//                        onFetchFailed()
//                        result.onNext(Resource.Error(response.errorMessage, null))
//                    }
//                }
//            }
//        mCompositeDisposable.add(response)
//    }
//
//    //    fun asLiveData(): LiveData<Resource<ResultType>> = result
//    fun asFlowable(): Flowable<Resource<ResultType>> =
//        result.toFlowable(BackpressureStrategy.BUFFER)
//}