package com.example.jsco_pc.choreapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.example.jsco_pc.choreapp.R
import com.example.jsco_pc.choreapp.data.ChoresDatabaseHandler
import com.example.jsco_pc.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbHandler:ChoresDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler= ChoresDatabaseHandler(this)

        saveChore.setOnClickListener{

            if(!enterChoreId.text.isEmpty() && !assignToId.text.isEmpty() && !assignedById.text.isEmpty()){

                var chore:Chore = Chore()
                chore.choreName=enterChoreId.text.toString()
                chore.assignedBy=assignedById.text.toString()
                chore.assignedTo=assignToId.text.toString()

                saveToDB(chore)
                Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show()
                enterChoreId.setText("")
                assignedById.setText("")
                assignToId.setText("")
                startActivity(Intent(this, ChoreListActivity::class.java))
            }
            else{
                Toast.makeText(this,"Please enter all fields",Toast.LENGTH_LONG).show()
            }
        }


//        var chore: Chore = Chore()
//        chore.choreName="name"
//        chore.assignedTo="test"
//        chore.assignedBy="test"
//        dbHandler!!.createChore(chore)
//
//        var chores:Chore = dbHandler!!.readAChore(1)
//        Toast.makeText(this,chores.choreName,Toast.LENGTH_LONG).show()

    }

    fun saveToDB(chore:Chore){
        dbHandler!!.createChore(chore)
    }
}
