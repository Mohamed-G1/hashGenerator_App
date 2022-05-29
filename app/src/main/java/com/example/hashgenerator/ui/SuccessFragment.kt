package com.example.hashgenerator.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hashgenerator.R
import com.example.hashgenerator.databinding.FragmentSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log

class SuccessFragment : Fragment() {


    private val args: SuccessFragmentArgs by navArgs()

    lateinit var binding: FragmentSuccessBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_success, container, false)

//        Log.d("success", args.hash)
        binding.hashTextView.text = args.hash
        binding.copyButton.setOnClickListener {
            onCopyClicked()
        }

        return binding.root
    }

    private fun onCopyClicked() {

        lifecycleScope.launch {
            copyToClipboard(args.hash)
            applyCopiedBarAnimation()
        }
    }

    // this to save text to clipboard
    private fun copyToClipboard(hash: String) {
        val clipboardManager =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Encrypted Text", hash)
        clipboardManager.setPrimaryClip(clipData)
    }

    // this fun to show animation for (copied) bar from top
    private suspend fun applyCopiedBarAnimation() {
        binding.include.copiedBackground.animate().translationY(80f).duration = 200L
        binding.include.messageTextView.animate().translationY(80f).duration = 200L

        delay(2000L)

        binding.include.copiedBackground.animate().translationY(-80f).duration = 500L
        binding.include.messageTextView.animate().translationY(-80f).duration = 500L
    }

}