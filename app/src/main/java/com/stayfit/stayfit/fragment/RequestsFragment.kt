package com.stayfit.stayfit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stayfit.stayfit.R
import com.stayfit.stayfit.adapter.ExercisesAdapter
import com.stayfit.stayfit.adapter.RequestsAdapter
import com.stayfit.stayfit.databinding.FragmentRequestsBinding
import com.stayfit.stayfit.model.RequestModel


class RequestsFragment : Fragment() {


    private var TAG = "RequestsFragment"
    private var binding : FragmentRequestsBinding? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var userUid = ""
    private var requestsList = arrayListOf<RequestModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestsBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userUid = mAuth.currentUser?.uid.toString()

        val requestsAdapter = RequestsAdapter(requestsList)
        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding!!.recyclerView.adapter = requestsAdapter

        db.collection("WorkWith")
            .whereEqualTo("to",mAuth.currentUser!!.email)
            .whereEqualTo("result","unknown")
            .addSnapshotListener { value, error ->

            if (error != null ){
                Toast.makeText(requireContext(),"Error: ${error.localizedMessage}",Toast.LENGTH_LONG).show()
            } else {
                if ( value != null){
                    if (!value.isEmpty){

                        val documents = value.documents
                        requestsList.clear()
                        for (document in documents){

                            val fromNameSurname = document.getString("fromNameSurname")
                            val message = document.getString("message")
                            val documentId = document.id

                            if (fromNameSurname != null && message != null){
                                val requestModel = RequestModel(fromNameSurname,message,documentId)
                                requestsList.add(requestModel)
                            }
                        }

                        requestsAdapter?.notifyDataSetChanged()

                    } else {
                        requestsList.clear()
                        requestsAdapter?.notifyDataSetChanged()
                    }
                }
            }

        }


    }


}