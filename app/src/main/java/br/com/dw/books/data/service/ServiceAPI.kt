package br.com.dw.books.data.service

import br.com.dw.books.data.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {

    @GET("/books/")
    suspend fun getBooks(
        @Query("page")page: String
    ): BooksResponse

}