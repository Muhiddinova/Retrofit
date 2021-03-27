package com.example.userretrofit.ui.userPosts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.userretrofit.R
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.data.database.MyDatabase
import com.example.userretrofit.model.ModelPost
import com.example.userretrofit.model.ModelUser
import com.example.userretrofit.databinding.PostFragmentBinding
import com.example.userretrofit.network.CreateRetrofit
import com.example.userretrofit.network.NetworkInterceptor
import com.google.gson.Gson

@Suppress("UNREACHABLE_CODE")
const val USER_ID = "user_id"

class PostFragment : Fragment() {


    private lateinit var viewModel: PostViewModel
    private lateinit var binding: PostFragmentBinding
    private lateinit var adapter: AdapterPost
//    private  var userId:Int =-1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.post_fragment, container, false)
        val user = arguments?.getString("user")
        if (user != null) {
            val modelUser = Gson().fromJson(user, ModelUser::class.java)
            binding.user = modelUser
            val interceptor = NetworkInterceptor(requireContext()   )
            val service = CreateRetrofit.getRetrofit(interceptor).create(RestApi::class.java)
            val dbSource = MyDatabase.createDatabase(requireContext())
            val factory = PostViewModelFactory(dbSource.userDao,service )

            viewModel = ViewModelProvider(requireActivity(), factory).get(PostViewModel::class.java)
            this@PostFragment.viewModel.fetchPosts(modelUser.id).observe(viewLifecycleOwner) { post ->

                setRv(post)

            }
        }
        return binding.root
    }

    private fun setRv(post: List<ModelPost>) {
        adapter = AdapterPost()
        binding.rvMain.adapter=adapter
        binding.rvMain.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )


        adapter.updateData(post as ArrayList<ModelPost>)

    }


}