package com.bisaai.projectfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bisaai.projectfirebase.model.Users
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tambah_edit.*

class TambahEditActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var dataFlag: String? = null
    private var dataUser: Users? = null

    companion object{
        const val TAMBAHDATA = "tambah"
        const val EDITDATA = "edit"
        const val DATAEXTRA = "data"
        const val FLAGDATA = "flag"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_edit)

        database = FirebaseDatabase.getInstance().reference
        dataFlag = intent.getStringExtra(FLAGDATA)
        if (dataFlag == EDITDATA){
            dataUser = intent.getParcelableExtra(DATAEXTRA)
            etNisn.setText(dataUser?.nisn)
            etNama.setText(dataUser?.nama)
            etAlamat.setText(dataUser?.alamat)
            etKelas.setText(dataUser?.kelas)
        }

        btnSimpan.setOnClickListener {
            var user =  Users(
                    nisn = etNisn.text.toString(),
                    nama = etNama.text.toString(),
                    kelas = etKelas.text.toString(),
                    alamat = etAlamat.text.toString()
            )
            when(dataFlag){
                TAMBAHDATA -> {
                    val dataKey = database.push().key.toString()
                    user = user.copy(key = dataKey)
                    database.child("Users").child(dataKey).setValue(user)
                            .addOnCompleteListener {
                                Toast.makeText(
                                        this,
                                        "Berhasil Menambahkan Data",
                                        Toast.LENGTH_SHORT
                                ).show()
                                etNisn.setText("")
                                etNama.setText("")
                                etAlamat.setText("")
                                etKelas.setText("")
                                etKelas.clearFocus()
                                clTambahEdit.requestFocus()
                            }
                }
                EDITDATA -> {
                    user = user.copy(key = dataUser?.key.toString())
                    database.child("Users").child(dataUser?.key.toString()).setValue(user)
                            .addOnCompleteListener {
                                Toast.makeText(
                                        this,
                                        "Berhasil Mengupdate Data",
                                        Toast.LENGTH_SHORT
                                ).show()
                            }
                }
            }

        }
    }
}