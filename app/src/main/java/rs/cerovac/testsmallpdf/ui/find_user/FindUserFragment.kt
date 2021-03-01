package rs.cerovac.testsmallpdf.ui.find_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.find_user_fragment.*
import rs.cerovac.testsmallpdf.R

class FindUserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.find_user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnFind.setOnClickListener {
            val action = FindUserFragmentDirections.actionNavigationLoginToNavigationUserDetails(etUsername.text.toString())
            findNavController().navigate(action)
        }
    }
}