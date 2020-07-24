package com.aliceresponde.transactionboard.presentation.transactions

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.aliceresponde.transactionboard.R
import com.aliceresponde.transactionboard.databinding.FragmentTransactionsBinding
import com.aliceresponde.transactionboard.domain.model.Transaction

class TransactionsFragment : Fragment() {
    private lateinit var binding: FragmentTransactionsBinding
    private val adapter: TransactionsAdapter by lazy {
        TransactionsAdapter(
            transactions = createFakeTransactions(),
            callback = this::navigateToTransactionInfo
        )
    }

    private val viewModel: TransactionsViewModel by viewModels()
    private lateinit var swipeBackground: ColorDrawable
    private lateinit var deleteIcon: Drawable

    private val transactionRecyclerView: RecyclerView by lazy { binding.transactions }
    private val itemTouchHelperCallback =
        object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
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

                if (dX > 0) { // right  swipe
                    swipeBackground.setBounds(
                        itemView.left,
                        itemView.top,
                        dX.toInt(),
                        itemView.bottom
                    )
                    deleteIcon.setBounds(
                        itemView.left + iconMargin,
                        itemView.top + iconMargin,
                        itemView.left + iconMargin + deleteIcon.minimumWidth,
                        itemView.bottom - iconMargin
                    )
                } else {
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
                if (dX > 0) {
                    c.clipRect(
                        itemView.left,
                        itemView.top,
                        dX.toInt(),
                        itemView.bottom
                    )
                } else {
                    c.clipRect(
                        itemView.left,
                        itemView.top,
                        dX.toInt(),
                        itemView.bottom
                    )
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
        binding.apply {
            viewModel = this@TransactionsFragment.viewModel
        }

        activity?.baseContext?.let {
            swipeBackground = ColorDrawable(ContextCompat.getColor(it, R.color.red))
            deleteIcon = ContextCompat.getDrawable(it, R.drawable.ic_delete)!!
        }


        setupRecycler()
        return binding.root
    }

    private fun setupRecycler() {
        transactionRecyclerView.adapter = adapter
        transactionRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(transactionRecyclerView)
        }
    }

    private fun navigateToTransactionInfo(transaction: Transaction) {

    }

    private fun createFakeTransactions(): MutableList<Transaction> {
        return mutableListOf(
            Transaction(
                1,
                200,
                1,
                "2020-02-22",
                "viva",
                "bogota",
                true
            ),
            Transaction(
                2,
                201,
                2,
                "2020-01-24",
                "viva",
                "bogota",
                true
            ),
            Transaction(
                3,
                202,
                2,
                "2020-01-22",
                "viva",
                "bogota",
                true
            ),
            Transaction(
                4,
                203,
                2,
                "2020-01-25",
                "viva",
                "bogota",
                true
            ),
            Transaction(
                5,
                204,
                2,
                "2020-01-26",
                "viva",
                "bogota",
                true
            ),
            Transaction(
                6,
                200,
                2,
                "2020-01-27",
                "viva",
                "bogota",
                false
            ),
            Transaction(
                7,
                205,
                2,
                "2020-01-28",
                "viva",
                "bogota",
                true
            )

        )
    }
}