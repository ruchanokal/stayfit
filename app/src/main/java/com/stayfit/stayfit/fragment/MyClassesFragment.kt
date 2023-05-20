package com.stayfit.stayfit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.stayfit.stayfit.R
import com.stayfit.stayfit.adapter.ClassesAdapter
import com.stayfit.stayfit.databinding.FragmentMyClassesBinding
import com.stayfit.stayfit.model.GClass
import com.stayfit.stayfit.util.Utils.classesList


class MyClassesFragment : Fragment() {

    private var binding : FragmentMyClassesBinding? = null
    private var classesAdapter : ClassesAdapter? = null
    private var myClassesList = arrayListOf<GClass>()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var userUid = ""
    val indexList = arrayListOf<Int>()
    private val TAG = "MyClassesFragment"
    var reference: ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyClassesBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userUid = mAuth.currentUser?.uid.toString()

        classesAdapter = ClassesAdapter(myClassesList,2)
        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding!!.recyclerView.adapter = classesAdapter


        db.collection("User").document(userUid).addSnapshotListener { value, error ->

            if (value != null){
                if (value.exists()){

                    val myIndexList = value.get("myclasses") as? ArrayList<Long>

                    if (myIndexList != null){

                        myClassesList.clear()
                        for (index in myIndexList){
                            myClassesList.add(classesList.get(index.toInt()))
                        }
                        classesAdapter!!.notifyDataSetChanged()

                    } else {


                    }

                }
            }


        }

    }

}