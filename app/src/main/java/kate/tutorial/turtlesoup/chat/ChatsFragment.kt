package kate.tutorial.turtlesoup.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kate.tutorial.turtlesoup.R
import kate.tutorial.turtlesoup.databinding.FragmentChatsBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val fragmentViewModel by viewModels<ChatsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChatsBinding.inflate(inflater, container, false).apply {
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
                .setPositiveButton(R.string.retry) { _, _ -> connect() }
                .setNegativeButton(R.string.finish) { _, _ -> }
                .setCancelable(true)
                .show()
        })
        binding.chatList.adapter = ChatAdapter(fragmentViewModel.chats.value ?: ArrayList())
        binding.send.setOnClickListener {
            fragmentViewModel.sendMessage(binding.edit.text.toString())
            binding.edit.text = null
        }
        connect()
    }

    private fun connect() {
        fragmentViewModel.connect()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
