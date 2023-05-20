package com.stayfit.stayfit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stayfit.stayfit.databinding.CustomerRowBinding
import com.stayfit.stayfit.fragment.CustomersFragment
import com.stayfit.stayfit.model.CustomerModel

class CustomersAdapter(val customersList : ArrayList<CustomerModel>, val fragment : CustomersFragment) : RecyclerView.Adapter<CustomersAdapter.CustomerHolder>() {

    class CustomerHolder(val binding : CustomerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerHolder {
        val binding = CustomerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomerHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerHolder, position: Int) {
        holder.binding.nameText.text = customersList.get(position).name
        holder.binding.callImageView.setOnClickListener {
            fragment.getPermission(it)
        }

    }

    override fun getItemCount(): Int {
        return customersList.size
    }
}