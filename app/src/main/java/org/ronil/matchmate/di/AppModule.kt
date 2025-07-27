package org.ronil.matchmate.di

import androidx.room.RoomDatabase
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.ronil.matchmate.room.CreateDatabase
import org.ronil.matchmate.room.MyRoomDatabase
import org.ronil.matchmate.room.MyRoomDatabaseDao
import org.ronil.matchmate.room.getAndroidPreferencesPath
import org.ronil.matchmate.network.RestApis
import org.ronil.matchmate.network.getRestApis
import org.ronil.matchmate.network.provideOkHttpClient
import org.ronil.matchmate.network.providesRetrofit
import org.ronil.matchmate.repository.RoomRepository
import org.ronil.matchmate.repository.NetworkRepository
import org.ronil.matchmate.utils.NetworkConnectivityManager
import org.ronil.matchmate.utils.PreferenceManager
import retrofit2.Retrofit



val AppModule = module {
    single { getAndroidPreferencesPath(get()) }
    single<MyRoomDatabase> { CreateDatabase(get()).getDatabase() }
    single<MyRoomDatabaseDao>() { get<MyRoomDatabase>().myRoomDatabaseDao }
    singleOf(::RoomRepository)
    singleOf(::NetworkConnectivityManager)
    singleOf(::NetworkRepository)
    singleOf(::PreferenceManager)
    single<OkHttpClient>{ provideOkHttpClient() }
    single<Retrofit>{ providesRetrofit(get()) }
    single<RestApis>{ getRestApis(get()) }


}





