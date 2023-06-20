package br.com.dw.books.data.model
import com.google.gson.annotations.SerializedName


data class BooksResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val books: List<Books>?
)

data class Books(
    @SerializedName("authors")
    val authors: List<Author>?,
    @SerializedName("bookshelves")
    val bookshelves: List<String>?,
    @SerializedName("copyright")
    val copyright: Boolean?,
    @SerializedName("download_count")
    val downloadCount: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("languages")
    val languages: List<String>?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("subjects")
    val subjects: List<String>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("translators")
    val translators: List<Translator>?
)

data class Author(
    @SerializedName("birth_year")
    val birthYear: Int?,
    @SerializedName("death_year")
    val deathYear: Int?,
    @SerializedName("name")
    val name: String?
)

data class Translator(
    @SerializedName("birth_year")
    val birthYear: Int?,
    @SerializedName("death_year")
    val deathYear: Int?,
    @SerializedName("name")
    val name: String?
)