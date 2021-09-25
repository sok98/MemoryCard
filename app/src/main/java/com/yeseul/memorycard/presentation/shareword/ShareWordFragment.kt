package com.yeseul.memorycard.presentation.shareword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.yeseul.memorycard.R
import com.yeseul.memorycard.databinding.FragmentShareWordBinding

class ShareWordFragment : Fragment(R.layout.fragment_share_word) {
    private lateinit var binding: FragmentShareWordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShareWordBinding.bind(view)
    }
}