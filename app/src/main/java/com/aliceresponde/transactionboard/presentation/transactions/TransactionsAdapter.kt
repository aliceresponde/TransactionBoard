package com.aliceresponde.transactionboard.presentation.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aliceresponde.transactionboard.R
import com.aliceresponde.transactionboard.databinding.TransactionRowBinding
import com.aliceresponde.transactionboard.domain.model.Transaction
import com.google.android.material.snackbar.Snackbar

class TransactionsAdapter(
    private val transactions: MutableList<Transaction> = mutableListOf(),
    private val callback: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {
    private var removedPos = -1
    private lateinit var removedItem: Transaction

    inner class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TransactionRowBinding.bind(view)

        fun onBind(transaction: Transaction, position: Int) {
            binding.apply {
                transactionCommerceName.text = transaction.commerceName
                transactionDate.text = transaction.createdDate
                transactionState.setBackgroundColor(
                    if (transaction.isNew && position < 20)
                        ContextCompat.getColor(itemView.context, R.color.yellow)
                    else ContextCompat.getColor(itemView.context, R.color.gray)
                )
            }
            itemView.setOnClickListener {
                callback(transaction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.transaction_row, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.onBind(transactions[position], position)
    }

    fun deleteItem(holder: RecyclerView.ViewHolder) {
        val index = holder.adapterPosition
        removedPos = index
        removedItem = transactions[index]
        transactions.removeAt(index)
        notifyDataSetChanged()

        Snackbar.make(
            holder.itemView,
            holder.itemView.context.getString(R.string.item_removed),
            Snackbar.LENGTH_LONG
        ).apply {
            setAction(context.getString(R.string.undo)) { unDeleteItem() }
            show()
        }
    }

    private fun unDeleteItem() {
        transactions.add(removedPos, removedItem)
        notifyDataSetChanged()
    }

    fun addTransactions(data: List<Transaction>) {
        transactions.addAll(data)
        notifyDataSetChanged()
    }
}