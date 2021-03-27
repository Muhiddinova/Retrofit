package com.example.userretrofit.ui.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.userretrofit.model.ModelPost
import com.example.userretrofit.databinding.RvItemBinding

class PostsAdapter(private val listener:PostsItemListener) :
RecyclerView.Adapter<PostsAdapter.VH>(){
    interface PostsItemListener{
        fun onClicked(modelPost: ModelPost)
    }

    private  val TAG="PostsAdapter"
    private var list= arrayListOf<ModelPost>()
    fun updateData(list:ArrayList<ModelPost>){
        this.list=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapter.VH {
        val inflater=LayoutInflater.from(parent.context)
        val binding=RvItemBinding.inflate(inflater,parent,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder:VH, position: Int){
        holder.itemView.setOnClickListener {
            listener.onClicked(list[position])
        }
        holder.onBind(list[position])
    }

    override fun getItemCount()=list.size
    class VH(private val binding: RvItemBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(modelPost: ModelPost){
            binding.post=modelPost
        }
    }

}