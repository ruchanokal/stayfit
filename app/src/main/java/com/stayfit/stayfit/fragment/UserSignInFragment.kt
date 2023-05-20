package com.stayfit.stayfit.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.stayfit.stayfit.R
import com.stayfit.stayfit.activitiy.MainActivity
import com.stayfit.stayfit.databinding.FragmentUserSignInBinding


class UserSignInFragment : Fragment() {

    private var binding : FragmentUserSignInBinding? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var reference: ListenerRegistration? = null
    private var emailList = arrayListOf<String>()
    private val TAG = "UserSignInFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSignInBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        if (mAuth.currentUser != null) {

            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("definite", 2)
            startActivity(intent)
            requireActivity().finish()

        }

        binding?.girisYapButton?.setOnClickListener { signIn() }

        binding?.kayitOlText?.setOnClickListener {

            val action = UserSignInFragmentDirections.actionUserSignInFragmentToUserSignUpFragment()
            Navigation.findNavController(it).navigate(action)

        }

        binding?.sifremiUnuttumText?.setOnClickListener {

            val action = UserSignInFragmentDirections.actionUserSignInFragmentToForgotPasswordFragment()
            Navigation.findNavController(it).navigate(action)

        }


        backButton()
    }

    private fun signIn() {

        binding?.progressBarSignIn?.visibility = View.VISIBLE
        binding?.progressBarSignIn?.translationZ = 2F
        binding?.progressBarSignIn?.elevation = 10F

        val email = binding?.editTextEmail?.text.toString()
        val password = binding?.editTextPassword?.text.toString()

        if (email.equals("") && password.equals("")) {

            Toast.makeText(
                activity, "Please fill in the required fields!",
                Toast.LENGTH_LONG
            ).show()

            binding?.progressBarSignIn?.visibility = View.GONE

        } else if (password.equals("")) {

            Toast.makeText(
                activity, "Please enter your password!",
                Toast.LENGTH_LONG
            ).show()

            binding?.progressBarSignIn?.visibility = View.GONE

        } else if (email.equals("")) {

            Toast.makeText(
                activity, "Please enter your registered e-mail address!",
                Toast.LENGTH_LONG
            ).show()

            binding?.progressBarSignIn?.visibility = View.GONE

        } else {

            val query = db.collection("User")

            reference = query.addSnapshotListener { value, error ->

                if (error != null) {

                    Toast.makeText(
                        requireContext(),
                        "Error: ${error.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    if (value != null) {

                        if (!value.isEmpty) {

                            Log.i(TAG, "user snapshotListener")

                            val documents = value.documents

                            for (document in documents) {

                                val testEmail = document.get("email") as String
                                emailList.add(testEmail)

                                if (testEmail.equals(email)) {

                                    mAuth.signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener {

                                            if (it.isSuccessful) {

                                                reference?.remove()

                                                val intent = Intent(
                                                    requireActivity(),
                                                    MainActivity::class.java
                                                )
                                                intent.putExtra("definite", 2)
                                                startActivity(intent)
                                                requireActivity().finish()
                                                binding?.progressBarSignIn?.visibility = View.GONE

                                            } else {

                                                try {
                                                    throw it.getException()!!
                                                } catch (e: FirebaseAuthUserCollisionException) {

                                                    Log.e(TAG,"error: " + e.localizedMessage)
                                                    reference?.remove()

                                                    Toast.makeText(
                                                        activity,
                                                        e.localizedMessage,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    binding?.progressBarSignIn?.visibility =
                                                        View.GONE

                                                } catch (e: FirebaseAuthEmailException) {

                                                    Log.e(TAG,"error: " + e.localizedMessage)
                                                    reference?.remove()

                                                    Toast.makeText(
                                                        activity,
                                                        e.localizedMessage,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    binding?.progressBarSignIn?.visibility =
                                                        View.GONE

                                                } catch (e: FirebaseAuthInvalidUserException) {

                                                    Log.e(TAG,"error: " + e.localizedMessage)
                                                    reference?.remove()

                                                    Toast.makeText(
                                                        activity,
                                                        "There is no user matching this email. Please try again!",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    binding?.progressBarSignIn?.visibility =
                                                        View.GONE

                                                } catch (e: FirebaseNetworkException) {

                                                    println(e)
                                                    reference?.remove()

                                                    Toast.makeText(
                                                        activity,
                                                        "Please check your internet connection!",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    binding?.progressBarSignIn?.visibility =
                                                        View.GONE

                                                } catch (e: FirebaseAuthInvalidCredentialsException) {

                                                    reference?.remove()

                                                    Toast.makeText(
                                                        activity,
                                                        e.localizedMessage,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    binding?.progressBarSignIn?.visibility =
                                                        View.GONE

                                                } catch (e: Exception) {
                                                    reference?.remove()

                                                    Toast.makeText(
                                                        activity,
                                                        e.localizedMessage,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    binding?.progressBarSignIn?.visibility =
                                                        View.GONE

                                                }

                                            }

                                        }

                                    break
                                } else
                                    Log.i(TAG, "No such email was found.")


                            }

                            val distinct = emailList.toSet().toList()

                            Log.i(TAG, "distinct: " + distinct)
                            Log.i(TAG, "email: " + email)

                            if (distinct.size > 0 && !distinct.contains(email)) {

                                Log.i(TAG, "email-2: " + email)

                                reference?.remove()
                                binding?.progressBarSignIn?.visibility = View.GONE

                                Toast.makeText(
                                    activity,
                                    "There is no user matching this email. Please try again!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                        } else {

                            reference?.remove()

                            Log.i(TAG, "No such email was found.")

                            binding?.progressBarSignIn?.visibility = View.GONE
                            Toast.makeText(
                                activity,
                                "There is no user matching this email. Please try again!",
                                Toast.LENGTH_SHORT
                            ).show()


                        }


                    } else {

                        reference?.remove()

                        Log.i(TAG, "data null")

                        binding?.progressBarSignIn?.visibility = View.GONE
                        Toast.makeText(
                            activity, "There is no such user. Please try again!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }


                }

            }

        }
    }

    private fun backButton() {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

}