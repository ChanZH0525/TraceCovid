package com.example.tracecovid.home.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tracecovid.R
import com.example.tracecovid.data.FAQData

class FAQAdapter (var faq: ArrayList<FAQData>):RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {
    class FAQViewHolder( itemView: View):RecyclerView.ViewHolder(itemView) {
        val faq_question = itemView.findViewById<TextView>(R.id.faqQuestion)
        val faq_ans = itemView.findViewById<TextView>(R.id.faqans)
        fun bind(f: FAQData)
        {
            faq_question.text = f.question
            faq_ans.text = f.answer
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.faq_list,parent,false)
        return FAQViewHolder(view)
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        holder.bind(faq[position])
    }

    override fun getItemCount(): Int {
        return faq.size
    }

}