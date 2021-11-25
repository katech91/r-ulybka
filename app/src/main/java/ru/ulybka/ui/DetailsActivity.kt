package ru.ulybka.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.ulybka.R
import ru.ulybka.viewmodels.DocumentViewModel

class DetailsActivity: AppCompatActivity() {

    private lateinit var adapter: DetailsAdapter
    private var searchJob: Job? = null
    private lateinit var viewModel: DocumentViewModel
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("DetailsActivity", "onCreate")
        setContentView(R.layout.activity_details)
        id = intent.getIntExtra("id", 1)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        val rv_document_details = findViewById<RecyclerView>(R.id.rv_document_details)
        adapter = DetailsAdapter()
        with(rv_document_details){
            adapter = this@DetailsActivity.adapter
            layoutManager = LinearLayoutManager(this@DetailsActivity)
            addItemDecoration(divider)
        }
    }

    @ExperimentalCoroutinesApi
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DocumentViewModel::class.java)
        id?.let { getDocument(it) }
        viewModel.document.observe(this, Observer {
            Log.e("DetailsActivity: initVM", adapter.currentList.toString())
            adapter.notifyDataSetChanged()
            adapter.submitList(it.positions)
        })
    }

    @ExperimentalCoroutinesApi
    private fun getDocument(_id: Int) {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                viewModel.handleLoadDocumentById(_id)
            }

    }
}