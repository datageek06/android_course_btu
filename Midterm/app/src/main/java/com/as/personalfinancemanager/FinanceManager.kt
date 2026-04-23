package com.`as`.personalfinancemanager

class FinanceManager {
    val firstName = "Andrew"
    val lastName  = "Simonyan"
    val birthDate = "09/02/2006"

    private val birthMonth: Int = birthDate.trim().split("/")[1].toInt()

    private val savingsPercent: Int = lastName.length + birthMonth

    fun getTotalExpenses(model: FinanceModel): Double {
        return model.rent + model.meals
    }

    fun getBalance(model: FinanceModel): Double {
        return model.salary - getTotalExpenses(model)
    }

    fun getSavingsAmount(model: FinanceModel): Double {
        val balance = getBalance(model)
        return if (balance > 0) balance * savingsPercent / 100.0 else 0.0
    }

    // მინუსში მუშაობს თუ არა
    fun isMinus(model: FinanceModel): Boolean {
        return model.salary < getTotalExpenses(model)
    }

    fun getSavingsPercent(): Int = savingsPercent
}
