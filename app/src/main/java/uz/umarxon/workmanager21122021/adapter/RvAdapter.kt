package uz.umarxon.rxkotlinroom.Adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ocpsoft.prettytime.PrettyTime
import uz.umarxon.workmanager21122021.Helpers.HumanDateUtils
import uz.umarxon.workmanager21122021.databinding.ItemRvBinding
import uz.umarxon.workmanager21122021.db.entity.User
import java.text.SimpleDateFormat
import java.util.*

class RvAdapter(var onItemCLickListener: OnItemCLickListener): ListAdapter<User, RvAdapter.Vh>(MyDiffUtil()) {

    inner class Vh(var itemRv: ItemRvBinding): RecyclerView.ViewHolder(itemRv.root){

        fun onBind(user: User,pos:Int){
            val cal: Calendar = Calendar.getInstance(Locale.getDefault())
            cal.setTimeInMillis(timeStamp)
            val date: String = DateFormat.format("yyyy-MM-dd HH:mm:ss", cal).toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val dateObj: Date = sdf.parse(date)
            val p = PrettyTime()
            return p.format(dateObj)
            itemRv.name.text = "${pos+1}) ${user.time}  "

            itemRv.root.setOnClickListener {
                onItemCLickListener.onItemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }

    class MyDiffUtil: DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.equals(newItem)
        }

    }

    interface OnItemCLickListener{
        fun onItemClick(user: User)
    }

}