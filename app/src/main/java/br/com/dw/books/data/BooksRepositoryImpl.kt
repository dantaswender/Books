package br.com.dw.books.data

import br.com.dw.books.data.model.BooksResponse
import br.com.dw.books.data.service.ServiceAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(val serviceAPI: ServiceAPI) : BooksRepository {
    override suspend fun getBooks(nextPage: String): Flow<BooksResponse> {
        return flow {
            emit(serviceAPI.getBooks(nextPage))
        }
    }
}