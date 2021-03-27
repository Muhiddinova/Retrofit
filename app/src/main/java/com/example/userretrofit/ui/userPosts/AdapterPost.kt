package com.example.userretrofit.ui.userPosts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.userretrofit.model.ModelPost
import com.example.userretrofit.databinding.RvItemBinding

class AdapterPost(): RecyclerView.Adapter<AdapterPost.VH>() {

    private var list = arrayListOf<ModelPost>()

    fun updateData(list: ArrayList<ModelPost>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

    class VH(private val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(post: ModelPost) {
            binding.post = post
        }

    }

}