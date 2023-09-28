package com.example.nycschoolportal.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.kmm.shared.entity.NYCSchool

class NYCSchoolListAdapter (var launches: List<NYCSchool>) : RecyclerView.Adapter<NYCSchoolListAdapter.LaunchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
            .run(::LaunchViewHolder)
    }

    override fun getItemCount(): Int = launches.count()

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bindData(launches[position])
    }

    inner class LaunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nycSchoolNameTextView = itemView.findViewById<TextView>(R.id.schoolName)
        private val dbnTextView = itemView.findViewById<TextView>(R.id.dbn)

        fun bindData(launch: NYCSchool) {

            nycSchoolNameTextView.text = launch.school_name
            dbnTextView.text =  launch.dbn
        }
    }
}