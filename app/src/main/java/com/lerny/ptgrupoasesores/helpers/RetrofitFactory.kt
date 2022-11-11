package com.lerny.ptgrupoasesores.helpers

import com.lerny.ptgrupoasesores.settings.GeneralSettings
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    companion object Factory {
        fun create (): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GeneralSettings.getDomain())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

}