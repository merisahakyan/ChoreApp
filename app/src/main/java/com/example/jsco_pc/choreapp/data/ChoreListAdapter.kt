package com.example.jsco_pc.choreapp.data

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jsco_pc.choreapp.R
import com.example.jsco_pc.choreapp.model.Chore

class ChoreListAdapter(private val list:ArrayList<Chore>, private val context:Context)
             : RecyclerView.Adapter<ChoreListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        //create view from xml
        val view = LayoutInflater.from(context).inflate(R.layout.list_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindViews(list[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var choreName=itemView.findViewById<TextView>(R.id.listChoreName)
        var assignedBy=itemView.findViewById<TextView>(R.id.listAssignedBy)
        var assignedTo=itemView.findViewById<TextView>(R.id.listAssignedTo)
        var assignedDate=itemView.findViewById<TextView>(R.id.listDate)

        fun bindViews(chore:Chore){
            choreName.text=chore.choreName
            assignedBy.text=chore.assignedBy
            assignedDate.text=chore.showHumanDate("dd/MM/yy hh:mm",chore.timeAssigned!!)
            assignedTo.text=chore.assignedTo
        }
    }
}