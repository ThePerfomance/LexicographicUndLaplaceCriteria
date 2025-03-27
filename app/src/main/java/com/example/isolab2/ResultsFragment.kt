package com.example.isolab2

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class ResultsFragment : Fragment() {

    private val students = mutableListOf(
        Student("Алиса", 8, 30, 2, 3),
        Student("Иван", 9, 45, 1, 4),
        Student("Семён", 7, 20, 3, 2),
        Student("Диана", 10, 50, 1, 5),
        Student("Мария", 6, 35, 2, 3),
        Student("Алексей", 8, 40, 1, 4),
        Student("Елена", 5, 25, 3, 2),
        Student("Кирилл", 9, 55, 1, 5)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_results, container, false)

        // Заполняем таблицу входных данных
        val inputTable = view.findViewById<TableLayout>(R.id.inputTable)
        inputTable.removeAllViews()
        inputTable.addView(createHeaderRow())
        for ((index, student) in students.withIndex()) {
            val row = createEditableRow(student, index)
            inputTable.addView(row)
        }

        // Кнопка "Добавить строку"
        view.findViewById<Button>(R.id.addRowButton).setOnClickListener {
            val newStudent = Student("", 0, 0, 0, 0)
            students.add(newStudent)
            val newRow = createEditableRow(newStudent, students.size - 1)
            inputTable.addView(newRow)
        }

        // Кнопка "Выполнить расчеты"
        view.findViewById<Button>(R.id.calculateButton).setOnClickListener {
            // Обновляем данные студентов из таблицы
            updateStudentsFromTable(inputTable)

            // Выполняем расчеты
            val lexicographicResult = CalculationLogic.lexicographicSort(students)
            val (laplaceResult, overallLaplace) = CalculationLogic.laplaceCriterion(students)

            // Заполняем таблицу лексикографического метода
            val lexicographicTable = view.findViewById<TableLayout>(R.id.lexicographicTable)
            lexicographicTable.removeAllViews()
            lexicographicTable.addView(createHeaderRow())
            for (student in lexicographicResult) {
                val row = createRow(student)
                lexicographicTable.addView(row)
            }

            // Заполняем таблицу критерия Лапласа
            val laplaceTable = view.findViewById<TableLayout>(R.id.laplaceTable)
            laplaceTable.removeAllViews()
            laplaceTable.addView(createHeaderRow())
            for (student in laplaceResult) {
                val row = createRow(student)
                laplaceTable.addView(row)
            }

            // Обновляем текстовые поля с общими результатами
            view.findViewById<TextView>(R.id.lexicographicResultText).text =
                "Общий результат: Первое место занял ${lexicographicResult.first().name}"
            view.findViewById<TextView>(R.id.laplaceResultText).text =
                "Общий результат: Максимальный критерий Лапласа = %.2f".format(overallLaplace)
            debugStudents()
        }
        // Отладочная информация

        return view
    }

    private fun createHeaderRow(): TableRow {
        return TableRow(requireContext()).apply {
            setBackgroundColor(Color.parseColor("#E3F2FD"))
            addView(createHeaderText("Имя"))
            addView(createHeaderText("Оценка"))
            addView(createHeaderText("Время (мин)"))
            addView(createHeaderText("Попытки"))
            addView(createHeaderText("Сложность"))
            addView(createHeaderText("Критерий")) // Новая колонка
        }
    }

    private fun createHeaderText(text: String): TextView {
        return TextView(requireContext()).apply {
            this.text = text
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            setTextColor(Color.BLACK)
            setTypeface(null, Typeface.BOLD)
        }
    }

    private fun createEditableRow(student: Student, index: Int): TableRow {
        return TableRow(requireContext()).apply {
            setBackgroundColor(Color.WHITE)
            addView(createEditableCell(student.name, index, "name"))
            addView(createEditableCell(student.score.toString(), index, "score"))
            addView(createEditableCell(student.duration.toString(), index, "duration"))
            addView(createEditableCell(student.attempts.toString(), index, "attempts"))
            addView(createEditableCell(student.difficulty.toString(), index, "difficulty"))
        }
    }

    private fun createEditableCell(text: String, index: Int, field: String): EditText {
        return EditText(requireContext()).apply {
            this.setText(text)
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            setTextColor(Color.BLACK)
            background = null // Убираем фон для более аккуратного вида

            // Добавляем слушатель для обновления данных
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val value = s.toString()
                    when (field) {
                        "name" -> students[index].name = value
                        "score" -> students[index].score = value.toIntOrNull() ?: 0
                        "duration" -> students[index].duration = value.toIntOrNull() ?: 0
                        "attempts" -> students[index].attempts = value.toIntOrNull() ?: 0
                        "difficulty" -> students[index].difficulty = value.toIntOrNull() ?: 0
                    }
                }
            })
        }
    }

    private fun createRow(student: Student): TableRow {
        return TableRow(requireContext()).apply {
            setBackgroundColor(Color.WHITE)
            addView(createCell(student.name))
            addView(createCell(student.score.toString()))
            addView(createCell(student.duration.toString()))
            addView(createCell(student.attempts.toString()))
            addView(createCell(student.difficulty.toString()))
            addView(createCell(student.criterion)) // Добавляем значение критерия
        }
    }

    private fun createCell(text: String): TextView {
        return TextView(requireContext()).apply {
            this.text = text
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            setTextColor(Color.BLACK)
        }
    }

    private fun updateStudentsFromTable(table: TableLayout) {
        students.clear() // Очищаем старые данные
        for (i in 1 until table.childCount) { // Проходим по всем строкам таблицы
            val row = table.getChildAt(i) as TableRow
            val name = (row.getChildAt(0) as EditText).text.toString()
            val duration = (row.getChildAt(1) as EditText).text.toString().toIntOrNull() ?: 0
            val attempts = (row.getChildAt(2) as EditText).text.toString().toIntOrNull() ?: 0
            val score = (row.getChildAt(3) as EditText).text.toString().toIntOrNull() ?: 0
            val difficulty = (row.getChildAt(4) as EditText).text.toString().toIntOrNull() ?: 1

            // Создаем нового студента с обновленными данными
            students.add(Student(name, duration, attempts, score, difficulty))
        }
    }
    private fun debugStudents() {
        println("Текущие данные студентов:")
        for (student in students) {
            println("Имя: ${student.name}, Оценка: ${student.score}, Время: ${student.duration}, Попытки: ${student.attempts}, Сложность: ${student.difficulty}")
        }
    }
}