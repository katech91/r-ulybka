package ru.ulybka.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.ulybka.R
import ru.ulybka.viewmodels.DocumentViewModel
import kotlinx.coroutines.flow.collect
import okhttp3.internal.notify
import ru.ulybka.data.local.DocumentItem


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
        adapter = DocumentsAdapter()
        with(rv_documents_list){
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(divider)
        }
    }

    @ExperimentalCoroutinesApi
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DocumentViewModel::class.java)
        getDocumentsList()
        viewModel.documentsList.observe(this, Observer {
            Log.e("TEST222", adapter.currentList.toString())

            adapter.notifyDataSetChanged()
            adapter.submitList(it)
        })
    }

    @ExperimentalCoroutinesApi
    private fun getDocumentsList() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.loadDocumentsDetails()
        }
    }
}