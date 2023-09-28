package com.example.nycschoolportal.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jetbrains.handson.kmm.shared.NYCSchoolSDK
import com.jetbrains.handson.kmm.shared.cache.DatabaseDriverFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var nycSchoolRecyclerView: RecyclerView
    private lateinit var progressBarView: FrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val nycSchoolNameListAdapter = NYCSchoolListAdapter(listOf())
    private val sdk = NYCSchoolSDK(DatabaseDriverFactory(this))
    private val mainScope = MainScope()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            title = "NYC School List"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_launcher) // Replace ic_launcher with your icon
        }
        setContentView(R.layout.activity_main)

        nycSchoolRecyclerView = findViewById(R.id.nycSchoolNameListRv)
        progressBarView = findViewById(R.id.progressBar)
        swipeRefreshLayout = findViewById(R.id.swipeContainer)
        nycSchoolRecyclerView.adapter = nycSchoolNameListAdapter
        nycSchoolRecyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            displayLaunches(true)
        }

        displayLaunches(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayLaunches(needReload: Boolean) {
        progressBarView.isVisible = true
        mainScope.launch {
            kotlin.runCatching {
                sdk.getNYCSchoolNames(needReload)
            }.onSuccess {
                nycSchoolNameListAdapter.launches = it
                nycSchoolNameListAdapter.notifyDataSetChanged()
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            progressBarView.isVisible = false
        }
    }
}
