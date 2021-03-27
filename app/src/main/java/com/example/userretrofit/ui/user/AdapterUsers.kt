package com.example.userretrofit.ui.user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.userretrofit.model.ModelUser
import com.example.userretrofit.databinding.UserItemBinding

class AdapterUsers(private val listener: UserItemListener) :ListAdapter<ModelUser,VH>(DiffUtilCalback()){
    interface   UserItemListener {

        fun onClickedUser(modelUser: ModelUser)
    }

    private val TAG="AdapterUsers"

    override fun submitList(list: MutableList<ModelUser>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserItemBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onClickedUser(currentList[position])
        }
        holder.onBind(currentList[position])
    }
}


    class VH(
        private val binding: UserItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(user: ModelUser) {
            binding.user = user

        }

    }
class DiffUtilCalback() : DiffUtil.ItemCallback<ModelUser>() {
    override fun areItemsTheSame(oldItem: ModelUser, newItem: ModelUser): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ModelUser, newItem: ModelUser): Boolean {
        return oldItem == newItem
    }

}






//    RecyclerView.Adapter<AdapterUsers.VH>() {
//
//    interface   UserItemListener {
//
//        fun onClickedUser(modelUser: ModelUser)
//    }
//
//
//    private var list = arrayListOf<ModelUser>()
//
//    fun updateData(list: ArrayList<ModelUser>) {
//        this.list = list
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = UserItemBinding.inflate(inflater, parent, false)
//        return VH(binding)
//    }
//
//    override fun onBindViewHolder(holder: VH, position: Int) {
//        holder.itemView.setOnClickListener {
//            listener.onClickedUser(list[position])
//        }
//        holder.onBind(list[position])
//    }
//
//    override fun getItemCount() = list.size
//    class VH(
//        private val binding: UserItemBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        @SuppressLint("SetTextI18n")
//        fun onBind(user: ModelUser) {
//            binding.user = user
//
//        }
//
//    }
//}