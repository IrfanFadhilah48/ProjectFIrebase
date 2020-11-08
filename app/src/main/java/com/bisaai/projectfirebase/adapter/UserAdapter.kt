package com.bisaai.projectfirebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bisaai.projectfirebase.R
import com.bisaai.projectfirebase.model.Users
import kotlinx.android.synthetic.main.list_user.view.*

class UserAdapter (
    val dataUser: ArrayList<Users>,
    val onItemClick:(data: Users, check: Int) -> Unit
): RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataUser[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Users){
            with(itemView){
                tvUserNisn.text = data.nisn
                tvUserNama.text = data.nama
                tvUserKelas.text = data.kelas
                tvUserAlamat.text = data.alamat

                setOnClickListener {
                    onItemClick(data, 1)
                }
                ivUserDelete.setOnClickListener {
                    onItemClick(data, 2)
                }
            }
        }
    }
}