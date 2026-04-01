package com.example.projectbtu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val carsList: RecyclerView = findViewById(R.id.carsList)
        val cars = arrayListOf<Car>()

        // მონაცემების დამატება Car კლასის მეშვეობით
        cars.add(Car(1, "bmw", "BMW M3 (F80 generation)", 38000.0))
        cars.add(Car(2, "mercedes", "Mercedes-Benz CLA-Class (Second Generation)", 46400.0))
        cars.add(Car(3, "porsche", "Porsche 911 GT3 RS (991.1 Generation)", 189000.0))
        cars.add(Car(4, "ferrari", "Ferrari 488 Spider", 260000.0))

        // 2 სვეტი
        carsList.layoutManager = GridLayoutManager(this, 2)
        // Adapter-ს ვიყებენ რომ შევცვალო RecyclerView-ის სტანდარტული დიზაინი
        carsList.adapter = CarsAdapter(cars, this)
    }
}