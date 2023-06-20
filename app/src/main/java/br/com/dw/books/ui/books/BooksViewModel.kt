package br.com.dw.books.ui.books

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.dw.books.base.BaseViewModel
import br.com.dw.books.data.BooksRepository
import br.com.dw.books.data.model.Author
import br.com.dw.books.data.model.Translator
import br.com.dw.books.ui.model.AuthorUI
import br.com.dw.books.ui.model.BooksUI
import br.com.dw.books.ui.model.TranslatorUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val repository: BooksRepository) :
    BaseViewModel<BooksState, BooksAction>() {

    override val viewState = BooksState()

    private var page = "1"
    var isLoading = false

    override fun dispatchViewAction(viewAction: BooksAction) {
        when (viewAction) {
            BooksAction.InitView -> {
                initView()
            }

            BooksAction.NextPage -> { nextPage()}
        }
    }

    private fun initView() {
        viewModelScope.launch {
            isLoading = true
            repository.getBooks(page)
                .flowOn(Dispatchers.IO)
                .catch {
                    isLoading = false
                    viewState.viewAction.postValue(BooksState.Action.OnError)
                }
                .map {
                    page = it.next?.takeLast(1) ?: "0"
                    it.books?.map { it2 ->
                        BooksUI(
                            id = 0,
                            subjects = it2.subjects ?: listOf(),
                            title = it2.title ?: "",
                            translatorUIS = getTranslator(it2.translators),
                            authors = getAuthors(it2.authors)
                        )
                    }
                }
                .collect {
                    isLoading = false
                    val book: List<BooksUI>? = it
                    viewState.viewAction.postValue(
                        book?.let { it1 -> BooksState.Action.OnSuccess(it1) }
                    )
                }
        }
    }

    private fun nextPage() {
        viewModelScope.launch {
            isLoading = true
            repository.getBooks(page)
                .flowOn(Dispatchers.IO)
                .catch {
                    isLoading = false
                    viewState.viewAction.postValue(BooksState.Action.OnError)
                }
                .map {
                    page = it.next?.takeLast(1) ?: "0"
                    it.books?.map { it2 ->
                        BooksUI(
                            id = 0,
                            subjects = it2.subjects ?: listOf(),
                            title = it2.title ?: "",
                            translatorUIS = getTranslator(it2.translators),
                            authors = getAuthors(it2.authors)
                        )
                    }
                }
                .collect {
                    isLoading = false
                    val book: List<BooksUI>? = it
                    viewState.viewAction.postValue(
                        book?.let { it1 -> BooksState.Action.OnSuccessNextPage(it1) }
                    )
                }
        }
    }

    private fun getAuthors(authors: List<Author>?): List<AuthorUI> {
        val authorUI = authors?.map { author ->
            AuthorUI(
                birthYear = author.birthYear ?: 0,
                deathYear = author.deathYear ?: 0 ,
                name = author.name ?: ""
            )
        }
        return authorUI ?: listOf()
    }

    private fun getTranslator(translators: List<Translator>?): List<TranslatorUI> {
        val translatorUIList = translators?.map { translator ->
            TranslatorUI(
                birthYear = translator.birthYear ?: 0,
                deathYear = translator.deathYear ?: 0 ,
                name = translator.name ?: ""
            )
        }
        return translatorUIList ?: listOf()
    }

}

sealed class BooksAction {
    object InitView : BooksAction()
    object NextPage : BooksAction()
}

class BooksState {
    val viewAction: MutableLiveData<Action> = MutableLiveData()

    sealed class Action {
        data class OnSuccess(val success: List<BooksUI>) : Action()
        data class OnSuccessNextPage(val success: List<BooksUI>) : Action()
        object OnError : Action()
        data class Loading(val isLoading: Boolean) : Action()
    }
}