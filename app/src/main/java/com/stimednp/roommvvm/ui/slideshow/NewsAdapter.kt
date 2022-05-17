package com.stimednp.roommvvm.ui.slideshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stimednp.roommvvm.data.db.entity.Article
import com.stimednp.roommvvm.databinding.AdapterNewsItemBinding
import com.stimednp.roommvvm.utils.UtilFunctions.loadImageFromGlide
import javax.inject.Inject

class NewsAdapter @Inject constructor() : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: AdapterNewsItemBinding) :RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {

            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            ivArticle.loadImageFromGlide(article.urlToImage)
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvSource.text = article.author
            tvPublished.text = article.publishedAt

        }

        holder.itemView.setOnClickListener {
            setArticleClickListener?.let {
                it(article)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var setArticleClickListener : ((article: Article)->Unit)? =null

    fun onArticleClicked(listener:(Article)->Unit){
        setArticleClickListener =listener
    }
}