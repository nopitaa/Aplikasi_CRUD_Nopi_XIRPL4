package com.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.withContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.room.Room.Buku
import com.room.Room.BukuDB
import com.room.Room.konstan
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val db by lazy { BukuDB (this) }
    lateinit var bukuAdapter: BukuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListener()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            bukuAdapter.setData(db.BukuDao().getBukuu())
            withContext(Dispatchers.Main) {
                bukuAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupView (){
        supportActionBar!!.apply {
            title = "DATA SISWA XI RPL 4"
        }
    }

    private fun setupListener(){
        btnadd.setOnClickListener {
            intentEdit(konstan.TYPE_CREATE, 0)
        }
    }

    private fun setupRecyclerView () {

        bukuAdapter = BukuAdapter(
            arrayListOf(),
            object : BukuAdapter.OnAdapterListener {
                override fun onClick(buku: Buku) {
                    intentEdit(konstan.TYPE_READ, buku.id)
                }

                override fun onUpdate(buku: Buku) {
                    intentEdit(konstan.TYPE_UPDATE, buku.id)
                }

                override fun onDelete(buku: Buku) {
                    deleteAlert(buku)
                }

            })

        rv_buku.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = bukuAdapter
        }

    }

    private fun intentEdit(intent_type: Int, buku_id: Int) {
        startActivity(
            Intent(this, AddActivity::class.java)
                .putExtra("intent_type", intent_type)
                .putExtra("buku_id", buku_id)
        )

    }

    private fun deleteAlert(buku: Buku){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Hapus")
            setMessage("Yakin akan menghapus data  ${buku.title}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.BukuDao().deleteBuku(buku)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }

        dialog.show()
    }
}