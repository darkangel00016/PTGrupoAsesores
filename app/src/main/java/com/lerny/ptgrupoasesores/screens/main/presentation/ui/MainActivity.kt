package com.lerny.ptgrupoasesores.screens.main.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.lerny.ptgrupoasesores.*
import com.lerny.ptgrupoasesores.databinding.ActivityMainBinding
import com.lerny.ptgrupoasesores.helpers.DatabaseHelper
import com.lerny.ptgrupoasesores.helpers.RetrofitFactory
import com.lerny.ptgrupoasesores.screens.main.presentation.adapters.ProductListAdapter
import com.lerny.ptgrupoasesores.screens.main.data.entities.Term
import com.lerny.ptgrupoasesores.screens.main.data.repositories.ProductRepository
import com.lerny.ptgrupoasesores.screens.main.presentation.di.ProductState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels { ProductViewModelFactory(
        ProductRepository(RetrofitFactory.create())
    ) }
    lateinit var listAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initViews()
        initObservers()
    }

    private fun initViews() {
        listAdapter = ProductListAdapter(arrayListOf())
        binding.searchInput.setOnQueryTextListener(this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.productList.layoutManager = layoutManager
        binding.productList.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        binding.productList.adapter = listAdapter
        initScrollListener()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is ProductState.Loading -> updateLoading(uiState.value)
                        is ProductState.Error -> handleError(uiState.message)
                        is ProductState.Success -> updateProducts(uiState.products, uiState.page)
                    }
                }
            }
        }
    }

    private fun initScrollListener() {
        binding.productList.addOnScrollListener(object: OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == listAdapter.itemCount - 1) {
                    loadMore()
                }
            }
        })
    }

    private fun loadMore() {
        if (viewModel.uiState.value.value) {
            return
        }
        viewModel.paginate(binding.searchInput.query.toString(), viewModel.uiState.value.page + 1)
    }

    private fun updateLoading(value: Boolean) {
        binding.searchProgress.visibility = if(value) View.VISIBLE else View.GONE
    }

    private fun handleError(value: String?) {
        updateLoading(false)
        if (value != null) {
            Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateProducts(products: List<Product>, page: Int) {
        updateLoading(false)
        lifecycleScope.launch {
            insertSearchTerm()
        }
        binding.message.visibility = if (products.isEmpty()) View.VISIBLE else View.GONE
        if (page == 1) {
            listAdapter.updateItems(products)
        } else {
            listAdapter.addItems(products)
        }
        binding.productList.visibility = View.VISIBLE
    }

    private suspend fun insertSearchTerm() {
        val termString: String = binding.searchInput.query.toString()
        val termDao = DatabaseHelper.getDatabase(this).termDao()
        val term = termDao.findByName(termString)
        if (term == null) {
            termDao.insertAll(Term(null, termString))
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            viewModel.search(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}