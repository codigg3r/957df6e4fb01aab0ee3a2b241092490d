package me.sukru.carpettravel.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.sukru.carpettravel.common.Constants
import me.sukru.carpettravel.data.local.CarpetTravelDao
import me.sukru.carpettravel.data.local.CarpetTravelDatabase
import me.sukru.carpettravel.data.remote.SpaceStationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSpaceStationApi(): SpaceStationApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpaceStationApi::class.java)
    }
    @Provides
    @Singleton
    fun provideCarpetTravelDatabase(context: Application): CarpetTravelDatabase {
        return Room.databaseBuilder(
            context,
            CarpetTravelDatabase::class.java, "carpet_travel_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCarpetTravelDao(database: CarpetTravelDatabase): CarpetTravelDao {
        return database.carpetTravelDao()
    }
}