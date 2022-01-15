package com.room.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (
    entities = [Buku::class],
    version = 1
)
abstract class BukuDB : RoomDatabase(){
    abstract fun BukuDao() : BukuDao

    companion object{
        @Volatile private var instance : BukuDB? = null
        private val LOCK = Any()
        operator fun invoke(context: Context )= instance ?: synchronized(LOCK){
            instance?: buildDatabase(context).also{
                instance = it

            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BukuDB::class.java,
            "buku12345.db"
        ).build()
    }
}