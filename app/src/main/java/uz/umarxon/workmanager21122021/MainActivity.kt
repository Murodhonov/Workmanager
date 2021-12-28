package uz.umarxon.workmanager21122021

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottomsheet.view.*
import uz.umarxon.rxkotlinroom.Adapter.RvAdapter
import uz.umarxon.workmanager21122021.databinding.ActivityMainBinding
import uz.umarxon.workmanager21122021.db.database.AppDatabase
import uz.umarxon.workmanager21122021.db.entity.User
import uz.umarxon.workmanager21122021.service.MyWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var rvAdapter: RvAdapter
    lateinit var appDatabase: AppDatabase
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MySharedPreference.init(this)
        if (MySharedPreference.isFirst){
            binding.btn.visibility = View.VISIBLE
        }else{
            binding.btn.visibility = View.GONE
        }

        binding.btn.setOnClickListener {
            MySharedPreference.isFirst = false
            binding.btn.visibility = View.GONE
            val build = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(this).enqueue(build)
        }

        appDatabase = AppDatabase.getInstance(this)
        rvAdapter = RvAdapter(object : RvAdapter.OnItemCLickListener {
            override fun onItemClick(user: User) {

                val d = BottomSheetDialog(this@MainActivity, R.style.SheetDialog)

                val i = LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.bottomsheet, null, false)

                d.setContentView(i)

                i.time.text = user.time

                i.yoq.setOnClickListener {
                    d.hide()
                }

                d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                i.rootView.startAnimation(
                    AnimationUtils.loadAnimation(
                        this@MainActivity,
                        R.anim.scale
                    )
                )

                d.setCancelable(true)

                d.show()
            }
        })

        appDatabase.userDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<List<User>>,
                io.reactivex.functions.Consumer<List<User>> {
                override fun accept(t: List<User>) {
                    rvAdapter.submitList(t)
                }
            })

        binding.rv.adapter = rvAdapter






    }

}



object MySharedPreference {
    private val NAME = "forCache"
    private val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operations: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operations(editor)
        editor.apply()
    }

    var isFirst: Boolean
        get() = preferences.getBoolean("first", true)
        set(value) {
            preferences.edit().putBoolean("first", value).apply()
        }

}