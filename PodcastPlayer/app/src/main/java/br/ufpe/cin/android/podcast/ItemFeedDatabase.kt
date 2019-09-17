package br.ufpe.cin.android.podcast

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ItemFeed::class], version = 1)
abstract class ItemFeedDataBase : RoomDatabase(){
    abstract fun itemFeedDao() : ItemFeedDAO

    companion object {
        private var INSTANCE : ItemFeedDataBase? = null
        fun getDatabase(ctx : Context) : ItemFeedDataBase {
            if (INSTANCE == null) {
                synchronized(ItemFeedDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        ItemFeedDataBase::class.java,
                        "itemFeed.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}