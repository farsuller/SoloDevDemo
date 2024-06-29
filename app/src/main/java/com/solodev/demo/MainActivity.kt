package com.solodev.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.solodev.demo.presentation.FruitScreen
import com.solodev.demo.presentation.FruitsViewModel
import com.solodev.demo.ui.theme.SoloDevDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoloDevDemoTheme {
                Surface {
                    val viewmodel = hiltViewModel<FruitsViewModel>()
                    val products = viewmodel.productsPagingFlow.collectAsLazyPagingItems()
                    FruitScreen(products = products)
                }
            }
        }
    }
}