package com.example.news.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.model.Category
import com.example.news.ui.news.NewsFragment

class HomeActivity : AppCompatActivity() {
    lateinit var toolbartitle: TextView
    lateinit var drawerLayout: DrawerLayout
    lateinit var menuicaon: ImageView
    lateinit var categories: View
    lateinit var settings: View
    val categoriesFragment = CategoriesFragment()
    val settingsFragment = SettingsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initiView()
        Pushfragment(categoriesFragment)
        toolbartitle.setText("Categories")
        categoriesFragment.oncategoryclicklistener =
            object : CategoriesFragment.OnCategoryClickListener {
                override fun OnCategoryClick(category: Category) {
                    Pushfragment(NewsFragment.getInstance(category), true)
                    toolbartitle.setText(category.id)
                }

            }

    }

    fun initiView() {
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbartitle = findViewById(R.id.toolbartitle)
        menuicaon = findViewById(R.id.iconmaenu)
        categories = findViewById(R.id.categories)
        settings = findViewById(R.id.settings)
        menuicaon.setOnClickListener {
            drawerLayout.open()
        }
        categories.setOnClickListener {
            Pushfragment(categoriesFragment)
            toolbartitle.setText("Categories")

        }
        settings.setOnClickListener {
            Pushfragment(settingsFragment)
            toolbartitle.setText("Settings")

        }

    }

    fun Pushfragment(fragment: Fragment, addtobacktack: Boolean = false) {
        val fragmentTransaction =
            supportFragmentManager.beginTransaction().replace(R.id.fragments_container, fragment)
        if (addtobacktack)
            fragmentTransaction.addToBackStack("")
        fragmentTransaction.commit()
        drawerLayout.close()
    }


}