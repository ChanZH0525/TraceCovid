package com.example.tracecovid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SelfReportAdapter (var questionnaire2:ArrayList<SelfReportClass>):
    RecyclerView.Adapter<SelfReportAdapter.SelfReportViewHolder>(){
    class SelfReportViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val question=itemView.findViewById<TextView>(R.id.self_report_question)
        val answer=itemView.findViewById<EditText>(R.id.self_report_ans)

        fun bind(ques2: SelfReportClass)
        {
            question.text=ques2.question
          //  answer.toString()=ques2.answer
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelfReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.selfreportquestions,parent,false)
        return SelfReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelfReportViewHolder, position: Int) {
        holder.bind(questionnaire2[position])
    }

    override fun getItemCount(): Int {
        return questionnaire2.size
    }
}