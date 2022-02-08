package com.ssindher.headlines.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.rememberNavController
import com.ssindher.headlines.navigation.SetupNavGraph
import com.ssindher.headlines.ui.home.HomeViewModel
import com.ssindher.headlines.util.ConnectionLiveData
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var isInitialised = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val connectionStatus: ConnectionLiveData = get()
            val isNetworkAvailable = connectionStatus.observeAsState(false).value
            if (isNetworkAvailable) {
                isInitialised = true
                homeViewModel.fetchNewsFromApi()
            } else {
                if (!isInitialised) {
                    homeViewModel.fetchNewsFromDB()
                }
            }
            val navController = rememberNavController()
            SetupNavGraph(navController = navController, isNetworkAvailable, homeViewModel)
        }
    }
}