package com.lerny.ptgrupoasesores

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("name")
    val title: String,
    @SerializedName("priceInfo")
    val price: ProductPriceInfo,
    @SerializedName("image")
    val thumb: String
)

data class ProductPriceInfo (
    @SerializedName("linePrice")
    val price: String
)
