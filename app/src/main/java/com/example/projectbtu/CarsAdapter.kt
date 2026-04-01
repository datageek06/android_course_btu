package com.example.projectbtu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

class CarsAdapter (var cars: List<Car>, var context: Context): RecyclerView.Adapter<CarsAdapter.MyViewHolder>() {
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.carListImage)
        val title: TextView = view.findViewById(R.id.carListTitle)
        val price: TextView = view.findViewById(R.id.carListPrice)
    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.car_design, p0, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        p1: Int
    ) {
        // მანქანის დასახელება
        holder.title.text = cars[p1].title

        // ფასი სწორ ფორმატში
        val formatter = DecimalFormat("#,###")
        val formattedPrice = formatter.format(cars[p1].price)
        holder.price.text = "$$formattedPrice"

        // სურათი
        var imageId = context.resources.getIdentifier(
            cars[p1].image,
            "drawable",
            context.packageName
        )
        holder.image.setImageResource(imageId)


        holder.itemView.setOnClickListener {
            val intent = android.content.Intent(context, PaymentActivity::class.java)

            intent.putExtra("car_item", cars[p1])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return cars.count()
    }
}