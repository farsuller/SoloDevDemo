package com.solodev.demo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.solodev.demo.data.local.FruitEntity
import com.solodev.demo.data.mappers.toFruit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FruitsViewModel @Inject constructor(
    pager: Pager<Int, FruitEntity>
) : ViewModel() {

    val fruitsPagingFlow = pager.flow.map { pagingData ->
        pagingData.map { it.toFruit() }
    }.cachedIn(viewModelScope)

}