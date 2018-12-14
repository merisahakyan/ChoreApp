package com.example.jsco_pc.choreapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.jsco_pc.choreapp.R
import com.example.jsco_pc.choreapp.data.ChoreListAdapter
import com.example.jsco_pc.choreapp.data.ChoresDatabaseHandler
import com.example.jsco_pc.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListActivity : AppCompatActivity() {

    var dbHandler:ChoresDatabaseHandler? = null
    private var adapter: ChoreListAdapter? = null
    private var choreList:ArrayList<Chore>? = null
    private var choreListItems:ArrayList<Chore>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        dbHandler= ChoresDatabaseHandler(this)

        choreList = ArrayList<Chore>()
        choreListItems = ArrayList<Chore>()
        layoutManager= LinearLayoutManager(this)
        adapter = ChoreListAdapter(choreListItems!!,this)

        //setup list = recyclerView
        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter

        //load chores
        choreList = dbHandler!!.readChores()
        choreList!!.reverse()

        for (c in choreList!!){
            var chore: Chore = Chore()
            chore.choreName = c.choreName
            chore.assignedBy = c.assignedBy
            chore.timeAssigned = c.timeAssigned
            chore.assignedTo = c.assignedTo

            choreListItems!!.add(chore)
        }

        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.add_menu_button){
            createPopupDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    fun createPopupDialog(){

        var view = layoutInflater.inflate(R.layout.popup,null)
        var choreName = view.popEnterChore
        var assignedBy = view.popAssignedBy
        var assignTo = view.popAssignTo
        var saveButton = view.popSaveChore

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog!!.show()

        saveButton.setOnClickListener{
            if(!choreName.text.isEmpty() && !assignTo.text.isEmpty() && !assignedBy.text.isEmpty()){
                var chore = Chore()

                chore.choreName=choreName.text.toString()
                chore.assignedBy=assignedBy.text.toString()
                chore.assignedTo=assignTo.text.toString()

                dbHandler!!.createChore(chore)

                Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show()
                dialog!!.dismiss()

                startActivity(Intent(this,ChoreListActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this,"Please enter all fields",Toast.LENGTH_LONG).show()
            }
        }
    }

}
