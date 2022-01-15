package com.room.Room

import android.provider.ContactsContract
import androidx.room.*

@Dao
interface BukuDao {

    @Insert
    suspend fun addBuku(buku: Buku )

    @Update
    suspend fun updateBuku(buku: Buku )

    @Delete
    suspend fun deleteBuku(buku: Buku )

    @Query("SELECT * FROM buku ORDER BY id DESC")
    suspend fun getBukuu() : List<Buku>

    @Query("SELECT * FROM buku WHERE id=:buku_id")
    suspend fun getbuku(buku_id: Int) : List<Buku>


}