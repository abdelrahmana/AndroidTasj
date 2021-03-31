package com.seven.delivery29.normaluser.bottomnav.homeuser.allservices

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtask.R
import com.example.androidtask.databinding.CarItemBinding
import com.example.androidtask.home.model.Data
import com.example.androidtask.util.ViewModelHandleChangeFragmentclass

class CarsAdaptor(
    var context: Context, var arrayList: ArrayList<Data>, val viewModelHandleChangeFragmentclass: ViewModelHandleChangeFragmentclass
) :
    RecyclerView.Adapter<CarsAdaptor.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       val view =  CarItemBinding.inflate(LayoutInflater.from(context),parent,false)

   //     view.layoutParams.width =  ((UtilKotlin.getScreenWidth(context) / numberToDisplay).toInt()) /// THIS LINE WILL DIVIDE OUR VIEW INTO NUMBERS OF PARTS
        return ViewHolder(
            view
        )
    }


    override fun getItemCount(): Int {

        return arrayList.size

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(arrayList[position])

    }
    fun updateData(newList: List<Data>) {
        val start = itemCount
        if (newList != null) {
            if (!newList.isEmpty()) { // if this is the first time
                if (arrayList.size > 0) // has old data
                {
                    this.arrayList.addAll(newList)
                    notifyItemRangeInserted(start, arrayList.size - 1)

                } else {
                    this.arrayList.addAll(newList)
                    notifyDataSetChanged()
                }
            }
        }
    }
   inner class ViewHolder(val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(
            carItem: Data
        ) {

            Glide.with(itemView.context!!).load(carItem.imageUrl?:"").error(R.drawable.car).into(binding.carImage)
            binding.brand.text = carItem.brand?:""
            binding.constructionDate.text = carItem.constractionYear?:""
            binding.isUsed.text = (carItem.isUsed?:false).toString()

            itemView.setOnClickListener{
           //     viewModelHandleChangeFragmentclass?.setNotifyItemSelected(adapterPosition)
             //   onRecyclerItemClickListener?.onItemClick(adapterPosition)
            }

        }



    }


}