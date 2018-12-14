package com.example.jsco_pc.choreapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

        checkDb()
        saveChore.setOnClickListener{

            if(!enterChore.text.isEmpty() && !assignTo.text.isEmpty() && !assignedBy.text.isEmpty()){

                var chore:Chore = Chore()
                chore.choreName=enterChore.text.toString()
                chore.assignedBy=assignedBy.text.toString()
                chore.assignedTo=assignTo.text.toString()

                saveToDB(chore)
                Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show()
                enterChore.setText("")
                assignedBy.setText("")
                assignTo.setText("")
                startActivity(Intent(this, ChoreListActivity::class.java))
            }
            else{
                Toast.makeText(this,"Please enter all fields",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun checkDb(){
        if(dbHandler!!.getChoresCount()>0)
        {
            startActivity(Intent(this,ChoreListActivity::class.java))
        }
    }

    fun saveToDB(chore:Chore){
        dbHandler!!.createChore(chore)
    }
}
