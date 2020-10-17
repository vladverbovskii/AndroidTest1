package android.androidtest1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import java.util.*

private const val TAG = "ItemEditFormFragment"
private const val ARG_ITEM_ID = "item_id"

class ItemEditFormFragment : Fragment() {

    private lateinit var item: ListItem
    private lateinit var nameField: EditText
    private lateinit var doneButton: Button
    private lateinit var revertButton: Button
    private val itemEditFormViewModel: ItemEditFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = ListItem()
        val itemId: UUID = arguments?.getSerializable(ARG_ITEM_ID) as UUID
        itemEditFormViewModel.loadItem(itemId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_edit_form, container, false)

        nameField = view.findViewById(R.id.item_name) as EditText
        doneButton = view.findViewById(R.id.done_button) as Button
        revertButton = view.findViewById(R.id.revert_button) as Button

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemEditFormViewModel.itemLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { item ->
                item?.let {
                    this.item = item
                    updateUI()
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()

        val nameWatcher = object : TextWatcher {

            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
                // This space intentionally left blank
            }
            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                item.name = sequence.toString()
            }
            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        nameField.addTextChangedListener(nameWatcher)
        doneButton.setOnClickListener {
            Toast.makeText(context, "Done button pressed!", Toast.LENGTH_SHORT)
                .show()
        }
        revertButton.setOnClickListener {
            Toast.makeText(context, "Revert button pressed!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onStop() {
        super.onStop()
        itemEditFormViewModel.saveItem(item)
    }

    private fun updateUI() {
        nameField.setText(item.name)
    }

    companion object {
        fun newInstance(itemId: UUID): ItemEditFormFragment {
            val args = Bundle().apply {
                putSerializable(ARG_ITEM_ID, itemId)
            }
            return ItemEditFormFragment().apply {
                arguments = args
            }
        }
    }
}