package ru.ulybka.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: DocumentsAdapter
    private var searchJob: Job? = null
    private lateinit var viewModel: DocumentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        initViewModel()
    }

    private fun initViews() {
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        val rv_documents_list = findViewById<RecyclerView>(R.id.rv_documents_list)
        adapter = DocumentsAdapter { document ->
            Log.d("MainActivity", "initViews")
            val intent = Intent(this@MainActivity.applicationContext, DetailsActivity::class.java)
                .apply { putExtra("id", document.id) }
            this@MainActivity.startActivity(intent)
        }
        with(rv_documents_list){
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(divider)
        }
    }

    @ExperimentalCoroutinesApi
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DocumentViewModel::class.java)
        loadDocuments()
        viewModel.documentsList.observe(this, Observer {
            adapter.notifyDataSetChanged()
            adapter.submitList(it)
        })
    }

    @ExperimentalCoroutinesApi
    private fun loadDocuments() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.loadDocumentsDetails()
        }
    }
}