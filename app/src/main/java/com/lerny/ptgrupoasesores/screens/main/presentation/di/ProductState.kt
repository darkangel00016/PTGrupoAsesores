package com.lerny.ptgrupoasesores.screens.main.presentation.di

import com.lerny.ptgrupoasesores.Product

sealed class ProductState {

    open var value: Boolean = false
    open var message: String? = null
    open var page: Int = 1
    open var products: List<Product> = listOf()

    data class Loading(override var value: Boolean): ProductState()
    data class Error(override var message: String?): ProductState()
    data class Success(override var products: List<Product> = listOf(), override var page: Int): ProductState()
}
