package rs.cerovac.testsmallpdf.ui.user_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.cerovac.testsmallpdf.R
import rs.cerovac.testsmallpdf.databinding.UserDetailsFragmentBinding
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            username = UserDetailsFragmentArgs.fromBundle(it) .username
            viewModel.getUserInfoByUsername(username)
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