package com.lerny.ptgrupoasesores.screens.main.data.api.responses

import com.google.gson.annotations.SerializedName
import com.lerny.ptgrupoasesores.Product

data class SearchResponse (
    @SerializedName("responseStatus")
    var status: String,
    @SerializedName("responseMessage")
    var message: String,
    var item: SearchResponseItem
)

data class SearchResponseItem (
    var props: SearchResponseItemProp
)

data class SearchResponseItemProp (
    var pageProps: SearchResponseItemPropPage
)

data class SearchResponseItemPropPage (
    var initialData: SearchResponseItemPropPageInitialData
)

data class SearchResponseItemPropPageInitialData (
    var searchResult: SearchResponseItemPropPageInitialDataResult
)

data class SearchResponseItemPropPageInitialDataResult (
    var itemStacks: List<SearchResponseItemPropPageInitialDataResultStack>
)

data class SearchResponseItemPropPageInitialDataResultStack (
    var items: List<Product>
)