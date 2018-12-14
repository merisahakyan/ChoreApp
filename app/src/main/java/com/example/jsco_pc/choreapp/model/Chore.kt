package com.example.jsco_pc.choreapp.model

import android.text.format.DateFormat
import java.util.*

class Chore() {
    var choreName:String? = null
    var assignedBy:String? = null
    var assignedTo:String? = null
    var timeAssigned:Long? = null
    var id:Int? = null

    constructor(choreName:String,AssignedBy:String,
                assignedTo:String,timeAssigned:Long,
                id:Int):this(){

        this.assignedBy=assignedBy
        this.choreName=choreName
        this.assignedTo=assignedTo
        this.timeAssigned=timeAssigned
        this.id=id
    }

    fun showHumanDate(dateFormat:String,milliseconds:Long):String{
        return DateFormat.format(dateFormat, milliseconds).toString()
    }
}