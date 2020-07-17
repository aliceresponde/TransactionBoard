package com.aliceresponde.transactionboard.presentation.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.HORIZONTAL
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.aliceresponde.transactionboard.R
import com.aliceresponde.transactionboard.databinding.FragmentTransactionsBinding
import com.aliceresponde.transactionboard.presentation.Transaction

class TransactionsFragment : Fragment() {
    private lateinit var binding: FragmentTransactionsBinding
    private val adapter: TransactionsAdapter by lazy { TransactionsAdapter(callback = this::navigateToTransactionInfo) }
    private val viewModel: TransactionsViewModel by viewModels()

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
            transactions.adapter = adapter
            viewModel = this@TransactionsFragment.viewModel
            transactions.addItemDecoration(DividerItemDecoration(context, HORIZONTAL))

        }
        return binding.root
    }

    private fun navigateToTransactionInfo(transaction: Transaction) {

    }
}