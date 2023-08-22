package com.example.news.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.news.NewsAdapter
import com.example.news.R
import com.example.news.api.ApiManager
import com.example.news.api.Constants
import com.example.news.model.Category
import com.example.news.model.NewsResponse
import com.example.news.model.SourcesItem
import com.example.news.model.SourcesResponse
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    companion object {

        fun getInstance(category: Category): NewsFragment {
            val fragment = NewsFragment()
            fragment.category = category
            return fragment
        }
    }

    lateinit var category: Category
    lateinit var tabLayout: TabLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    var adapter = NewsAdapter(null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout = view.findViewById(R.id.tablayout)
        progressBar = view.findViewById(R.id.progbar)
        recyclerView = view.findViewById(R.id.recview)
        recyclerView.adapter = adapter

        getNewsSources()

    }

    fun getNewsSources() {

        ApiManager.getApis().getsources(Constants.apiKey, category.id)
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

    fun addsourcestotablayout(sources: List<SourcesItem?>?) {
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

    fun getnewsofsource(sourcec: SourcesItem) {
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

// ana kda 3ndy 2 call backs
// 1- click on categories adapter will call category fragment (category adapter--->category fragemnt) (adapter->fragment)
// 2- home activity should replace category fragment by new fragment according to chosen category ( home activity replace category fragment by news fragment)(fragmment->activity)