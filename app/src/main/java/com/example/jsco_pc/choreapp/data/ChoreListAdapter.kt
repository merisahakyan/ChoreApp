package com.example.jsco_pc.choreapp.data

import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.jsco_pc.choreapp.R
import com.example.jsco_pc.choreapp.activity.ChoreListActivity
import com.example.jsco_pc.choreapp.model.Chore
import kotlinx.android.synthetic.main.popup.view.*

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


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener {

        var choreName=itemView.findViewById<TextView>(R.id.listChoreName)
        var assignedBy=itemView.findViewById<TextView>(R.id.listAssignedBy)
        var assignedTo=itemView.findViewById<TextView>(R.id.listAssignedTo)
        var assignedDate=itemView.findViewById<TextView>(R.id.listDate)
        var deleteButton = itemView.findViewById<Button>(R.id.listDeleteButton)
        var editButton = itemView.findViewById<Button>(R.id.listEditButton)

        override fun onClick(v: View?) {
            var mPos:Int = adapterPosition
            var chore = list[mPos]

            when(v!!.id){
                deleteButton.id -> {
                    deleteChore(chore.id!!)
                    list.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
                editButton.id -> {
                    editChore(chore)
                }

            }
        }

        fun bindViews(chore:Chore){
            choreName.text=chore.choreName
            assignedBy.text=chore.assignedBy
            assignedDate.text=chore.showHumanDate("dd/MM/yy hh:mm",chore.timeAssigned!!)
            assignedTo.text=chore.assignedTo

            deleteButton.setOnClickListener(this)
            editButton.setOnClickListener(this)
        }

        fun deleteChore(id:Int){
            var db:ChoresDatabaseHandler = ChoresDatabaseHandler(context)

            db.deleteChore(id)
        }

        fun editChore(chore:Chore){
            var dbHandler:ChoresDatabaseHandler = ChoresDatabaseHandler(context)
            var dialogBuilder: AlertDialog.Builder? = null
            var dialog: AlertDialog? = null

            var view = LayoutInflater.from(context)!!.inflate(R.layout.popup,null)
            var choreName = view.popEnterChore
            var assignedBy = view.popAssignedBy
            var assignTo = view.popAssignTo
            var saveButton = view.popSaveChore

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog!!.show()

            saveButton.setOnClickListener{
                if(!choreName.text.isEmpty() && !assignTo.text.isEmpty() && !assignedBy.text.isEmpty()){

                    chore.choreName=choreName.text.toString()
                    chore.assignedBy=assignedBy.text.toString()
                    chore.assignedTo=assignTo.text.toString()

                    dbHandler!!.updateChore(chore)
                    notifyItemChanged(adapterPosition,chore)
                    dialog!!.dismiss()
                }
                else{}
            }
        }
    }
}