package com.example.jsco_pc.choreapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jsco_pc.choreapp.R
import com.example.jsco_pc.choreapp.data.ChoresDatabaseHandler
import com.example.jsco_pc.choreapp.model.Chore

class MainActivity : AppCompatActivity() {

    var dbHandler:ChoresDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler= ChoresDatabaseHandler(this)

        var chore: Chore = Chore()
        chore.choreName="name"
        chore.assignedTo="test"
        chore.assignedBy="test"
        dbHandler!!.createChore(chore)

        var chores:Chore = dbHandler!!.readAChore(1)
        Toast.makeText(this,chores.choreName,Toast.LENGTH_LONG).show()

    }
}
