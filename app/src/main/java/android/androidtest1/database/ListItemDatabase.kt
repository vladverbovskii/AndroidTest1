package android.androidtest1.database

import android.androidtest1.ListItem
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ ListItem::class], version=1, exportSchema = false)
@TypeConverters(ListItemTypeConverters::class)
abstract class ListItemDatabase : RoomDatabase() {

    abstract fun listItemDao(): ListItemDao
}