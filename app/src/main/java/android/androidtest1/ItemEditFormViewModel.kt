package android.androidtest1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class ItemEditFormViewModel() : ViewModel() {

    private val itemRepository = ListItemRepository.get()
    private val itemIdLiveData = MutableLiveData<UUID>()

    var itemLiveData: LiveData<ListItem?> =
        Transformations.switchMap(itemIdLiveData) { itemId ->
            itemRepository.getListItem(itemId)
        }

    fun loadItem(itemId: UUID) {
        itemIdLiveData.value = itemId
    }

    fun saveItem(item: ListItem) {
        itemRepository.updateListItem(item)
    }
}