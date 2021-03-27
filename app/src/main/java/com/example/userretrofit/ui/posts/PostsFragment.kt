package com.example.userretrofit.ui.posts

import android.os.Bundle
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
import com.example.userretrofit.data.RestApi
import com.example.userretrofit.data.database.MyDatabase
import com.example.userretrofit.model.ModelPost
import com.example.userretrofit.databinding.PostsFragmentBinding
import com.example.userretrofit.network.CreateRetrofit
import com.example.userretrofit.network.NetworkInterceptor
import com.example.userretrofit.ui.posts.adapter.PostsAdapter
import com.google.gson.Gson

class PostsFragment : Fragment(), PostsAdapter.PostsItemListener {
    private val TAG = "PostsFragment"
    private lateinit var binding: PostsFragmentBinding
    private lateinit var adapter: PostsAdapter
    private lateinit var viewModel: PostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.posts_fragment, container, false)
        val dbSource = MyDatabase.createDatabase(requireContext())
        val interceptor = NetworkInterceptor(requireContext()   )
        val service = CreateRetrofit.getRetrofit(interceptor).create(RestApi::class.java)

        val factory =PostsViewModelFactory(dbSource.userDao,service)

        viewModel = ViewModelProvider(requireActivity(),factory).get(PostsViewModel::class.java)
        viewModel.getAllPosts().observe(viewLifecycleOwner){posts->
            setRv(posts)

        }



        return binding.root

    }

    private fun setRv(posts: List<ModelPost>) {
        adapter = PostsAdapter(this)
        binding.rvMainPost.adapter = adapter
        binding.rvMainPost.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        adapter.updateData(posts as ArrayList<ModelPost>)
    }

    override fun onClicked(modelPost: ModelPost) {
        val jsonString = Gson().toJson(modelPost)
        findNavController().navigate(
            R.id.commentsFragment,
            bundleOf("post" to jsonString)
        )
    }

}