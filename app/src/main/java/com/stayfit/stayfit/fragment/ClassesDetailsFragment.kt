package com.stayfit.stayfit.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.stayfit.stayfit.R
import com.stayfit.stayfit.adapter.ClassesAdapter
import com.stayfit.stayfit.adapter.ExercisesAdapter
import com.stayfit.stayfit.databinding.FragmentClassesDetailsBinding


class ClassesDetailsFragment : Fragment() {

    private var binding : FragmentClassesDetailsBinding? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var userUid = ""
    val indexList = arrayListOf<Int>()
    private val TAG = "ClassesDetailsFragment"
    var reference: ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassesDetailsBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userUid = mAuth.currentUser?.uid.toString()

        arguments?.let {

            val gclass = ClassesDetailsFragmentArgs.fromBundle(it).gclass
            val exercisesAdapter = ExercisesAdapter(gclass.innerClass)
            binding!!.classNameText.text = gclass.title
            binding!!.classDescText.text = gclass.desc
            binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding!!.recyclerView.adapter = exercisesAdapter
            binding!!.addThisClassButton.visibility = View.GONE

            reference = db.collection("User").document(userUid).addSnapshotListener { value, error ->

                if (value != null){

                    if (value.exists()){

                         val listFromDB = value.get("myclasses") as? ArrayList<Long>
                         binding!!.addThisClassButton.visibility = View.VISIBLE

                         if (listFromDB != null){

                             if (listFromDB.contains(gclass.index)){
                                 binding!!.addThisClassButton.text = resources.getString(R.string.exit_my_classes)
                                 binding!!.addThisClassButton.setOnClickListener {

                                     db.collection("User").document(userUid).update("myclasses", FieldValue.arrayRemove(gclass.index)).addOnSuccessListener {
                                         Log.i(TAG,"index çıkarıldı -> " + gclass.index)
                                     }

                                 }
                             } else {
                                 binding!!.addThisClassButton.text = resources.getString(R.string.add_to_my_classes)
                                 binding!!.addThisClassButton.setOnClickListener {

                                     db.collection("User").document(userUid).update("myclasses", FieldValue.arrayUnion(gclass.index)).addOnSuccessListener {
                                         Log.i(TAG,"index eklendi -> " + gclass.index)
                                     }

                                 }
                             }

                         } else {

                             binding!!.addThisClassButton.text = resources.getString(R.string.add_to_my_classes)
                             binding!!.addThisClassButton.setOnClickListener {

                                 db.collection("User").document(userUid).update("myclasses", FieldValue.arrayUnion(gclass.index)).addOnSuccessListener {
                                     Log.i(TAG,"index eklendi -> " + gclass.index)
                                 }

                             }

                         }

                    }else {
                        binding!!.addThisClassButton.visibility = View.GONE
                    }

                } else {
                    binding!!.addThisClassButton.visibility = View.GONE
                }
            }




        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        reference?.remove()
    }
}