package com.example.isolab2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private var students: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    override fun getItemCount(): Int = students.size

    fun updateData(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        private val scoreTextView = itemView.findViewById<TextView>(R.id.scoreTextView)
        private val durationTextView = itemView.findViewById<TextView>(R.id.durationTextView)
        private val attemptsTextView = itemView.findViewById<TextView>(R.id.attemptsTextView)
        private val criterionTextView = itemView.findViewById<TextView>(R.id.criterionTextView)

        fun bind(student: Student) {
            nameTextView.text = student.name
            scoreTextView.text = student.score.toString()
            durationTextView.text = "${student.duration} мин"
            attemptsTextView.text = student.attempts.toString()
            criterionTextView.text = student.criterion
        }
    }
}