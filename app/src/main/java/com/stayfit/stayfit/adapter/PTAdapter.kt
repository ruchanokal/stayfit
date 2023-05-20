package com.stayfit.stayfit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.stayfit.stayfit.databinding.PtRowBinding
import com.stayfit.stayfit.fragment.PersonalTrainersFragmentDirections
import com.stayfit.stayfit.model.PtModel

class PTAdapter(val ptList : ArrayList<PtModel>) : RecyclerView.Adapter<PTAdapter.PTHolder>() {

    class PTHolder(val binding : PtRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PTHolder {
        val binding = PtRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PTHolder(binding)
    }

    override fun onBindViewHolder(holder: PTHolder, position: Int) {
        holder.binding.ptNameText.text = ptList.get(position).ptName
        holder.binding.ptDescText.text = ptList.get(position).ptDesc

        holder.itemView.setOnClickListener {

            val action = PersonalTrainersFragmentDirections.actionPtFragmentToPTrainersDetailsFragment(ptList.get(position))
            Navigation.findNavController(it).navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return ptList.size
    }
}