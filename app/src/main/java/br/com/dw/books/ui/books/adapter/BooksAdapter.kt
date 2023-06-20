package br.com.dw.books.ui.books.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.dw.books.databinding.ItemBookBinding
import br.com.dw.books.ui.model.BooksUI

class BooksAdapter(
    private var list: MutableList<BooksUI>,
    private val callBack: BookCallBack
) : RecyclerView.Adapter<BooksAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position], callBack)
    }

    fun add(book: MutableList<BooksUI>) {
        this.list.addAll(book)
        this.notifyDataSetChanged()
    }

    fun addMore(book: MutableList<BooksUI>) {
        this.list.addAll(book)
        this.notifyItemRangeChanged(this.list.size, book.size)
    }

    class ListViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BooksUI?, callBack: BookCallBack) {
            binding.bookTitle.text = item?.title
            item?.authors?.forEach {
                binding.bookAuthor.text = "${it?.name} "
            }

            binding.root.setOnClickListener {
                item?.id?.let { it1 -> callBack.onClick(
                    it1, item
                ) }
            }
        }
    }
}

interface BookCallBack{
    fun onClick(bookId: Int, item: BooksUI)
}