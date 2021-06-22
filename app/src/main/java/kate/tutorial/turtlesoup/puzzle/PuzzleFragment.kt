package kate.tutorial.turtlesoup.puzzle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kate.tutorial.turtlesoup.databinding.FragmentPuzzleBinding

/**
= * Use the [PuzzleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PuzzleFragment : Fragment() {
    private var _binding: FragmentPuzzleBinding? = null
    private val args: PuzzleFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val fragmentViewModel by viewModels<PuzzleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPuzzleBinding.inflate(inflater, container, false).apply {
            viewModel = fragmentViewModel
        }
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.fetchPuzzle(args.puzzleId)
    }
}
