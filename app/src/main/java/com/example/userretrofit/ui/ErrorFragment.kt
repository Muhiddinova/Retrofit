package com.example.userretrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.userretrofit.R
import com.example.userretrofit.Variables
import com.example.userretrofit.databinding.FragmentErrorBinding
import com.example.userretrofit.ui.user.UserViewModel


class ErrorFragment : Fragment() {

    private lateinit var binding: FragmentErrorBinding
    private lateinit var viewModel: UserViewModel

    @Suppress("UNREACHABLE_CODE")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_error, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Variables.isNetworkAvailable.observe(viewLifecycleOwner) { isAvailable ->

            findNavController().popBackStack()
        }
    }


}