package com.example.news.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.Category
import com.example.news.ui.categories.categoriesAdapter


class CategoriesFragment : Fragment() {

    val ctegorieslist = listOf(
        Category("sports", R.string.sports, R.drawable.sports, R.color.red),
        Category("technology", R.string.technology, R.drawable.politics, R.color.blue),
        Category("health", R.string.health, R.drawable.health, R.color.pink),
        Category("business", R.string.business, R.drawable.bussines, R.color.brown),
        Category("general", R.string.general, R.drawable.environment, R.color.babyblu),
        Category("science", R.string.science, R.drawable.science, R.color.yellow)
    )
    val adapter = categoriesAdapter(ctegorieslist)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.categoriesrv)

        recyclerView.adapter = adapter
        adapter.onitemclicklistener =
            object : categoriesAdapter.OnItemClickListener { //seconed callback
                override fun OnItemClick(position: Int, category: Category) {
                    oncategoryclicklistener?.OnCategoryClick(category)

                }
            }
    }

    var oncategoryclicklistener: OnCategoryClickListener? = null

    interface OnCategoryClickListener {
        fun OnCategoryClick(category: Category)
    }


}