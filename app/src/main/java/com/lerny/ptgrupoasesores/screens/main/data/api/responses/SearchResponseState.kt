package com.lerny.ptgrupoasesores.screens.main.data.api.responses

import com.lerny.ptgrupoasesores.Product

sealed class SearchResponseState {

    data class Error(val message: String? = null): SearchResponseState()
    data class Success(val products: List<Product>, val page: Int): SearchResponseState()

}