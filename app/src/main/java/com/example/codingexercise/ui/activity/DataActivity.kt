package com.example.codingexercise.ui.activity


import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codingexercise.R
import com.example.codingexercise.api.DataRowsList
import com.example.codingexercise.ui.adapter.DataAdapter
import com.example.codingexercise.utils.Status
import com.example.codingexercise.viewmodel.DataViewModel
import com.example.codingexercise.viewmodel.DataViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class DataActivity : AppCompatActivity() {

    @Inject
    lateinit var dataViewModelFactory: DataViewModelFactory
    private var dataAdapter = DataAdapter(ArrayList(),this)
    private lateinit var dataViewModel: DataViewModel

    lateinit var dataListItems : List<DataRowsList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        dataListItems = ArrayList()
        initializeRecycler()

        dataViewModel = ViewModelProviders.of(this, dataViewModelFactory).get(
            DataViewModel::class.java)

        progressBar.visibility = View.VISIBLE
        dataViewModel?.fetch.value = true
        dataViewModel?.dataResult?.observe(this, Observer {
            if ( it.status == Status.SUCCESS){
                dataListItems = it!!.data!!.rows
                supportActionBar!!.title= it!!.data!!.title
                dataAdapter.addData(it!!.data!!.rows)
                recycler.adapter = dataAdapter
                progressBar.visibility = View.GONE
            }
        })

        itemsrefresh.setOnRefreshListener {
            dataAdapter.addData(dataListItems)
            recycler.adapter = dataAdapter
            itemsrefresh.isRefreshing = false
        }

    }

    private fun initializeRecycler() {
        val gridLayoutManager = GridLayoutManager(this, 1)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
        }
    }


    override fun onDestroy() {
        dataViewModel.disposeElements()
        super.onDestroy()
    }
}