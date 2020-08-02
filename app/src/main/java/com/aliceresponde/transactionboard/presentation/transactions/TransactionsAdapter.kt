package com.aliceresponde.transactionboard.presentation.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aliceresponde.transactionboard.R
import com.aliceresponde.transactionboard.databinding.TransactionRowBinding
import com.aliceresponde.transactionboard.domain.model.Transaction

class TransactionsAdapter(
    private var transactions: MutableList<Transaction> = mutableListOf(),
    private val onItemClicked: (Transaction) -> Unit,
    private val onItemDeleted: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {
    private var removedPos = -1
    private lateinit var removedItem: Transaction

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.transaction_row, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.onBind(transactions[position])
    }

    fun deleteItem(holder: RecyclerView.ViewHolder) {
        val index = holder.adapterPosition
        removedPos = index
        removedItem = transactions[index]
        transactions.removeAt(index)
        notifyDataSetChanged()
        onItemDeleted.invoke(removedItem)
    }

    fun updateData(newItemList: List<Transaction>) {
        val oldList = transactions
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ShowItemDiffCallback(oldList, newItemList)
        )
        transactions = newItemList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    inner class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TransactionRowBinding.bind(view)

        fun onBind(transaction: Transaction) {
            binding.apply {
                transactionCommerceName.text = transaction.commerceName
                transactionDate.text = transaction.createdDate
                transactionState.setBackgroundColor(
                    if (transaction.isNew && adapterPosition < 20) ContextCompat.getColor(
                        itemView.context,
                        R.color.yellow
                    )
                    else ContextCompat.getColor(itemView.context, R.color.gray)
                )
            }
            itemView.setOnClickListener { onItemClicked(transaction) }
        }
    }

    class ShowItemDiffCallback(
        private var oldItemList: List<Transaction>,
        private var newItemList: List<Transaction>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItemList[oldItemPosition].id == newItemList[newItemPosition].id

        override fun getOldListSize(): Int = oldItemList.size

        override fun getNewListSize(): Int = newItemList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItemList[oldItemPosition] == newItemList[newItemPosition]
    }
}