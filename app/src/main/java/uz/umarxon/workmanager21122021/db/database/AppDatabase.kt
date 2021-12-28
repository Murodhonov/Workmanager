package uz.umarxon.workmanager21122021.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.umarxon.workmanager21122021.db.dao.UserDao
import uz.umarxon.workmanager21122021.db.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instanse: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {

            when (instanse) {
                null -> {
                    instanse = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return instanse!!
        }

    }
}