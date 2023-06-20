package br.com.dw.books.data

import br.com.dw.books.data.model.BooksResponse
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun getBooks(nextPage: String): Flow<BooksResponse>
}