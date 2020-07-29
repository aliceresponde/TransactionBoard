package com.aliceresponde.transactionboard.presentation.transactions

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.IndicPositionalCategory.LEFT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.aliceresponde.transactionboard.R
import com.aliceresponde.transactionboard.databinding.FragmentTransactionsBinding
import com.aliceresponde.transactionboard.domain.model.Transaction
import com.aliceresponde.transactionboard.presentation.transactions.TransactionMessage.EMPTY
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransactionsFragment : Fragment() {
    private lateinit var binding: FragmentTransactionsBinding
    private val viewModel: TransactionsViewModel by viewModels()
    private val adapter: TransactionsAdapter by lazy { TransactionsAdapter(onItemClicked = this::navigateToTransactionInfo) }

    private lateinit var swipeBackground: ColorDrawable
    private lateinit var deleteIcon: Drawable

    private val transactionRecyclerView: RecyclerView by lazy { binding.transactions }
    private val itemTouchHelperCallback =
        object : ItemTouchHelper.SimpleCallback(0, LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.deleteItem(viewHolder)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2

                if (dX < 0) { // left  swipe
                    swipeBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )

                    deleteIcon.setBounds(
                        itemView.right - iconMargin - deleteIcon.intrinsicWidth,
                        itemView.top + iconMargin,
                        itemView.right - iconMargin,
                        itemView.bottom - iconMargin
                    )
                }

                swipeBackground.draw(c)
                c.save()
                if (dX < 0) {
                    c.clipRect(itemView.right, itemView.top, dX.toInt(), itemView.bottom)
                }

                c.restore()
                deleteIcon.draw(c)

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_transactions,
            container,
            false
        )
        activity?.baseContext?.let {
            swipeBackground = ColorDrawable(ContextCompat.getColor(it, R.color.red))
            deleteIcon = ContextCompat.getDrawable(it, R.drawable.ic_delete)!!
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@TransactionsFragment.viewModel
            lifecycleOwner = this@TransactionsFragment
            deleteAllTransactions.setOnClickListener { this@TransactionsFragment.viewModel.deleteAllTransactions() }
            restoreAllTransactions.setOnClickListener { this@TransactionsFragment.viewModel.restoreTransactions() }
        }

        setupRecycler()
        setupObservers()
        viewModel.getTransactions()
    }

    private fun setupObservers() {
        viewModel.transactions.observe(viewLifecycleOwner, Observer { adapter.addTransactions(it) })
        viewModel.labelMessage.observe(viewLifecycleOwner, Observer {
            binding.message.text = when (it) {
                EMPTY -> getString(R.string.no_data)
                else -> getString(R.string.internet_error)
            }
        })
    }


    private fun setupRecycler() {
        transactionRecyclerView.adapter = adapter
        transactionRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(transactionRecyclerView)
        }
    }

    private fun navigateToTransactionInfo(transaction: Transaction) {
        val action =
            TransactionsFragmentDirections.actionTransactionsFragmentToTransactionDetailFragment(
                transaction
            )
        findNavController().navigate(action)
    }
}