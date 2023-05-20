package com.stayfit.stayfit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stayfit.stayfit.databinding.ExercisesRowBinding
import com.stayfit.stayfit.model.InnerClass

class ExercisesAdapter(val innerClassList : ArrayList<InnerClass>) : RecyclerView.Adapter<ExercisesAdapter.ExercisesHolder>() {

    class ExercisesHolder(val binding: ExercisesRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesHolder {
        val binding = ExercisesRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExercisesHolder(binding)
    }

    override fun onBindViewHolder(holder: ExercisesHolder, position: Int) {

        holder.binding.exerciseTitleText.text = innerClassList.get(position).title
        if (innerClassList.get(position).sets != null && innerClassList.get(position).reps != null && innerClassList.get(position).rest != null){
            holder.binding.exerciseDescText.text = "Sets = ${innerClassList.get(position).sets}  Reps = ${innerClassList.get(position).reps}  Rest = ${innerClassList.get(position).rest}"
        } else if (innerClassList.get(position).sets == null && innerClassList.get(position).reps == null && innerClassList.get(position).rest == null) {
            holder.binding.exerciseDescText.text = "Time = ${innerClassList.get(position).time}"
        } else if (innerClassList.get(position).sets == null && innerClassList.get(position).reps != null && innerClassList.get(position).rest != null) {
            holder.binding.exerciseDescText.text = "Reps = ${innerClassList.get(position).reps}  Rest = ${innerClassList.get(position).rest}"
        }
        holder.binding.exercisesImageView.setImageResource(innerClassList.get(position).drawable)
    }

    override fun getItemCount(): Int {
        return innerClassList.size
    }
}