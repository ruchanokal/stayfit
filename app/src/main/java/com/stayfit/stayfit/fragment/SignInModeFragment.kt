package com.stayfit.stayfit.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.stayfit.stayfit.R
import com.stayfit.stayfit.activitiy.MainActivity
import com.stayfit.stayfit.databinding.FragmentSignInModeBinding


class SignInModeFragment : Fragment() {

    private var binding : FragmentSignInModeBinding? = null
    private lateinit var mAuth : FirebaseAuth
    var loginType = ""
    var value = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInModeBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val prefences = requireActivity().getSharedPreferences("com.stayfit.stayfit", Context.MODE_PRIVATE)
        hizliGiris(prefences)

        binding!!.userLoginButton.setOnClickListener {

            val action = SignInModeFragmentDirections.actionSignInModeFragmentToUserSignInFragment()
            Navigation.findNavController(it).navigate(action)
            prefences.edit().putString("login","user").apply()

        }

        binding!!.ptLoginButton.setOnClickListener {

            val action = SignInModeFragmentDirections.actionSignInModeFragmentToPTSignInFragment()
            Navigation.findNavController(it).navigate(action)
            prefences.edit().putString("login","pt").apply()

        }

    }

    private fun hizliGiris(prefences  : SharedPreferences) {
        if (mAuth.currentUser != null ) {

            loginType = prefences.getString("login","")!!

            if (loginType.equals("user")) {
                value = 2
            } else if (loginType.equals("pt")){
                value = 4
            } else
                value = 0

            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("definite",value)
            startActivity(intent)
            requireActivity().finish()

        }
    }



}