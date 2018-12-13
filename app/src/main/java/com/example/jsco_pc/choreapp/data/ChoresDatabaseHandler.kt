package com.example.jsco_pc.choreapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import com.example.jsco_pc.choreapp.model.*
import java.util.*
import kotlin.collections.ArrayList

class ChoresDatabaseHandler (context:Context):
                     SQLiteOpenHelper(context, DATABASE_NAME , null , DATABASE_VERSION) {

    private var context:Context? = null
    init{
         this.context=context
    }
    override fun onCreate(db: SQLiteDatabase?) {
        var createChoreDatabase = "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY, $KEY_CHORENAME TEXT," +
                                  "$KEY_ASSIGNED_BY TEXT, $KEY_ASSIGNED_TO TEXT, $KEY_CHORE_ASSIGNED_TIME LONG )"
        db?.execSQL(createChoreDatabase)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun createChore(chore:Chore){
        var db:SQLiteDatabase = writableDatabase
        var contentValues:ContentValues = ContentValues()

        contentValues.put(KEY_CHORENAME,chore.choreName)
        contentValues.put(KEY_ASSIGNED_BY,chore.assignedBy)
        contentValues.put(KEY_ASSIGNED_TO,chore.assignedTo)
        contentValues.put(KEY_CHORE_ASSIGNED_TIME,System.currentTimeMillis())

        db.insert(TABLE_NAME,null,contentValues)

        //Log.d("Data inserted","SUCCESS")
        Toast.makeText(this.context,"created",Toast.LENGTH_LONG).show()
        db.close()
    }

    fun readAChore(id:Int):Chore{
        var db:SQLiteDatabase = writableDatabase
        var cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_CHORENAME, KEY_ASSIGNED_BY, KEY_ASSIGNED_TO,
                                     KEY_CHORE_ASSIGNED_TIME), KEY_ID + "=?", arrayOf(id.toString()),
                                     null,null,null,null)
        if(cursor!==null){
            cursor.moveToFirst()
        }

        var chore:Chore = Chore()
        chore.choreName=cursor.getString(cursor.getColumnIndex(KEY_CHORENAME))
        chore.id=cursor.getInt(cursor.getColumnIndex(KEY_ID))
        chore.assignedTo=cursor.getString(cursor.getColumnIndex(KEY_ASSIGNED_TO))
        chore.assignedBy=cursor.getString(cursor.getColumnIndex(KEY_ASSIGNED_BY))
        chore.timeAssigned=cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

        return chore
    }

    fun readChores(): ArrayList<Chore>{
        var db:SQLiteDatabase = readableDatabase
        var list = ArrayList<Chore>()

        var selectAllQuery = "SELECT * FROM $TABLE_NAME"
        var cursor:Cursor = db.rawQuery(selectAllQuery,null)

        if(cursor.moveToFirst()){
            do{
                var chore = Chore()

                chore.choreName=cursor.getString(cursor.getColumnIndex(KEY_CHORENAME))
                chore.id=cursor.getInt(cursor.getColumnIndex(KEY_ID))
                chore.assignedTo=cursor.getString(cursor.getColumnIndex(KEY_ASSIGNED_TO))
                chore.assignedBy=cursor.getString(cursor.getColumnIndex(KEY_ASSIGNED_BY))
                chore.timeAssigned=cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

                list.add(chore)
            }
                while(cursor.moveToNext())
        }
        return list
    }

    fun updateChore(chore:Chore):Int{
        var db:SQLiteDatabase = writableDatabase

        var contentValues:ContentValues = ContentValues()

        contentValues.put(KEY_CHORENAME,chore.choreName)
        contentValues.put(KEY_ASSIGNED_BY,chore.assignedBy)
        contentValues.put(KEY_ASSIGNED_TO,chore.assignedTo)
        contentValues.put(KEY_CHORE_ASSIGNED_TIME,System.currentTimeMillis())

        return db.update(TABLE_NAME,contentValues, KEY_ID+"=?", arrayOf(chore.id.toString()))
    }

    fun deleteChore(chore:Chore){
        var db:SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID+"=?", arrayOf(chore.id.toString()))
        db.close()
    }

    fun getChoresCount():Int{
        var db:SQLiteDatabase = writableDatabase

        var countQuery = "SELECT * FROM $TABLE_NAME"
        var cursor: Cursor = db.rawQuery(countQuery,null)

        return cursor.count
    }
}