package com.`as`.personalfinancemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragmentA : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editSalary = view.findViewById<EditText>(R.id.as_edit_salary)
        val editRent = view.findViewById<EditText>(R.id.as_edit_rent)
        val editMeals = view.findViewById<EditText>(R.id.as_edit_meals)
        val btnCalc = view.findViewById<Button>(R.id.as_btn_calculate)

        btnCalc.setOnClickListener {
            val salaryStr = editSalary.text.toString().trim()
            val rentStr = editRent.text.toString().trim()
            val mealsStr = editMeals.text.toString().trim()

            // ყველა ველი უნდა იყოს შევსებული
            if (salaryStr.isEmpty() || rentStr.isEmpty() || mealsStr.isEmpty()) {
                Toast.makeText(requireContext(), "შეავსეთ ყველა ველი", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val salary = salaryStr.toDoubleOrNull()
            val rent = rentStr.toDoubleOrNull()
            val meals = mealsStr.toDoubleOrNull()

            // მნიშვნელობების ვალიდაცია
            if (salary == null || rent == null || meals == null) {
                Toast.makeText(requireContext(), "გამოიყენეთ მხოლოდ რიცხვები", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // FragmentB-ში მონაცემების გადაცემა
            val bundle = Bundle().apply {
                putDouble("salary", salary)
                putDouble("rent", rent)
                putDouble("meals", meals)
            }

            val fragmentB = FragmentB()
            fragmentB.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.as_input_fragment_container, fragmentB)
                .addToBackStack("fragmentB")
                .commit()
        }
    }
}