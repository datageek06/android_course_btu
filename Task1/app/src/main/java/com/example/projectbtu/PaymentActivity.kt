package com.example.projectbtu

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class PaymentActivity : AppCompatActivity() {

    private var discountedPrice: Double = 0.0
    private var shippingFee: Double = 1700.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val car = intent.getSerializableExtra("car_item") as? Car ?: return

        // ფასდაკლების ლოგიკა (5%)
        discountedPrice = car.price * 0.95

        // UI ელემენტები
        val imgCar: ImageView = findViewById(R.id.paymentCarImage)
        val tvTitle: TextView = findViewById(R.id.paymentCarTitle)
        val tvPrice: TextView = findViewById(R.id.paymentCarPrice)
        val tvTotal: TextView = findViewById(R.id.tvFinalTotal)
        val shippingGroup: RadioGroup = findViewById(R.id.shippingRadioGroup)
        val btnPay: Button = findViewById(R.id.btnPay)

        // მონაცემების შევსება
        tvTitle.text = car.title
        val formatter = DecimalFormat("#,###")
        tvPrice.text = "$${formatter.format(car.price)}"

        val imageId = resources.getIdentifier(car.image, "drawable", packageName)
        imgCar.setImageResource(imageId)

        // საწყისი ჯამური ფასი
        updateTotalDisplay(tvTotal)

        // მიწოდების პირობის შეცვლა
        shippingGroup.setOnCheckedChangeListener { _, checkedId ->
            shippingFee = if (checkedId == R.id.radioExpress) 1700.0 else 0.0
            updateTotalDisplay(tvTotal)
        }

        btnPay.setOnClickListener {
            val intent = Intent(this, FinalActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateTotalDisplay(tvTotal: TextView) {
        val total = discountedPrice + shippingFee
        tvTotal.text = "$${DecimalFormat("#,###").format(total)}"
    }
}