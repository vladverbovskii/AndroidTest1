package android.androidtest1

import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {

    private val itemRepository = ListItemRepository.get()
    val listLiveData = itemRepository.getListItems()
    val drawableStarOn = ResourcesCompat.getDrawable(android.content.res.Resources.getSystem(), android.R.drawable.btn_star_big_on, null)
    val drawableStarOff = ResourcesCompat.getDrawable(android.content.res.Resources.getSystem(), android.R.drawable.btn_star_big_off, null)

    fun addListItem(item: ListItem) {
        itemRepository.addListItem(item)
    }

    fun saveItem(item: ListItem) {
        itemRepository.updateListItem(item)
    }
}