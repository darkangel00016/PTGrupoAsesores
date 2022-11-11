package com.lerny.ptgrupoasesores.screens.main.data.api

import com.lerny.ptgrupoasesores.screens.main.data.api.responses.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

interface ProductApi {

    @GET("search")
    suspend fun search(@HeaderMap headerMap: HashMap<String, String>, @Query("query") query: String, @Query("page") page: Int): Response<SearchResponse>

}