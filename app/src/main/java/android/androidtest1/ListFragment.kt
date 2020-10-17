package android.androidtest1

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "ListFragment"

class ListFragment : Fragment() {

    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun onItemPressed(itemId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var listRecyclerView: RecyclerView
    private lateinit var addButton: Button
    private var adapter: ListItemAdapter? = ListItemAdapter(emptyList())
    private val listViewModel: ListViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        listRecyclerView = view.findViewById(R.id.list_recycler_view) as RecyclerView
        listRecyclerView.layoutManager = LinearLayoutManager(context)
        listRecyclerView.adapter = adapter
        addButton = view.findViewById(R.id.add_button) as Button

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel.listLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { items ->
                items?.let {
                    Log.i(TAG, "Got items ${items.size}")
                    updateUI(items)
                }
            }
        )

        addButton.setOnClickListener {
            val item = ListItem()
            listViewModel.addListItem(item)
            callbacks?.onItemPressed(item.id)
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(items: List<ListItem>) {
        adapter = ListItemAdapter(items)
        listRecyclerView.adapter = adapter
    }

    private inner class ListItemHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        private lateinit var item: ListItem

        private val nameTextView: TextView = itemView.findViewById(R.id.item_name)
        private val itemImageView: ImageView = itemView.findViewById(R.id.item_icon)
        private val itemCheckBox: CheckBox = itemView.findViewById(R.id.item_check_box)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bind(item: ListItem) {
            this.item = item
            val icon = if (this.item.isChecked) listViewModel.drawableStarOn else listViewModel.drawableStarOff
            nameTextView.text = this.item.name
            itemImageView.setImageDrawable(icon)
            itemCheckBox.isChecked = this.item.isChecked
            itemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                this.item.isChecked = isChecked
                listViewModel.saveItem(this.item)
            }
        }

        override fun onClick(v: View) {
            callbacks?.onItemPressed(item.id)
        }

        override fun onLongClick(v: View): Boolean {
            Toast.makeText(context, "${item.name} long pressed!", Toast.LENGTH_SHORT)
                .show()
            return true
        }
    }

    private inner class ListItemAdapter(var items: List<ListItem>)
        : RecyclerView.Adapter<ListItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
            val view = layoutInflater.inflate(R.layout.list_item, parent, false)
            return ListItemHolder(view)
        }

        override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
            val item = items[position]
            holder.bind(item)
        }

        override fun getItemCount() = items.size
    }

    companion object {
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }
}