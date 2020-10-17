package android.androidtest1

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ListItem(@PrimaryKey val id: UUID = UUID.randomUUID(),
                    var name: String = "",
                    var isChecked: Boolean = false)