package com.stayfit.stayfit.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.stayfit.stayfit.activitiy.SignActivity
import com.stayfit.stayfit.databinding.FragmentMainBinding
import kotlin.math.sign

var namesurname = ""
var phoneNumber = ""

class MainFragment : Fragment() {

    private var binding : FragmentMainBinding? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var userUid = ""
    private lateinit var alertDialog: AlertDialog.Builder
    private var TAG = "MainFragment"
    private var description = ""
    private var fee = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userUid = mAuth.currentUser?.uid.toString()

        databaseCollection()

        signOut()

    }

    private fun databaseCollection() {

        val intent = requireActivity().intent
        val definiteNumber = intent.getIntExtra("definite",0)

        Log.i(TAG,"definiteNumber --> " + definiteNumber)

        if ( definiteNumber == 1) {

            binding!!.ptLayout.visibility = View.GONE
            namesurname = intent.getStringExtra("namesurname")!!
            phoneNumber = intent.getStringExtra("phonenumber")!!
            binding?.welcomeText?.text = namesurname
            Log.i(TAG,"yeni oluşturulan kullanıcı --> " + namesurname)

        } else if ( definiteNumber == 2) {

            binding!!.ptLayout.visibility = View.GONE
            Log.i(TAG,"kullanıcı giriş yaptı --> " + mAuth.currentUser?.email)
            getDatas(2)

        } else if ( definiteNumber == 3) {

            Log.i(TAG,"yeni oluşturulan (pt) kullanıcı --> " + namesurname)
            binding!!.userLayout.visibility = View.GONE
            namesurname = intent.getStringExtra("namesurname")!!
            description = intent.getStringExtra("description")!!
            phoneNumber = intent.getStringExtra("phonenumber")!!
            fee = intent.getStringExtra("fee")!!

            binding?.ptNameSurnameText?.text = namesurname
            binding?.ptFeeText?.setText(fee)
            binding?.ptDescText?.setText(description)

            changeFeeAndDesc(fee,description,3)

        } else if ( definiteNumber == 4) {

            Log.i(TAG,"(pt) kullanıcı giriş yaptı --> " + mAuth.currentUser?.email)
            binding!!.userLayout.visibility = View.GONE
            getDatas(4)

        }

    }

    private fun changeFeeAndDesc(fee: String, description: String, loginInd : Int) {

        var newFee = fee
        var newDesc = description

        binding!!.ptFeeText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                newFee = p0.toString()
            }
        })

        binding!!.ptDescText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                newDesc = p0.toString()
            }
        })

        binding!!.editFeeButton.setOnClickListener {

            binding!!.ptFeeText.isEnabled = !binding!!.ptFeeText.isEnabled

            if (!binding!!.ptFeeText.isEnabled && !newFee.equals(fee)){
                Log.i(TAG,"ptFeeText changed && ptFeeText disabled")
                if (loginInd==3)
                    this.fee = newFee

                db.collection("PT").document(userUid).update("fee",newFee).addOnSuccessListener {
                    Toast.makeText(requireContext(),"The fee has changed! ",Toast.LENGTH_LONG).show()
                }

            } else {
                binding!!.ptFeeText.isFocusable = true
            }

        }

        binding!!.editAboutMeButton.setOnClickListener {

            binding!!.ptDescText.isEnabled = !binding!!.ptDescText.isEnabled

            if (!binding!!.ptDescText.isEnabled && !newDesc.equals(description) ){
                Log.i(TAG,"ptDescText changed && ptDescText disabled")
                if (loginInd==3)
                    this.description = newDesc

                db.collection("PT").document(userUid).update("description",newDesc).addOnSuccessListener {
                    Toast.makeText(requireContext(),"About me text has changed! ",Toast.LENGTH_LONG).show()
                }
            }else {
                binding!!.ptDescText.isFocusable = true
            }
        }


    }

    private fun getDatas(defNum : Int) {

        if (defNum == 2){

            Log.i(TAG,"defNum: ${defNum}, user db")

            db.collection("User").document(userUid).addSnapshotListener { value, error ->

                if (error != null){
                    Toast.makeText(requireContext(),"Error: ${error.localizedMessage}",Toast.LENGTH_LONG).show()
                } else {
                    if (value != null) {
                        if (value.exists()) {
                            val name = value.get("namesurname") as? String
                            Log.i(TAG,"name: " + name)
                            binding!!.welcomeText.text = name
                        }
                    }
                }

            }

        } else {

            Log.i(TAG,"defNum: ${defNum}, pt db")

            db.collection("PT").document(userUid).addSnapshotListener { value, error ->

                if (error != null){
                    Toast.makeText(requireContext(),"Error: ${error.localizedMessage}",Toast.LENGTH_LONG).show()
                } else {
                    if (value != null) {
                        if (value.exists()) {
                            val name = value.get("namesurname") as? String
                            val desc = value.get("description") as? String
                            val fee = value.get("fee") as? String

                            Log.i(TAG,"name: " + name)
                            Log.i(TAG,"desc: " + desc)
                            Log.i(TAG,"fee: " + fee)

                            binding!!.ptNameSurnameText.text = name
                            binding!!.ptDescText.setText(desc)
                            binding!!.ptFeeText.setText(fee)

                            if (fee != null && desc != null)
                                changeFeeAndDesc(fee,desc,4)

                        }
                    }
                }
            }

        }


    }

    private fun signOut() {

        binding?.signOutButton?.setOnClickListener {

            alertDialog = AlertDialog.Builder(requireContext())

            alertDialog.setTitle(getString(R.string.exitstring))
            alertDialog.setMessage(getString(R.string.exit_desc))
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton(getString(R.string.yesstring)) { dialog,which ->

                mAuth.signOut()
                val intent = Intent(requireActivity(),SignActivity::class.java)
                startActivity(intent)
                requireActivity().finish()

            }.setNeutralButton(getString(R.string.cancelstring)) { dialog,which ->


            }

            alertDialog.show()


        }
    }

}