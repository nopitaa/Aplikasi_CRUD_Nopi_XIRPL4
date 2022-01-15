package com.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import com.room.Room.Buku
import com.room.Room.BukuDB
import com.room.Room.konstan
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private val db by lazy { BukuDB (this)}
    private var bukuId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupView()
        setupLstener()
    }

    private fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        when (intentType()) {
            konstan.TYPE_CREATE -> {
                supportActionBar!!.title = "TAMBAH DATA"
                btn_save.visibility = View.VISIBLE
                btn_updt.visibility = View.GONE
            }
            konstan.TYPE_READ -> {
                supportActionBar!!.title = "BACA DATA"
                btn_save.visibility = View.GONE
                btn_updt.visibility = View.GONE
                getbuku()
            }
            konstan.TYPE_UPDATE -> {
                supportActionBar!!.title = "UPDATE DATA"
                btn_save.visibility = View.GONE
                btn_updt.visibility = View.VISIBLE
                getbuku()
            }
        }
    }

    private fun setupLstener(){
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.BukuDao().addBuku(
                    Buku(
                        0,
                        et_title.text.toString(),
                        et_description.text.toString()
                    )
                )
                finish()
            }
        }
        btn_updt.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.BukuDao().updateBuku(
                    Buku(
                        bukuId,
                        et_title.text.toString(),
                        et_description.text.toString()
                    )
                )
                finish()
            }
        }
    }

    private fun getbuku(){
        bukuId = intent.getIntExtra("buku_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val buku = db.BukuDao().getbuku(bukuId).get(0)
            et_title.setText( buku.title )
            et_description.setText( buku.desc )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun intentType(): Int {
        return intent.getIntExtra("intent_type", 0)
    }
}