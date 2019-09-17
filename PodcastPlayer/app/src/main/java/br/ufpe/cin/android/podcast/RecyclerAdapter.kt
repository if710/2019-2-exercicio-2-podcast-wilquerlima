package br.ufpe.cin.android.podcast

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.itemlista.view.*
import org.jetbrains.anko.design.snackbar

class RecyclerAdapter(private val listFeed: List<ItemFeed>, private val ctx: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.itemlista, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFeed.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemFeed = listFeed[position]
        holder.title.text = itemFeed.title
        holder.date.text = itemFeed.pubDate

        holder.title.setOnClickListener {
            val intent = Intent(ctx, EpisodeDetailActivity::class.java).apply {
                putExtra("item_details", listFeed[position])
            }
            ctx.startActivity(intent)
        }

        holder.download.setOnClickListener {
            try {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(listFeed[position].downloadLink)
                ctx.startActivity(i)
            } catch (e: Exception) {
                holder.itemView.snackbar(e.message ?: e.toString())
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.item_title!!
        val download = itemView.item_action!!
        val date = itemView.item_date!!
    }
}