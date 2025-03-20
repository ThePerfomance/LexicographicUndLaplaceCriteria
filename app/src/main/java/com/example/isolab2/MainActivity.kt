package com.example.isolab2

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val students = mutableListOf(
        Student("Алиса", 30, 2, 8, 3),
        Student("Иван", 45, 1, 9, 4),
        Student("Семён", 20, 3, 7, 2),
        Student("Диана", 50, 1, 10, 5),
        Student("Мария", 35, 2, 6, 3),
        Student("Алексей", 40, 1, 8, 4),
        Student("Елена", 25, 3, 5, 2),
        Student("Кирилл", 55, 1, 9, 5)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Заполняем таблицу входных данных
        val inputTable = findViewById<TableLayout>(R.id.inputTable)
        for (student in students) {
            val row = TableRow(this).apply {
                addView(TextView(this@MainActivity).apply {
                    text = student.name
                    gravity = Gravity.CENTER
                    setPadding(8, 8, 8, 8)
                })
                addView(TextView(this@MainActivity).apply {
                    text = student.score.toString()
                    gravity = Gravity.CENTER
                    setPadding(8, 8, 8, 8)
                })
                addView(TextView(this@MainActivity).apply {
                    text = student.duration.toString()
                    gravity = Gravity.CENTER
                    setPadding(8, 8, 8, 8)
                })
                addView(TextView(this@MainActivity).apply {
                    text = student.attempts.toString()
                    gravity = Gravity.CENTER
                    setPadding(8, 8, 8, 8)
                })
                addView(TextView(this@MainActivity).apply {
                    text = student.difficulty.toString()
                    gravity = Gravity.CENTER
                    setPadding(8, 8, 8, 8)
                })
            }
            inputTable.addView(row)
        }

        // Обработка кнопки
        findViewById<Button>(R.id.calculateButton).setOnClickListener {
            val lexicographicResult = CalculationLogic.lexicographicSort(students)
            val (laplaceResult, overallLaplace) = CalculationLogic.laplaceCriterion(students)

            // Заполняем таблицу лексикографического метода
            val lexicographicTable = findViewById<TableLayout>(R.id.lexicographicTable)
            lexicographicTable.removeAllViews()
            lexicographicTable.addView(createHeaderRow())
            for (student in lexicographicResult) {
                val row = createRow(student)
                lexicographicTable.addView(row)
            }

            // Заполняем таблицу критерия Лапласа
            val laplaceTable = findViewById<TableLayout>(R.id.laplaceTable)
            laplaceTable.removeAllViews()
            laplaceTable.addView(createHeaderRow())
            for (student in laplaceResult) {
                val row = createRow(student)
                laplaceTable.addView(row)
            }

            // Обновляем текстовые поля с общими результатами
            findViewById<TextView>(R.id.lexicographicResultText).text =
                "Общий результат: Первое место занял ${lexicographicResult.first().name}"
            findViewById<TextView>(R.id.laplaceResultText).text =
                "Общий результат: Максимальный критерий Лапласа = %.2f".format(overallLaplace)
        }
    }
    private fun createHeaderRow(): TableRow {
        return TableRow(this).apply {
            setBackgroundColor(Color.parseColor("#E3F2FD")) // Светло-синий цвет
            addView(TextView(this@MainActivity).apply {
                text = "Имя"
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.BOLD)
                setPadding(8, 8, 8, 8)
                setTextColor(Color.parseColor("#3F51B5")) // Темно-синий текст
            })
            addView(TextView(this@MainActivity).apply {
                text = "Оценка"
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.BOLD)
                setPadding(8, 8, 8, 8)
                setTextColor(Color.parseColor("#3F51B5"))
            })
            addView(TextView(this@MainActivity).apply {
                text = "Время (мин)"
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.BOLD)
                setPadding(8, 8, 8, 8)
                setTextColor(Color.parseColor("#3F51B5"))
            })
            addView(TextView(this@MainActivity).apply {
                text = "Попытки"
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.BOLD)
                setPadding(8, 8, 8, 8)
                setTextColor(Color.parseColor("#3F51B5"))
            })
            addView(TextView(this@MainActivity).apply {
                text = "Критерий"
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.BOLD)
                setPadding(8, 8, 8, 8)
                setTextColor(Color.parseColor("#3F51B5"))
            })
        }
    }
    private fun createRow(student: Student): TableRow {
        return TableRow(this).apply {
            setBackgroundColor(Color.WHITE)
            addView(createCell(student.name))
            addView(createCell(student.score.toString()))
            addView(createCell(student.duration.toString()))
            addView(createCell(student.attempts.toString()))
            addView(createCell(student.criterion))
        }
    }
    private fun createCell(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            setTextColor(Color.BLACK)
            setBackgroundResource(android.R.drawable.list_selector_background)
        }
    }
}