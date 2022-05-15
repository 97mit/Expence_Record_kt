package com.stimednp.roommvvm.ui.home

import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stimednp.roommvvm.R
import com.stimednp.roommvvm.databinding.FragmentHomeBinding
import com.stimednp.roommvvm.ui.*
import com.stimednp.roommvvm.ui.home.MessViewModel
import com.stimednp.roommvvm.ui.mess_home.MessHomeViewModel
import com.stimednp.roommvvm.utils.Coroutines
import com.stimednp.roommvvm.utils.UtilExtensions.myToast
import com.stimednp.roommvvm.utils.UtilExtensions.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<MessViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var messListAdapter: MessListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        val root: View = binding.root
        messListAdapter = MessListAdapter {
            activity?.openActivity(MealsActivity::class.java) {
                putParcelable(MainActivity.NOTE_DATA, it)
            }
        }
        initView()
        observeNotes()

        return root
    }

    private fun initView() {
        binding.addNoteFAB.setOnClickListener {
            activity?.openActivity(AddMessActivity::class.java)
        }

        binding.notesRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = messListAdapter
        }

        ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(binding.notesRV)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearNoteItem -> clearNote()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun observeNotes() {
        Coroutines.main {
            viewModel.getAllMesses().observe(viewLifecycleOwner, {
                messListAdapter.submitList(it)
            })
        }
    }

    private fun itemTouchHelperCallback(): ItemTouchHelper.SimpleCallback {
        return object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Coroutines.main {
                    val note = messListAdapter.getNoteAt(viewHolder.adapterPosition)
                    viewModel.deleteMess(note).also {
                        activity?.myToast(getString(R.string.success_delete))
                    }
                }
            }
        }
    }

    private fun clearNote() {
        val dialog = AlertDialog.Builder(Application(), R.style.ThemeOverlay_AppCompat_Dialog)
        dialog.setTitle(getString(R.string.clear_note))
            .setMessage(getString(R.string.sure_clear_items))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                Coroutines.main {
                    viewModel.clearMess().also {
                        activity?.myToast(getString(R.string.success_clear))
                    }
                }
            }.setNegativeButton(android.R.string.cancel, null).create().show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        //binding = null
    }
}