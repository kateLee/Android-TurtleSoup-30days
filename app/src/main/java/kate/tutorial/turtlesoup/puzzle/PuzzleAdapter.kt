package kate.tutorial.turtlesoup.puzzle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kate.tutorial.turtlesoup.R
import kate.tutorial.turtlesoup.databinding.ItemPuzzleBinding

/**
 * Created by Kate on 2021/6/18
 */
class PuzzleAdapter(private var puzzleList: ArrayList<Puzzle>) : RecyclerView.Adapter<PuzzleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemBinding : ItemPuzzleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_puzzle, parent, false
        )
        return ViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return puzzleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val puzzle = puzzleList[position]
        holder.bind(puzzle)
    }

    class ViewHolder(private val binding: ItemPuzzleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(puzzle: Puzzle) {
            binding.puzzle = puzzle
            binding.executePendingBindings()
        }

        fun getPuzzle(): Puzzle? {
            return binding.puzzle
        }
    }

    fun setPuzzleList(it: ArrayList<Puzzle>) {
        puzzleList = it
        notifyDataSetChanged()
    }
}
@BindingAdapter("puzzleItems")
fun bindRecyclerViewWithPuzzleItemList(recyclerView: RecyclerView, itemList: ArrayList<Puzzle>?) {
    itemList?.let {
        recyclerView.adapter.apply {
            when (this) {
                is PuzzleAdapter -> setPuzzleList(it)
            }
        }
    }
}
