package com.example.userretrofit.ui.comments

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
import com.example.userretrofit.model.ModelComments
import com.example.userretrofit.model.ModelPost
import com.example.userretrofit.databinding.CommentsFragmentBinding
import com.example.userretrofit.network.CreateRetrofit
import com.example.userretrofit.network.NetworkInterceptor
import com.example.userretrofit.ui.comments.adapter.CommentsAdapter
import com.google.gson.Gson

class CommentsFragment : Fragment() {

    private lateinit var binding: CommentsFragmentBinding
    private lateinit var adapter: CommentsAdapter
    private lateinit var viewModel: CommentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.comments_fragment, container, false)
        val post = arguments?.getString("post")
        if (post != null) {
            val modelComments = Gson().fromJson(post, ModelPost::class.java)
            binding.comment = modelComments
            val dbSource = MyDatabase.createDatabase(requireContext())
            val interceptor = NetworkInterceptor(requireContext()   )
            val service = CreateRetrofit.getRetrofit(interceptor).create(RestApi::class.java)
            val factory = CommentsViewModelFactory(dbSource.userDao,service)
            viewModel =
                ViewModelProvider(requireActivity(), factory).get(CommentsViewModel::class.java)
            this@CommentsFragment.viewModel.fetchComments(modelComments.id)
                .observe(viewLifecycleOwner) { comments ->
                    setRv(comments)
                }


        }
        return binding.root
    }

    private fun setRv(comments: List<ModelComments>) {
        adapter = CommentsAdapter()
        binding.rvComment.adapter = adapter
        binding.rvComment.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )
        adapter.updateData(comments as ArrayList<ModelComments>)
    }


}