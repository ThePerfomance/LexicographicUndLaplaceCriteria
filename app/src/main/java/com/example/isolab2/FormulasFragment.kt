package com.example.isolab2

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat

class FormulasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_formulas, container, false)

        // Лексикографический метод
        val lexicographicFormula = SpannableString(
            "Лексикографический метод:\n" +
                    "1. Сортировка студентов по следующим критериям:\n" +
                    "   - По убыванию оценки.\n" +
                    "   - При равенстве оценок — по возрастанию времени выполнения теста.\n" +
                    "   - При равенстве времени — по возрастанию количества попыток.\n" +
                    "2. Присвоение рангов:\n" +
                    "   - Первому студенту в отсортированном списке присваивается ранг 1.\n" +
                    "   - Второму студенту — ранг 2, и так далее."
        )
        lexicographicFormula.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.purple_500)),
            0,
            "Лексикографический метод:".length,
            0
        )

        // Критерий Лапласа
        val laplaceFormula = SpannableString(
            "Критерий Лапласа:\n" +
                    "1. Для каждого студента вычисляется значение критерия:\n" +
                    "   Критерий = Оценка / Сложность\n" +
                    "2. Находится максимальное значение среди всех критериев.\n" +
                    "3. Студент с максимальным значением критерия считается лучшим."
        )
        laplaceFormula.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.teal_700)),
            0,
            "Критерий Лапласа:".length,
            0
        )

        // Установка текста
        view.findViewById<TextView>(R.id.lexicographicFormulaText).text = lexicographicFormula
        view.findViewById<TextView>(R.id.laplaceFormulaText).text = laplaceFormula

        return view
    }
}