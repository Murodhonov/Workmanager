package uz.umarxon.workmanager21122021.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Flowable
import uz.umarxon.workmanager21122021.db.entity.User

@Dao
interface UserDao {
    @Insert
    fun addUser(user: User)

    @Query("select * from user")
    fun getAll(): Flowable<List<User>>


}