package com.stayfit.stayfit.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.stayfit.stayfit.R
import com.stayfit.stayfit.activitiy.MainActivity
import com.stayfit.stayfit.databinding.FragmentPTSignUpBinding


class PTSignUpFragment : Fragment() {

    private var binding : FragmentPTSignUpBinding? = null
    private lateinit var db : FirebaseFirestore
    private val TAG = "PTSignUpFragment"
    private lateinit var mAuth: FirebaseAuth
    var reference : ListenerRegistration? = null
    var userUid = ""
    private lateinit var email : String
    private lateinit var username : String
    private lateinit var phoneNumber : String
    private lateinit var nameSurname : String
    private var pass : String = ""
    private var pass2 : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPTSignUpBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding?.ptSignUpButton?.setOnClickListener {

            binding?.progressBarSignUp?.visibility = View.VISIBLE

            email = binding?.editTextEmailSignUp?.text.toString()
            username = binding?.editTextUserNameSignUp?.text.toString()
            nameSurname = binding?.editTextNameSurnameSignUp?.text.toString()
            phoneNumber = binding?.editTextPhoneSignUp?.text.toString()
            pass = binding?.editTextPassSignUp?.text.toString()
            pass2 = binding?.editTextPassAgainSignUp?.text.toString()

            databaseCollection()

        }
    }

    private fun databaseCollection() {

        reference = db.collection("PT").whereEqualTo("username",username).addSnapshotListener { value, error ->

                Log.i(TAG,"username: " + username)

            if (value !=null) {

                Log.i(TAG,"not null")

                if (!value.isEmpty) {
                    Log.i(TAG,"not empty")

                    Toast.makeText(context,"Please try another username!",
                        Toast.LENGTH_LONG).show()

                    binding?.progressBarSignUp?.visibility = View.GONE

                    reference?.remove()

                } else {
                    Log.i(TAG,"empty")
                    kontroller()
                }

            } else {
                Log.i(TAG,"null")
                kontroller()
            }

            if (error!= null)
                Log.i(TAG,"error: " + error)

        }

    }

    private fun kontroller() {


        if (email.equals("")
            || username.equals("")
            || nameSurname.equals("")
            || phoneNumber.equals("")
            || pass.equals("")
            || pass2.equals("") ){

            reference?.remove()

            Toast.makeText(activity,"Please fill in the required fields!",Toast.LENGTH_LONG).show()

            binding?.progressBarSignUp?.visibility = View.GONE

        } else if (!pass.equals(pass2)){

            reference?.remove()

            Toast.makeText(activity,"Passwords must match!",Toast.LENGTH_LONG).show()

            binding?.progressBarSignUp?.visibility = View.GONE

        }  else  {

            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener { task ->

                if (task.isSuccessful){

                    reference?.remove()

                    userUid = mAuth.currentUser?.uid.toString()

                    binding?.progressBarSignUp?.visibility = View.GONE

                    val hashMap = hashMapOf<Any,Any>()
                    val email = mAuth.currentUser?.email

                    email?.let { hashMap.put("email", it) }
                    username.let { hashMap.put("username",it) }
                    nameSurname.let { hashMap.put("namesurname",it) }
                    hashMap.put("description",resources.getString(R.string.pt_desc))
                    hashMap.put("fee","20$")
                    hashMap.put("phonenumber",phoneNumber)

                    db.collection("PT").document(userUid).set(hashMap).addOnSuccessListener {

                        Log.i(TAG,"added one user more")

                        val intent = Intent(activity, MainActivity::class.java)
                        intent.putExtra("username",username)
                        intent.putExtra("namesurname",nameSurname)
                        intent.putExtra("description",resources.getString(R.string.pt_desc))
                        intent.putExtra("fee","20$")
                        intent.putExtra("phonenumber",phoneNumber)
                        intent.putExtra("definite",3)
                        startActivity(intent)
                        requireActivity().finish()

                        Toast.makeText(activity,"Welcome ${username}",Toast.LENGTH_LONG).show()

                    }.addOnFailureListener {

                        Log.e(TAG,"Failed to create user, try again!")

                    }



                }

            }.addOnFailureListener { exception ->


                try {
                    throw exception
                }   catch (e: FirebaseAuthUserCollisionException) {

                    reference?.remove()

                    Toast.makeText(activity,"This email address is already in use by another account",Toast.LENGTH_LONG).show()
                    binding?.progressBarSignUp?.visibility = View.GONE

                } catch(e : FirebaseAuthWeakPasswordException) {

                    reference?.remove()

                    Toast.makeText(activity,"Please enter a password of at least 6 digits",Toast.LENGTH_LONG).show()
                    binding?.progressBarSignUp?.visibility = View.GONE

                } catch (e: FirebaseNetworkException) {

                    reference?.remove()

                    Toast.makeText(activity,"Please check your internet connection",Toast.LENGTH_LONG).show()
                    binding?.progressBarSignUp?.visibility = View.GONE

                } catch(e : FirebaseAuthInvalidCredentialsException) {

                    reference?.remove()

                    Toast.makeText(activity,e.localizedMessage,Toast.LENGTH_LONG).show()
                    binding?.progressBarSignUp?.visibility = View.GONE

                } catch (e: Exception) {

                    reference?.remove()

                    Toast.makeText(activity,e.localizedMessage,Toast.LENGTH_LONG).show()
                    binding?.progressBarSignUp?.visibility = View.GONE
                }

            }


        }
    }


}