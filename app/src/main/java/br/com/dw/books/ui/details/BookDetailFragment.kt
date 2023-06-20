package br.com.dw.books.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.dw.books.databinding.FragmentBookDetailsBinding
import br.com.dw.books.ui.model.AuthorUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : Fragment() {

    private lateinit var binding: FragmentBookDetailsBinding

    private val args: BookDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detail = args.book
        binding.bookDetailTitle.text = detail?.title
        binding.bookAuthorDetail.text = getAuthors(detail?.authors)
        binding.bookSubjectDetail.text = detail?.subjects?.first()
    }

    private fun getAuthors(authors: List<AuthorUI>?): StringBuilder {
        var author = StringBuilder()
        authors?.forEachIndexed { index, authorUI ->
            author.append("${authorUI.name}")
            if (authorUI.birthYear != 0 && authorUI.deathYear != 0)
                author.append(" ${authorUI.birthYear} - ${authorUI.deathYear}")
            if (index < authors.size && authors.size > 1)
                author.append("\n")
        }

        return author
    }

}