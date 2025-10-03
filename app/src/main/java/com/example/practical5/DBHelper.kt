package com.example.practical5

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
        SQLiteOpenHelper(context, "StudentDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE Student(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "age INTEGER," +
                    "branch TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Student")
        onCreate(db)
    }

    fun insertStudent(name: String, age: Int, branch: String): Long{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("age", age)
        cv.put("branch", branch)
        return db.insert("Student", null, cv)
    }
    fun updateStudent(id: Int, name: String, age: Int, branch: String): Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("age", age)
        cv.put("branch", branch)
        val result = db.update("Student", cv, "id=?", arrayOf(id.toString()))
        return  result > 0
    }
    fun deleteStudent(id: Int): Boolean{
        val db = this.writableDatabase
        val result = db.delete("Student", "id=?", arrayOf(id.toString()))
        return result > 0
    }
    fun getStudent(id: Int): String{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Student WHERE id=?", arrayOf(id.toString()))
        return if(cursor.moveToFirst()){
            val name = cursor.getString(1)
            val age = cursor.getString(2)
            val branch = cursor.getString(3)
            "ID: $id\nName: $name\nAge: $age\nBranch: $branch"
        } else{
            "No student found with ID $id"
        }
    }
}