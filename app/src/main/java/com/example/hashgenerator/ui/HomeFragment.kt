package com.example.hashgenerator.ui

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.graphics.alpha
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashgenerator.R
import com.example.hashgenerator.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding


    override fun onResume() {
        super.onResume()
        // we will show the strings in string array
        val hashAlgorithm = resources.getStringArray(R.array.hash_algorithm)
        // we create a layout (drop_down_items) to show the items inside it
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_items, hashAlgorithm)
        binding.autoTextInput.setAdapter(arrayAdapter)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        // this line to show menu icon in bar
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonGenerate.setOnClickListener {
            onGeneratedButtonClicked()
        }
    }

    private fun onGeneratedButtonClicked() {
        if (binding.editText.text.isEmpty()) {
            // we will show snackBar
            showSnackBar("Field Empty!")

        } else {
            lifecycleScope.launch {
                applyAnimation()
                navigationToSuccess()
            }
        }
    }

    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(
            binding.rootLayout,
            message,
            Snackbar.LENGTH_SHORT
        )
        snackBar.setAction("Okay") {}
        snackBar.show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    // this fun to animate text view
    private suspend fun applyAnimation() {
        // this to disable button after one click
        binding.buttonGenerate.isClickable = false

        binding.textView.animate().alpha(0f).duration = 400L
        binding.buttonGenerate.animate().alpha(0f).duration = 400L
        binding.textInputLayout.animate()
            .alpha(0f)
            .translationXBy(1200f)
            .duration = 400L

        binding.editText.animate()
            .alpha(0f)
            .translationXBy(-1200f)
            .duration = 400L


        delay(300)
        // will animate success background
        binding.successBackground.animate().alpha(1f).duration = 600L
        binding.successBackground.animate().rotationBy(720f).duration = 600L
        binding.successBackground.animate().scaleXBy(900f).duration = 800L
        binding.successBackground.animate().scaleYBy(900f).duration = 800L

        delay(500)
        // will show success circle
        binding.successImageView.animate().alpha(1f).duration = 1000L

        // this delay to wait 1.5 second before moving to success screen
        delay(1500)

    }

    private fun navigationToSuccess() {
        findNavController().navigate(R.id.action_homeFragment_to_successFragment)
    }

    // this to if the fragment destroyed will set binding into null
    // to avoid memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        //        binding = null

    }

}