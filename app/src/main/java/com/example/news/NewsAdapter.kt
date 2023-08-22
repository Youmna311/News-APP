package com.example.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.model.ArticlesItem

class NewsAdapter(var items: List<ArticlesItem?>?) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.source_title)
        val author: TextView = itemView.findViewById(R.id.source_auth)
        val datetime: TextView = itemView.findViewById(R.id.source_datetime)
        val image: ImageView = itemView.findViewById(R.id.soucre_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.source_item_view, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.title.setText(item?.title)
        holder.author.setText(item?.author)
        holder.datetime.setText(item?.publishedAt)

        Glide.with(holder.itemView)
            .load(item?.urlToImage). // urltoimage da el url elly gay mn el api
            into(holder.image) // holder.image da el view elly hythar feh el image
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    fun changeData(articles: List<ArticlesItem?>?) {
        items = articles
        notifyDataSetChanged()

    }
}