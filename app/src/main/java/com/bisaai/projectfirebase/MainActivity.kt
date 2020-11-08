package com.bisaai.projectfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bisaai.projectfirebase.adapter.UserAdapter
import com.bisaai.projectfirebase.model.Users
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var firebase: FirebaseDatabase
    private lateinit var database: DatabaseReference
    private lateinit var mAdapter: UserAdapter

    private var dataUser = arrayListOf<Users>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebase = FirebaseDatabase.getInstance()
        database = firebase.reference

        setRecyclerView()
        getData()

        fabMain.setOnClickListener {
            val intent = Intent(this, TambahEditActivity::class.java)
            intent.putExtra(TambahEditActivity.FLAGDATA, TambahEditActivity.TAMBAHDATA)
            startActivity(intent)
            finish()
        }
    }

    private fun setRecyclerView() {
        val mLayoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        mAdapter = UserAdapter(dataUser){ data, check ->
            when(check){
                1 -> {
                    val intent = Intent(this, TambahEditActivity::class.java)
                    intent.putExtra(TambahEditActivity.FLAGDATA, TambahEditActivity.EDITDATA)
                    intent.putExtra(TambahEditActivity.DATAEXTRA, data)
                    startActivity(intent)
                    finish()
                }
                2 -> {
                    database.child("Users").child(data.key).removeValue()
                            .addOnSuccessListener {
                                Toast.makeText(
                                        this,
                                        "Data berhasil di hapus",
                                        Toast.LENGTH_SHORT
                                ).show()
                            }
                    dataUser.clear()

                }
            }
        }
        rvMain.apply {
            layoutManager = mLayoutManager
            addItemDecoration(decoration)
            adapter = mAdapter
        }
    }

    private fun getData() {
        database.child("Users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val siswa: Users? = data.getValue(Users::class.java)
                    siswa?.let {
                        dataUser.add(it)
                    }
                }
                mAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}