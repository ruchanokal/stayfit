package com.stayfit.stayfit.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stayfit.stayfit.R
import com.stayfit.stayfit.adapter.CustomersAdapter
import com.stayfit.stayfit.adapter.RequestsAdapter
import com.stayfit.stayfit.databinding.FragmentCustomersBinding
import com.stayfit.stayfit.databinding.FragmentRequestsBinding
import com.stayfit.stayfit.model.CustomerModel
import com.stayfit.stayfit.model.RequestModel


class CustomersFragment : Fragment() {

    private var binding : FragmentCustomersBinding? = null
    private var TAG = "CustomersFragment"
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var userUid = ""
    private var customersList = arrayListOf<CustomerModel>()
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomersBinding.inflate(inflater,container,false)
        val view = binding!!.root
        registerLauncher()
        return view
    }

    private fun registerLauncher() {

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i(TAG,"izin verildi arama yapabilirsin!")
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            } else {
                Log.i(TAG,"İzin verilmedi!, lütfen izin al")
                Toast.makeText(requireContext(), "Telefon arama izni verilmedi", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun getPermission(view : View) {

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            Log.i(TAG,"henüz izin alınmadı")
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),android.Manifest.permission.CALL_PHONE)){
                Snackbar.make(view,"Arama yapabilmek için izin vermelisin", Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver") {
                    requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
                }.show()
            }else {
                requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
            }
        } else {
            Log.i(TAG,"izin alındı")
            requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userUid = mAuth.currentUser?.uid.toString()

        val customersAdapter = CustomersAdapter(customersList,this)
        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding!!.recyclerView.adapter = customersAdapter

        db.collection("WorkWith")
            .whereEqualTo("to",mAuth.currentUser!!.email)
            .whereEqualTo("result","accepted")
            .addSnapshotListener { value, error ->

                if (error != null ){
                    Toast.makeText(requireContext(),"Error: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
                } else {
                    if ( value != null){
                        if (!value.isEmpty){

                            val documents = value.documents
                            customersList.clear()
                            for (document in documents){

                                val fromNameSurname = document.getString("fromNameSurname")
                                val phoneNumber = document.getString("phoneNumber")

                                if (fromNameSurname != null && phoneNumber != null){
                                    val customerModel = CustomerModel(fromNameSurname,phoneNumber)
                                    customersList.add(customerModel)
                                }
                            }

                            customersAdapter?.notifyDataSetChanged()

                        } else {
                            customersList.clear()
                            customersAdapter?.notifyDataSetChanged()
                        }
                    }
                }

            }

    }
}