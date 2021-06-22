package kate.tutorial.turtlesoup.puzzle

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

        fragmentViewModel.requestError.observe(viewLifecycleOwner, Observer<String> {
            AlertDialog.Builder(requireContext())
                .setMessage(it)
                .setPositiveButton(R.string.retry) { _, _ ->  }
                .setNegativeButton(R.string.finish) { _, _ -> }
                .setCancelable(true)
                .show()
        })
        fragmentViewModel.puzzle.observe(viewLifecycleOwner, Observer<PuzzleDetail> {
            requireActivity().finish()
        })
    }
    fun postPuzzle() {
        fragmentViewModel.postPuzzle(binding.title.text.toString(), binding.description.text.toString(), binding.tags.text.toString())
    }
}
