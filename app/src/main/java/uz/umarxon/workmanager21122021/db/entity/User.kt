package uz.umarxon.workmanager21122021.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
class User {

    @PrimaryKey
    var id:Int? = null

    var time = SimpleDateFormat("dd-MM-yyyy hh:mm:ssSSS").format(Date())

}