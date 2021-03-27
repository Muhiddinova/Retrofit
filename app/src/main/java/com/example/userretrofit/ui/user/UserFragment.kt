package com.example.userretrofit.ui.user

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.userretrofit.R
import com.example.userretrofit.Variables
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.data.database.MyDatabase
import com.example.userretrofit.databinding.UserFragmentBinding
import com.example.userretrofit.model.ModelUser
import com.example.userretrofit.network.CreateRetrofit
import com.example.userretrofit.network.NetworkInterceptor
import com.google.gson.Gson


class UserFragment : Fragment(), AdapterUsers.UserItemListener {
    private val TAG = "UserFragment"
    private lateinit var binding: UserFragmentBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: AdapterUsers
    private val list = arrayListOf<ModelUser>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_fragment, container, false)

        val dbSource = MyDatabase.createDatabase(requireContext())
        val interceptor = NetworkInterceptor(requireContext())
        val service = CreateRetrofit.getRetrofit(interceptor).create(RestApi::class.java)
        val factory = UserViewModelFactory(dbSource.userDao, service, requireContext())


        viewModel = ViewModelProvider(requireActivity(), factory).get(UserViewModel::class.java)
        viewModel.getUser().observe(viewLifecycleOwner) { users ->
            Log.d(TAG, "onCreateView: $users")
            val prefs = requireActivity().getSharedPreferences("timer", Context.MODE_PRIVATE)
            val lastUpdate = prefs.getLong("LastUpdate", 0)
            if (System.currentTimeMillis() - lastUpdate <= 1000) {
                if (!users.isNullOrEmpty())
                    setRv(users)
                else if (Variables.isNetworkAvailable.value == true){
                    viewModel.fetchUser(requireContext())
                }
//                else {
//                    findNavController().navigate(R.id.errorFragment)
//                }
            } else {
                findNavController().navigate(R.id.errorFragment)
            }
        }



        return binding.root
    }

    private fun setRv(users: List<ModelUser>) {

        adapter = AdapterUsers(this)
        binding.rvMainUser.adapter = adapter
        binding.rvMainUser.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        adapter.submitList(users as ArrayList<ModelUser>)
        Log.d(TAG, "setRv: $users")

//        adapter.updateData(users as ArrayList<ModelUser>)
    }


    override fun onClickedUser(
        modelUser: ModelUser
    ) {
        val jsonString = Gson().toJson(modelUser)
        findNavController().navigate(
            R.id.action_userFragment_to_postFragment,
            bundleOf("user" to jsonString)

        )
    }


}