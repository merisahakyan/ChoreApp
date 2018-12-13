package com.example.jsco_pc.choreapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.jsco_pc.choreapp.model.*

class ChoresDatabaseHandler (context:Context):
                     SQLiteOpenHelper(context, DATABASE_NAME , null , DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        var createChoreDatabase = "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY, $KEY_CHORENAME TEXT," +
                                  "$KEY_ASSIGNED_BY TEXT, $KEY_ASSIGNED_TO TEXT, $KEY_CHORE_ASSIGNED_TIME LONG )"
        db?.execSQL(createChoreDatabase)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}