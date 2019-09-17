package br.ufpe.cin.android.podcast

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val URL = "https://s3-us-west-1.amazonaws.com/podcasts.thepolyglotdeveloper.com/podcast.xml"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        download()
    }

    private fun download(){
        doAsync {
            val db = ItemFeedDataBase.getDatabase(this@MainActivity)
            var itemFeedList: List<ItemFeed>

            try {
                itemFeedList = if(isConnected()){
                    val rss = URL(URL).readText()
                    Parser.parse(rss)
                }else{
                    db.itemFeedDao().selectAll()
                }
                db.itemFeedDao().addAll(itemFeedList)

                uiThread {
                    progress?.visibility = View.GONE
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = RecyclerAdapter(itemFeedList, applicationContext)
                    }
                }
            } catch (e: Throwable) {
                uiThread {
                    progress?.visibility = View.GONE
                    toast(e.message ?: e.toString())
                    Log.d("",e.message ?: e.toString())
                }
                //itemFeedList = db.itemFeedDao().selectAll()
            }


        }
    }

    private fun isConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork

        return activeNetwork != null
    }
}
