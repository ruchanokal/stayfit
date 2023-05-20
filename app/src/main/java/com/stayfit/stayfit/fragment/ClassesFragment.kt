package com.stayfit.stayfit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayfit.stayfit.R
import com.stayfit.stayfit.adapter.ClassesAdapter
import com.stayfit.stayfit.databinding.FragmentClassesBinding
import com.stayfit.stayfit.model.GClass
import com.stayfit.stayfit.model.InnerClass
import com.stayfit.stayfit.util.Utils.classesList


class ClassesFragment : Fragment() {

    private var binding : FragmentClassesBinding? = null
    private var classesAdapter : ClassesAdapter? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassesBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        classesAdapter = ClassesAdapter(classesList,1)
        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding!!.recyclerView.adapter = classesAdapter

    }

}