package br.ufpe.cin.android.podcast

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_episode_detail.*

class EpisodeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_detail)

        val itemFeed = intent.getSerializableExtra("item_details") as ItemFeed
        putItem(itemFeed)
    }

    private fun putItem(itemFeed: ItemFeed) {
        if(itemFeed.imageUrl.isNotEmpty()){
            Picasso.get()
                .load(itemFeed.imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .fit()
                .into(image)
        }

        title_txt.text = itemFeed.title
        description.text = itemFeed.description
        link.text = itemFeed.link
        link.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }
}
