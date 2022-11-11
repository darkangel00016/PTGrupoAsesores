package com.lerny.ptgrupoasesores.screens.main.data.repositories

import com.lerny.ptgrupoasesores.screens.main.data.api.ProductApi
import com.lerny.ptgrupoasesores.screens.main.data.api.responses.SearchResponseState
import com.lerny.ptgrupoasesores.settings.GeneralSettings
import retrofit2.Retrofit
import retrofit2.create
import java.net.SocketTimeoutException

class ProductRepository(private val client: Retrofit) {

    suspend fun search(query: String, page: Int = 1): SearchResponseState {
        var responseState: SearchResponseState = SearchResponseState.Error()
        val api = client.create<ProductApi>()
        try {
            val response = api.search(GeneralSettings.getDefaultHeaders(), query, page)
            val body = response.body()
            if (body != null) {
                if (body.status == "PRODUCT_FOUND_RESPONSE") {
                    responseState = SearchResponseState.Success(
                        body.item.props.pageProps.initialData.searchResult.itemStacks[0].items,
                        page
                    )
                } else {
                    responseState = SearchResponseState.Error(body.message)
                }
            }
        } catch (e: SocketTimeoutException) {
            responseState = SearchResponseState.Error("Error en el tiempo de ajecucion.")
        }catch (e: Exception) {
            responseState = SearchResponseState.Error(e.toString())
        }
        return responseState
    }

}