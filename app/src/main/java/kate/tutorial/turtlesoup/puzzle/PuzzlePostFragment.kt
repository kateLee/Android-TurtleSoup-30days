package kate.tutorial.turtlesoup.puzzle

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import kate.tutorial.turtlesoup.R
import kate.tutorial.turtlesoup.databinding.FragmentPostPuzzleBinding

class PuzzlePostFragment : Fragment() {
    private var _binding: FragmentPostPuzzleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val fragmentViewModel by viewModels<PuzzlePostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostPuzzleBinding.inflate(inflater, container, false).apply {
            viewModel = fragmentViewModel
        }
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.requestError.observe(viewLifecycleOwner, {
            Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
        })
        fragmentViewModel.puzzle.observe(viewLifecycleOwner, {
            Toast.makeText(requireActivity(), R.string.success_puzzle_post, Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        })
    }
    fun postPuzzle() {
        if (binding.title.text.toString().trim().isEmpty() || binding.description.text.toString().trim().isEmpty() || binding.tags.text.toString().trim().isEmpty()) {
            Snackbar.make(binding.title, R.string.success_puzzle_post, Snackbar.LENGTH_SHORT).show()
            return
        }
        fragmentViewModel.postPuzzle(binding.title.text.toString(), binding.description.text.toString(), binding.tags.text.toString())
    }
}
