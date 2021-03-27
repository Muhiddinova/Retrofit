package com.example.userretrofit.ui.comments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.userretrofit.model.ModelComments
import com.example.userretrofit.databinding.CommentsItemBinding

class CommentsAdapter ():RecyclerView.Adapter<CommentsAdapter.VH>(){
    private var list= arrayListOf<ModelComments>()

    fun updateData(list :ArrayList<ModelComments>){
        this.list=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsAdapter.VH {
        val inflater=LayoutInflater.from(parent.context)
            val binding=CommentsItemBinding.inflate(inflater,parent,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: CommentsAdapter.VH, position: Int) {
       holder.onBind(list[position])
    }

    override fun getItemCount()=list.size
    class VH(private val binding:CommentsItemBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(comments: ModelComments){
            binding.comment=comments
        }
    }
}