package kate.tutorial.turtlesoup.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kate.tutorial.turtlesoup.R
import kate.tutorial.turtlesoup.databinding.ItemChatBinding

/**
 * Created by Kate on 2021/7/3
 */
class ChatAdapter(private var chatList: ArrayList<String>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemBinding : ItemChatBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_chat, parent, false
        )
        return ViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    class ViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: String) {
            binding.chat = chat
            binding.executePendingBindings()
        }
    }

    fun setChatList(it: ArrayList<String>) {
        chatList = it
        notifyDataSetChanged()
    }
}
@BindingAdapter("chatItems")
fun bindRecyclerViewWithChatItemList(recyclerView: RecyclerView, itemList: ArrayList<String>?) {
    itemList?.let {
        recyclerView.adapter.apply {
            when (this) {
                is ChatAdapter -> setChatList(it)
            }
        }
    }
}
