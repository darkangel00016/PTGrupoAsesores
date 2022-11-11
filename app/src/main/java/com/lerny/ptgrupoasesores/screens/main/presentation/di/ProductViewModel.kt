package com.lerny.ptgrupoasesores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lerny.ptgrupoasesores.screens.main.data.api.responses.SearchResponseState
import com.lerny.ptgrupoasesores.screens.main.data.repositories.ProductRepository
import com.lerny.ptgrupoasesores.screens.main.presentation.di.ProductState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository): ViewModel() {

    private val _uiState = MutableStateFlow<ProductState>(ProductState.Success(emptyList(), 1))
    val uiState: StateFlow<ProductState>  = _uiState.asStateFlow()

    fun search(query: String) {
        if (query.length < 3) {
            return
        }
        if (uiState.value is ProductState.Loading && (uiState.value as ProductState.Loading).value) {
            return
        }
        viewModelScope.launch {
            _uiState.value = ProductState.Loading(true)
            try {
                val response = repository.search(query)
                when (response) {
                    is SearchResponseState.Error -> _uiState.value =
                        ProductState.Error(response.message)
                    is SearchResponseState.Success -> {
                        _uiState.value = ProductState.Success(response.products, response.page)
                    }
                }
            } catch (e: Exception) {
                _uiState.value =
                    ProductState.Error(e.toString())
            }
            _uiState.value = ProductState.Loading(false)
        }
    }

    fun paginate(query: String, page: Int) {
        if (query.length < 3) {
            return
        }
        if (uiState.value is ProductState.Loading && (uiState.value as ProductState.Loading).value) {
            return
        }
        viewModelScope.launch {
            _uiState.value = ProductState.Loading(true)
            try {
                val response = repository.search(query, page)
                when (response) {
                    is SearchResponseState.Error -> _uiState.value =
                        ProductState.Error(response.message)
                    is SearchResponseState.Success -> {
                        _uiState.value = ProductState.Success(response.products, response.page)
                    }
                }
            } catch (e: Exception) {
                _uiState.value =
                    ProductState.Error(e.toString())
            }
            _uiState.value = ProductState.Loading(false)
        }
    }

}

class ProductViewModelFactory(private val repository: ProductRepository): ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        if(modelClass.isAssignableFrom(ProductViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }
}