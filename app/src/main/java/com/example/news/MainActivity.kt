package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Callback
import com.example.news.api.ApiManager
import com.example.news.api.Constants
import com.example.news.model.NewsResponse
import com.example.news.model.SourcesItem
import com.example.news.model.SourcesResponse
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    var adapter = NewsAdapter(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tablayout)
        progressBar = findViewById(R.id.progbar)
        recyclerView = findViewById(R.id.recview)
        recyclerView.adapter = adapter

        getNewsSources()
    }

    //calling api
    fun getNewsSources() {

        ApiManager.getApis().getsources(Constants.apiKey, "")
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressBar.isVisible = false
                    addsourcestotablayout(response.body()?.sources)
                }

                override fun onFailure(
                    call: Call<SourcesResponse>,
                    t: Throwable
                ) { // in case no retrive data
                    Log.e("Response data fail", t.message.toString());
                }


            })

    }

    private fun addsourcestotablayout(sources: List<SourcesItem?>?) {
        sources?.forEach {
            val tab = tabLayout.newTab()
            tab.setText(it?.name)
            tab.tag = it // tag here as pointer using to connect data with view
            tabLayout.addTab(tab)

        }
        tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    //val source = sources?.get(tab?.position?:0)
                    val source = tab?.tag as SourcesItem
                    getnewsofsource(source)


                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    getnewsofsource(source)

                }

            }
        )
        tabLayout.getTabAt(0)?.select()

    }

    private fun getnewsofsource(sourcec: SourcesItem) {
        progressBar.isVisible = true
        ApiManager.getApis().getnews(Constants.apiKey, sourcec.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressBar.isVisible = false
                    adapter.changeData(response.body()?.articles)

                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressBar.isVisible = false
                }
            })


    }
}