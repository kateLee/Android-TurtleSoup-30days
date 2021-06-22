package kate.tutorial.turtlesoup.puzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import kate.tutorial.turtlesoup.OnRecyclerItemTouchListener
import kate.tutorial.turtlesoup.R
import kate.tutorial.turtlesoup.databinding.FragmentPuzzlesBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PuzzlesFragment : Fragment() {

    private var _binding: FragmentPuzzlesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val fragmentViewModel by viewModels<PuzzlesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPuzzlesBinding.inflate(inflater, container, false).apply {
            viewModel = fragmentViewModel
        }
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener { fragmentViewModel.refreshPuzzles() }
        binding.puzzleList.adapter = PuzzleAdapter(fragmentViewModel.puzzles.value ?: ArrayList())
        binding.puzzleList.addOnItemTouchListener(OnRecyclerItemTouchListener(onItemClick = { viewHolder ->
            if (viewHolder is PuzzleAdapter.ViewHolder) {
                viewHolder.getPuzzle()?.also {
                    val action =
                        PuzzlesFragmentDirections.actionPuzzlesFragmentToPuzzleFragment(it.id)
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }
        }))

        val itemDecoration = DividerItemDecoration(requireContext(), VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.diveder)!!)

        fragmentViewModel.requestError.observe(viewLifecycleOwner, {
            AlertDialog.Builder(requireContext())
                .setMessage(it)
                .setPositiveButton(R.string.retry) { _, _ -> fragmentViewModel.fetchPuzzles() }
                .setNegativeButton(R.string.finish) { _, _ -> }
                .setCancelable(true)
                .show()
        })
        fragmentViewModel.fetchPuzzles()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
