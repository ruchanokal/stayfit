package com.stayfit.stayfit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.stayfit.stayfit.databinding.RequestsRowBinding
import com.stayfit.stayfit.model.RequestModel

class RequestsAdapter(val requestsList : ArrayList<RequestModel>) : RecyclerView.Adapter<RequestsAdapter.RequestsHolder>() {

    private lateinit var db: FirebaseFirestore

    class RequestsHolder(val binding : RequestsRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestsHolder {
        val binding = RequestsRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        db = FirebaseFirestore.getInstance()
        return RequestsHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestsHolder, position: Int) {
        holder.binding.requestNameText.text = requestsList.get(position).userNameSurname
        holder.binding.requestDetailsText.text = requestsList.get(position).userMessage
        val documentId = requestsList.get(position).documentId

        holder.binding.acceptButton.setOnClickListener {

            db.collection("WorkWith").document(documentId).update("result","accepted").addOnSuccessListener {
                Toast.makeText(holder.itemView.context,"The request accepted!",Toast.LENGTH_LONG).show()
            }

        }

        holder.binding.cancelButton.setOnClickListener {
            db.collection("WorkWith").document(documentId).update("result","rejected").addOnSuccessListener {
                Toast.makeText(holder.itemView.context,"The request rejected!",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return requestsList.size
    }


}