package br.com.dw.books.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BooksUI(
    val authors: List<AuthorUI>?,
    var id: Int?,
    var subjects: List<String>?,
    var title: String?,
    var translatorUIS: List<TranslatorUI>,
) : Parcelable

@Parcelize
data class AuthorUI(
    var birthYear: Int?,
    var deathYear: Int?,
    var name: String?
) : Parcelable

//data class Formats(
//    var application/epub+zip: String?,
//    var application/octet-stream: String?,
//    var application/rdf+xml: String?,
//    var application/x-mobipocket-ebook: String?,
//    var image/jpeg: String?,
//    var text/html: String?,
//    var text/html; charset=iso-8859-1: String?,
//    var text/html; charset=utf-8: String?,
//    var text/plain: String?,
//    var text/plain; charset=iso-8859-1: String?,
//    var text/plain; charset=us-ascii: String?,
//    var text/plain; charset=utf-8: String?
//)


@Parcelize
data class TranslatorUI(
    var birthYear: Int?,
    var deathYear: Int?,
    var name: String?
) : Parcelable