package com.amalhanaja.movietrek.core.data.di

import com.amalhanaja.movietrek.core.data.repository.DataRepository
import com.amalhanaja.movietrek.core.data.repository.DataRepositoryImpl
import com.amalhanaja.movietrek.core.tmdb.TmdbClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @CoroutineContextIO
    fun provideContextIo(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    fun proveTmdbClient(@CoroutineContextIO coroutineContext: CoroutineContext): TmdbClient {
        return TmdbClient(coroutineContext, "API_KEY")
    }

    @Provides
    fun provideDataRepository(tmdbClient: TmdbClient): DataRepository {
        return DataRepositoryImpl(tmdbClient)
    }
}