package com.example.librog.ui.main.addbook

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import com.example.librog.databinding.FragmentAddBookBinding
import com.example.librog.ui.BaseFragment

class AddBookFragment : BaseFragment<FragmentAddBookBinding>(FragmentAddBookBinding::inflate) {
    lateinit var imm: InputMethodManager

    override fun initAfterBinding() {
        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.addbookSearchCl.setOnClickListener{
            binding.addbookSearchEt.requestFocus()
            imm.showSoftInput(binding.addbookSearchEt, InputMethodManager.SHOW_IMPLICIT)
        }
        binding.addbookCancelIv.setOnClickListener{
            binding.addbookSearchEt.text.clear()
        }

    }


}