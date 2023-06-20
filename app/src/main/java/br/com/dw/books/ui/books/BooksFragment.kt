package br.com.dw.books.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.dw.books.databinding.FragmentBooksBinding
import br.com.dw.books.ui.books.adapter.BookCallBack
import br.com.dw.books.ui.books.adapter.BooksAdapter
import br.com.dw.books.ui.model.BooksUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BooksFragment : Fragment() {

    private lateinit var binding: FragmentBooksBinding
    private val viewModel: BooksViewModel by viewModels()

    private val adapter: BooksAdapter by lazy {
        BooksAdapter(mutableListOf(), object : BookCallBack {
            override fun onClick(bookId: Int, book: BooksUI) {
                val direction = BooksFragmentDirections.actionBooksFragmentToBookDetailFragment(
                    bookId, book
                )
                findNavController().navigate(direction)
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        endScroll()
        observer()
        viewModel.dispatchViewAction(BooksAction.InitView)
    }

    private fun observer() {
        viewModel.viewState.viewAction.observe(
            viewLifecycleOwner
        ) { action ->
            handlerViewAction(action)
        }
    }

    private fun handlerViewAction(action: BooksState.Action?) {
        when (action) {
            is BooksState.Action.OnSuccess -> {
                binding.rvBooks.adapter = adapter
                addBooks(action.success)
            }

            is BooksState.Action.OnError -> {
            }

            is BooksState.Action.Loading -> {}
            null -> {}
            is BooksState.Action.OnSuccessNextPage -> {
                addMoreBooks(action.success)
            }
        }
    }

    private fun addMoreBooks(success: List<BooksUI>) {
        adapter.addMore(success as MutableList<BooksUI>)
    }

    private fun addBooks(books: List<BooksUI>) {
        adapter.add(books as MutableList<BooksUI>)
    }

    private fun endScroll() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvBooks.layoutManager = layoutManager

        binding.rvBooks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                val loadMoreThreshold = 5

                if (!viewModel.isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + loadMoreThreshold)) {
                    viewModel.dispatchViewAction(BooksAction.NextPage)
                }
            }
        })
    }

}