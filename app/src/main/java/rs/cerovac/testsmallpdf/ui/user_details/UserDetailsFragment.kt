package rs.cerovac.testsmallpdf.ui.user_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.cerovac.testsmallpdf.R
import rs.cerovac.testsmallpdf.data.remote.api.base.Status
import rs.cerovac.testsmallpdf.databinding.UserDetailsFragmentBinding
import rs.cerovac.testsmallpdf.utils.observeNotNull
import rs.cerovac.testsmallpdf.utils.observeNullable

class UserDetailsFragment : Fragment() {

    private val viewModel: UserDetailsViewModel by viewModel()
    lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: UserDetailsFragmentBinding =  DataBindingUtil.inflate(inflater, R.layout.user_details_fragment, container, false)
        binding.vm = viewModel
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            username = UserDetailsFragmentArgs.fromBundle(it) .username
            viewModel.getUserInfoByUsername(username)
        }

        viewModel.result.observeNotNull(viewLifecycleOwner) { state ->
            when (state.status) {
                Status.SUCCESS -> { }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), resources.getString(R.string.user_not_found), Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                Status.EMPTY -> { }
                Status.LOADING -> { }
            }
        }
    }

    object BindingLayoutUtils {
        @JvmStatic
        @BindingAdapter("avatar")
        fun loadAvatar(view: ImageView, imageUrl: String?) {
            imageUrl?.let {
                Glide.with(view.context)
                    .load(it).apply(RequestOptions().circleCrop())
                    .into(view)
            }
        }
    }

    private fun setupObservers() = with(viewModel) {
        onReposClicked.observeNullable(viewLifecycleOwner) {
            findNavController().navigate(
                UserDetailsFragmentDirections.actionNavigationUserDetailsToNavigationUserRepos(username = username))
        }
    }
}