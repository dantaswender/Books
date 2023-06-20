package br.com.dw.books.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel<VS, VA> : ViewModel() {
    abstract val viewState: VS

    abstract fun dispatchViewAction(viewAction: VA)

    protected fun executeCoutines(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        trach: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                trach.invoke()
            } catch (ex: Exception){
                Log.DEBUG
            }
        }
    }
}