package com.`as`.personalfinancemanager

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentB : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FragmentA-დან მონაცემების მიღება
        val salary = arguments?.getDouble("salary") ?: 0.0
        val rent = arguments?.getDouble("rent")   ?: 0.0
        val meals = arguments?.getDouble("meals")  ?: 0.0

        val model = FinanceModel(salary, rent, meals)
        val manager = FinanceManager()

        // გამოთვლები
        val totalExpenses = manager.getTotalExpenses(model)
        val balance = manager.getBalance(model)
        val savings = manager.getSavingsAmount(model)
        val savingsPct = manager.getSavingsPercent()
        val isMinus = manager.isMinus(model)

        val firstName = manager.firstName
        val lastName  = manager.lastName
        val birthYear = manager.birthDate.split("/")[2]

        // View-ები
        val tvSalary = view.findViewById<TextView>(R.id.as_text_salary_result)
        val tvExpenses = view.findViewById<TextView>(R.id.as_text_expenses_result)
        val tvBalance = view.findViewById<TextView>(R.id.as_text_balance_result)
        val tvSavings = view.findViewById<TextView>(R.id.as_text_savings_result)
        val tvStatus = view.findViewById<TextView>(R.id.as_text_status)
        val tvUser = view.findViewById<TextView>(R.id.as_text_user)
        val btnBack = view.findViewById<Button>(R.id.as_btn_back)


        tvSalary.text = "ხელფასი: $${"%.2f".format(salary)}"
        tvExpenses.text = "ხარჯი: $${"%.2f".format(totalExpenses)}"
        tvBalance.text = "ბალანსი: $${"%.2f".format(balance)}"
        tvSavings.text = "დანაზოგის პროცენტი ($savingsPct%): $${"%.2f".format(savings)}"

        // ვიზუალური ვალიდაცია
        if (isMinus) {
            tvBalance.setTextColor(Color.parseColor("#D32F2F"))
            tvStatus.text = "ცუდად გაქვს საქმე, მეგობარო"
            tvStatus.setTextColor(Color.parseColor("#D32F2F"))
        } else {
            tvBalance.setTextColor(Color.parseColor("#2E7D32"))
            tvStatus.text = "ყველაფერი რიგზეა!"
            tvStatus.setTextColor(Color.parseColor("#2E7D32"))
        }

        // Dynamic Identity
        tvUser.text = "$firstName $lastName, $birthYear"

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}