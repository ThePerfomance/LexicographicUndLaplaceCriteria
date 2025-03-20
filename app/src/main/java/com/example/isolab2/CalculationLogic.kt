package com.example.isolab2

object CalculationLogic {

    // Лексикографический метод
    fun lexicographicSort(students: List<Student>): List<Student> {
        return students.sortedWith(
            compareByDescending<Student> { it.score }
                .thenBy { it.duration }
                .thenBy { it.attempts }
        ).mapIndexed { index, student ->
            student.copy(criterion = (index + 1).toString()) // Только число (ранг)
        }
    }

    // Критерий Лапласа
    fun laplaceCriterion(students: List<Student>): Pair<List<Student>, Double> {
        val laplaceScores = students.map { student ->
            val averageScore = student.score.toDouble() / student.difficulty
            student.copy(criterion = "%.2f".format(averageScore))
        }
        val overallResult = laplaceScores.maxOf { it.score.toDouble() / it.difficulty }
        return Pair(laplaceScores, overallResult)
    }
}