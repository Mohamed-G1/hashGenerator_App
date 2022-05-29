package com.example.hashgenerator.ui

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashgenerator.R
import com.example.hashgenerator.databinding.FragmentHomeBinding
import com.example.hashgenerator.viewModels.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()


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

                navigationToSuccess(getHashData())

            }
        }
    }

    // fun to get hash
    private fun getHashData(): String {
        // first we will get the values from edit text

        val algorithm = binding.autoTextInput.text.toString()
        val text = binding.editText.text.toString()
        return viewModel.getHash(text, algorithm)

    }

    // show snackBar
    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(
            binding.rootLayout,
            message,
            Snackbar.LENGTH_SHORT
        )
        snackBar.setAction("Okay") {}
            snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        snackBar.show()

    }

    // show menu in top bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    // create an action on item selected in menu on top bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_menu) {
            binding.editText.text.clear()
            showSnackBar("Cleared! ")
            return true
        }
        return true

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

    private fun navigationToSuccess(hash : String) {
        val directions = HomeFragmentDirections.actionHomeFragmentToSuccessFragment().setHash(hash)

        findNavController().navigate(directions)
    }

    // this to if the fragment destroyed will set binding into null
    // to avoid memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        //        binding = null

    }

}