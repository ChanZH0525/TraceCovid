package com.example.tracecovid

import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RiskAssessmentAdapter (var questionnaire:ArrayList<RiskAssessmentClass>):
    RecyclerView.Adapter<RiskAssessmentAdapter.RiskAssessmentViewHolder>()
{
    class RiskAssessmentViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val questionname = itemView.findViewById<TextView>(R.id.riskassementQuestion)
     //   val radioyes=itemView.findViewById<RadioButton>(R.id.radio_yes)
       // val radiono=itemView.findViewById<RadioButton>(R.id.radio_no)

        fun bind(ques: RiskAssessmentClass)
        {
            questionname.text=ques.question
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiskAssessmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.riskassessmentquestions,parent,false)
        return RiskAssessmentViewHolder(view)
    }

    override fun onBindViewHolder(holder:RiskAssessmentViewHolder, position: Int) {
        holder.bind(questionnaire[position])
    }

    override fun getItemCount(): Int {
        return questionnaire.size
    }

}