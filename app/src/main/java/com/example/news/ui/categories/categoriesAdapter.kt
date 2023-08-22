package com.example.news.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.news.NewsAdapter
import com.example.news.R
import com.example.news.model.Category
import com.google.android.material.card.MaterialCardView

class categoriesAdapter(val categorieslist: List<Category>) :
    RecyclerView.Adapter<categoriesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categorytitle: TextView = itemView.findViewById(R.id.categoriestitle)
        val categoriesimage: ImageView = itemView.findViewById(R.id.categoriesimage)
        val materialCardView: MaterialCardView = itemView.findViewById(R.id.material_card)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            if (viewType == LEFT_SIDED_VIEWTYPE) R.layout.left_sided_card_style
            else R.layout.right_sided_card_style,
            parent, false
        )
        return ViewHolder(view)
    }

    //ana 3yza a3ml inflate ll xml bs ana 3ndy 2 xml (right/ left) f ana 3yza el position 3shan a3rf ana R/L f
    // i will use viewtypr param thar returned by  getItemViewType elly m3aha el poistion w ashof
    // lw el position R--> return value x , w lw L--->  return value y
    val RIGHT_SIDED_VIEWTYPE = 20;
    val LEFT_SIDED_VIEWTYPE = 10;

    override fun getItemViewType(position: Int): Int {
        if (position % 2 == 0)
            return LEFT_SIDED_VIEWTYPE
        else
            return RIGHT_SIDED_VIEWTYPE

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = categorieslist[position]
        holder.categorytitle.setText(item.titleid)
        holder.categoriesimage.setImageResource(item.imageid)
        holder.materialCardView.setCardBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                item.backgroundcolorid
            )
        )

        onitemclicklistener?.let {
            //let de in will excute incase onitemclicklistener not null
            holder.itemView.setOnClickListener {
                onitemclicklistener?.OnItemClick(position, item)
            }
        } //first call back
    }

    override fun getItemCount(): Int {
        return categorieslist.size
    }

    var onitemclicklistener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun OnItemClick(position: Int, category: Category)
    }
}


