package rs.cerovac.testsmallpdf.ui.find_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.find_user_fragment.*
import rs.cerovac.testsmallpdf.R

class FindUserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.find_user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnFind.setOnClickListener {
            val username = etUsername.text.toString()
            if (username.isEmpty()){
                Toast.makeText(requireContext(), resources.getString(R.string.username_is_required), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val action = FindUserFragmentDirections.actionNavigationLoginToNavigationUserDetails(username)
            findNavController().navigate(action)
        }
    }
}