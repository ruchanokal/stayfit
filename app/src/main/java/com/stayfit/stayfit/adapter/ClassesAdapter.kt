package com.stayfit.stayfit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.stayfit.stayfit.databinding.ClassesRowBinding
import com.stayfit.stayfit.fragment.ClassesFragmentDirections
import com.stayfit.stayfit.fragment.MyClassesFragment
import com.stayfit.stayfit.fragment.MyClassesFragmentDirections
import com.stayfit.stayfit.model.GClass

class ClassesAdapter(val classList : ArrayList<GClass>,val from : Int) : RecyclerView.Adapter<ClassesAdapter.ClassesHolder>() {

    class ClassesHolder(val binding: ClassesRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassesHolder {
        val binding = ClassesRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ClassesHolder(binding)
    }

    override fun onBindViewHolder(holder: ClassesHolder, position: Int) {

        holder.binding.classesTitleText.text = classList.get(position).title
        holder.binding.classesDescText.text = classList.get(position).desc

        holder.itemView.setOnClickListener {

            if (from == 1){

                val action = ClassesFragmentDirections.actionClassesFragmentToClassesDetailsFragment2(classList.get(position))
                Navigation.findNavController(it).navigate(action)

            } else {

                val action = MyClassesFragmentDirections.actionMyClassesFragmentToClassesDetailsFragment2(classList.get(position))
                Navigation.findNavController(it).navigate(action)
            }



        }

    }

    override fun getItemCount(): Int {
        return classList.size
    }


}