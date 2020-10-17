package android.androidtest1

import android.androidtest1.database.ListItemDatabase
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "list-item-database"

class ListItemRepository private constructor(context: Context) {

    private val database : ListItemDatabase = Room.databaseBuilder(
        context.applicationContext,
        ListItemDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val listItemDao = database.listItemDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getListItems(): LiveData<List<ListItem>> = listItemDao.getListItems()

    fun getListItem(id: UUID): LiveData<ListItem?> = listItemDao.getListItem(id)

    fun updateListItem(item: ListItem) {
        executor.execute {
            listItemDao.updateListItem(item)
        }
    }

    fun addListItem(item: ListItem) {
        executor.execute {
            listItemDao.addListItem(item)
        }
    }

    companion object {
        private var INSTANCE: ListItemRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ListItemRepository(context)
            }
        }

        fun get(): ListItemRepository {
            return INSTANCE ?:
                    throw IllegalStateException("ListItemRepository must be initialized")
        }
    }
}