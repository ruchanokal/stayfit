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
import com.stayfit.stayfit.adapter.PTAdapter
import com.stayfit.stayfit.databinding.FragmentPersonalTrainersBinding
import com.stayfit.stayfit.model.PtModel


class PersonalTrainersFragment : Fragment() {

    private var binding : FragmentPersonalTrainersBinding? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var userUid = ""
    private var ptList = arrayListOf<PtModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalTrainersBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userUid = mAuth.currentUser?.uid.toString()

        val adapter = PTAdapter(ptList)
        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding!!.recyclerView.adapter = adapter

        db.collection("PT").addSnapshotListener { value, error ->

            if (error != null){

                Toast.makeText(requireContext(),"Error: ${error.localizedMessage}",Toast.LENGTH_LONG).show()

            } else {

                if (value != null){

                    if (!value.isEmpty){

                        val documents = value.documents
                        ptList.clear()
                        for (document in documents){

                           val username = document.getString("username") as String
                           val nameSurname = document.getString("namesurname") as String
                           val email = document.getString("email") as String
                           val description = document.getString("description") as String
                           val fee = document.getString("fee") as String

                           val pt = PtModel(nameSurname,description,username,email,fee)
                           ptList.add(pt)

                        }

                        adapter?.notifyDataSetChanged()

                    }

                }

            }



        }

    }


}