package com.example.isolab2

data class Student(
    var name: String = "",
    var score: Int = 0,
    var duration: Int = 0, // Длительность выполнения теста (минуты)
    var attempts: Int = 0, // Количество попыток // Оценка (0-10)
    var difficulty: Int = 1, // Уровень сложности теста (1-5)
    var criterion: String = "" // Результат анализа (критерий)
)
