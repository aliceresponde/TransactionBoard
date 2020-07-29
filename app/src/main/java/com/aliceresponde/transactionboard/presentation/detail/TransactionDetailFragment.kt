package com.aliceresponde.transactionboard.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.aliceresponde.transactionboard.R.layout.fragment_transaction_detail
import com.aliceresponde.transactionboard.databinding.FragmentTransactionDetailBinding
import com.aliceresponde.transactionboard.domain.model.Transaction
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class TransactionDetailFragment : Fragment() {

    private lateinit var binding: FragmentTransactionDetailBinding
    private val args: TransactionDetailFragmentArgs by navArgs()
    private val transaction: Transaction by lazy { args.transaction }
    private val viewModel: TransactionDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, fragment_transaction_detail, container, false)
        binding.apply {
            viewModel = this@TransactionDetailFragment.viewModel
            lifecycleOwner = this@TransactionDetailFragment
            transactionDetailDate.text = "Date: ${transaction.createdDate}"
            transactionDetailCommerceName.text = "Commerce:  ${transaction.commerceName}"
            transactionDetailBranch.text = "Place:  ${transaction.branchName}"
        }

        viewModel.transactionInfo.observe(viewLifecycleOwner, Observer { transactionInfo ->
            binding.transactionDetailPoints.text = "Points:  ${transactionInfo.points}"
            binding.transactionDetailValue.text = "Value: $   ${NumberFormat.getNumberInstance(
                Locale.US
            ).format(transactionInfo.value)}"
        })

        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.transactionDetailUserName.text = "User : ${it.name}"
        })

        viewModel.getTransactionInfo(transaction.id)
        viewModel.getUserInfo(transaction.userId)

        return binding.root
    }
}