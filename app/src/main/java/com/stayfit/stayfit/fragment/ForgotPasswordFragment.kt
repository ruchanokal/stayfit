package com.stayfit.stayfit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.stayfit.stayfit.R
import com.stayfit.stayfit.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : Fragment() {

    private var binding : FragmentForgotPasswordBinding? = null
    lateinit var mAuth : FirebaseAuth
    private val TAG = "ForgotPasswordFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        binding?.sifremiUnuttumTamamButton?.setOnClickListener {


            if (!binding?.forgotPassEditText?.text?.isEmpty()!!) {

                val email = binding?.forgotPassEditText?.text.toString()

                mAuth.sendPasswordResetEmail(email).addOnFailureListener {

                    Toast.makeText(context,"E-posta gönderilemedi, tekrar deneyin!",
                        Toast.LENGTH_LONG).show()

                }.addOnSuccessListener {

                    Toast.makeText(context,"E-posta başarıyla gönderildi",
                        Toast.LENGTH_LONG).show()

                }

            } else {

                Toast.makeText(context,"Lütfen kayıtlı e-posta adresinizi girin!",
                    Toast.LENGTH_LONG).show()

            }

        }
    }

}