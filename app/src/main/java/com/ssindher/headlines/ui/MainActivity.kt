package com.ssindher.headlines.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.ssindher.headlines.ui.theme.HeadlinesTheme
import com.ssindher.headlines.util.ConnectionLiveData
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val connectionStatus: ConnectionLiveData by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isNetworkAvailable = connectionStatus.observeAsState().value
            HeadlinesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HeadlinesTheme {
        Greeting("Android")
    }
}