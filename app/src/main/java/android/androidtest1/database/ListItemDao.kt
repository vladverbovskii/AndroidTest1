package android.androidtest1.database

import android.androidtest1.ListItem
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.*

@Dao
interface ListItemDao {

    @Query("SELECT * FROM listItem")
    fun getListItems(): LiveData<List<ListItem>>

    @Query("SELECT * FROM listItem WHERE id=(:id)")
    fun getListItem(id: UUID): LiveData<ListItem?>

    @Update
    fun updateListItem(item: ListItem)

    @Insert
    fun addListItem(item: ListItem)
}