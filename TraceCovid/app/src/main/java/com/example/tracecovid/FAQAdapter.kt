package com.example.tracecovid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class FAQAdapter (var faq: ArrayList<FAQClass>):RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {
    class FAQViewHolder( itemView: View):RecyclerView.ViewHolder(itemView) {
        val faq_question = itemView.findViewById<TextView>(R.id.faqQuestion)
        val faq_ans = itemView.findViewById<TextView>(R.id.faqans)
        fun bind(f:FAQClass)
        {
            faq_question.text = f.question
            faq_ans.text = f.answer
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.faq_list,parent,false)
        return FAQAdapter.FAQViewHolder(view)
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        holder.bind(faq[position])
    }

    override fun getItemCount(): Int {
        return faq.size
    }

}