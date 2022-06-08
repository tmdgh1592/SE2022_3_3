package com.se.hanger.view.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.se.hanger.R
import com.se.hanger.data.model.Cloth
import com.se.hanger.databinding.ItemClothBinding
import com.se.hanger.view.cloth.OnClickDeleteButton

class ClothRvAdapter(val clothList: MutableList<Cloth>) :
    RecyclerView.Adapter<ClothRvAdapter.ClothViewHolder>() {

    private var onClickDeleteButton: OnClickDeleteButton? = null

    fun setClickListener(onClickDeleteButton: OnClickDeleteButton) {
        this.onClickDeleteButton = onClickDeleteButton
    }

    inner class ClothViewHolder(val binding: ItemClothBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val clothModel = clothList[position]

            clothModel.let {
                with(binding) {
                    Log.d("clothPhotoUri", "bind: " + it.clothPhoto.photoUriString!!)

                    Glide.with(clothIv.context).load(it.clothPhoto.photoUriString!!).placeholder(R.drawable.sample_cloth) .into(clothIv)
                    clothNameTv.text = it.clothName
                    deleteBtn.setOnClickListener {
                        onClickDeleteButton?.delete(clothModel)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothViewHolder {
        val binding = ItemClothBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClothViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClothViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return clothList.size
    }

    fun updateItem(newClothList: List<Cloth>) {
        clothList.clear()
        clothList.addAll(newClothList)
        notifyDataSetChanged()
    }
}