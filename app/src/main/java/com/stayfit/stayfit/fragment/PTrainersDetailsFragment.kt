package com.stayfit.stayfit.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stayfit.stayfit.R
import com.stayfit.stayfit.databinding.DialogWorkWithBinding
import com.stayfit.stayfit.databinding.FragmentPTrainersDetailsBinding
import com.stayfit.stayfit.databinding.PtRowBinding
import java.text.SimpleDateFormat
import java.util.*


class PTrainersDetailsFragment : Fragment() {

    private var binding : FragmentPTrainersDetailsBinding? = null
    var dialog = activity?.let { Dialog(it) }
    private val TAG = "PTrainersDetailsFragment"
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var userUid = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPTrainersDetailsBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userUid = mAuth.currentUser?.uid.toString()

        arguments?.let {

            dialog = activity?.let { Dialog(it) }

            val ptModel = PTrainersDetailsFragmentArgs.fromBundle(it).ptmodel

            binding!!.feeText.text = ptModel.ptFee
            binding!!.ptNameSurnameText.text = ptModel.ptName
            binding!!.ptDescText.text = ptModel.ptDesc

            binding!!.workWithText.setOnClickListener {

                Log.i(TAG,"workWithText is clicked")

                if (dialog != null){

                    Log.i(TAG,"dialog is not null")

                    if (dialog!!.isShowing)
                        dialog!!.dismiss()

                    dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    val binding2 : DialogWorkWithBinding = DialogWorkWithBinding.inflate(layoutInflater)
                    dialog!!.setContentView(binding2.root)

                    binding2.sendButton.setOnClickListener {

                        if (binding2.textInputEditText.text.toString().isNotEmpty()){

                            val hashMap = hashMapOf<Any,Any>()

                            hashMap.put("fromEmail",mAuth.currentUser!!.email!!)
                            hashMap.put("to",ptModel.ptEmail)
                            hashMap.put("message",binding2.textInputEditText.text.toString())
                            hashMap.put("phoneNumber",phoneNumber)
                            hashMap.put("result","unknown")

                            if (namesurname.isNotEmpty())
                                hashMap.put("fromNameSurname", namesurname)

                            db.collection("WorkWith").document(userUid).set(hashMap).addOnSuccessListener {
                                Toast.makeText(requireContext(),"Your message sent successfully!",Toast.LENGTH_LONG).show()
                                dialog!!.dismiss()
                            }

                        } else {
                            Toast.makeText(requireContext(),"Please fill in the required field!!",Toast.LENGTH_LONG).show()
                        }

                    }


                    dialog!!.show()

                }

            }


        }

    }

}