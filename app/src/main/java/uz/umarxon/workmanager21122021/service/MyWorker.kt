package uz.umarxon.workmanager21122021.service

 import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import uz.umarxon.workmanager21122021.R
import uz.umarxon.workmanager21122021.db.database.AppDatabase
import uz.umarxon.workmanager21122021.db.entity.User


class MyWorker(var context: Context, workerParameters: WorkerParameters) : Worker(context,workerParameters) {
    override fun doWork(): Result {
        addNewItem()
        return Result.success()
    }

    private fun addNewItem() {
        Log.d("Murodhonov", "addNewItem: start adding")
        AppDatabase.getInstance(context).userDao().addUser(User())
        MediaPlayer.create(context, R.raw.jump).start()
        Log.d("Murodhonov", "addNewItem: sucess Added")
    }


}