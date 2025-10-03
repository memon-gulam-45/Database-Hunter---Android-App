package com.example.practical5

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DBHelper(this)

        val insertOpt = findViewById<Button>(R.id.insertOpt)
        val updateOpt = findViewById<Button>(R.id.updateOpt)
        val deleteOpt = findViewById<Button>(R.id.deleteOpt)
        val viewOpt = findViewById<Button>(R.id.viewOpt)

        val insertSelected = findViewById<LinearLayout>(R.id.insertSelected)
        val updateSelected = findViewById<LinearLayout>(R.id.updateSelected)
        val deleteSelected = findViewById<LinearLayout>(R.id.deleteSelected)
        val viewSelected = findViewById<LinearLayout>(R.id.viewSelected)
        val insertBtn = findViewById<Button>(R.id.insertBtn)
        val insertName = findViewById<EditText>(R.id.insertName)
        val insertAge = findViewById<EditText>(R.id.insertAge)
        val insertBranch = findViewById<EditText>(R.id.insertBranch)
        val insertMSG = findViewById<TextView>(R.id.insertMSG)
        val updateBtn = findViewById<Button>(R.id.updateBtn)
        val updateId = findViewById<EditText>(R.id.updateId)
        val updateName = findViewById<EditText>(R.id.updateName)
        val updateAge = findViewById<EditText>(R.id.updateAge)
        val updateBranch = findViewById<EditText>(R.id.updateBranch)
        val updateMSG = findViewById<TextView>(R.id.updateMSG)
        val deleteBtn = findViewById<Button>(R.id.deleteBtn)
        val deleteId = findViewById<EditText>(R.id.deleteId)
        val deleteMSG = findViewById<TextView>(R.id.deleteMSG)
        val viewBtn = findViewById<Button>(R.id.viewBtn)
        val viewId = findViewById<EditText>(R.id.viewId)
        val viewMSG = findViewById<TextView>(R.id.viewMSG)

        fun hideAllMsg(){
            insertMSG.text = ""
            updateMSG.text = ""
            deleteMSG.text = ""
            viewMSG.text = ""
        }

        insertOpt.setOnClickListener {
            insertSelected.visibility = View.VISIBLE
            updateSelected.visibility = View.GONE
            deleteSelected.visibility = View.GONE
            viewSelected.visibility = View.GONE
            hideAllMsg()
        }
        updateOpt.setOnClickListener {
            insertSelected.visibility = View.GONE
            updateSelected.visibility = View.VISIBLE
            deleteSelected.visibility = View.GONE
            viewSelected.visibility = View.GONE
            hideAllMsg()
        }
        deleteOpt.setOnClickListener {
            insertSelected.visibility = View.GONE
            updateSelected.visibility = View.GONE
            deleteSelected.visibility = View.VISIBLE
            viewSelected.visibility = View.GONE
            hideAllMsg()
        }
        viewOpt.setOnClickListener {
            insertSelected.visibility = View.GONE
            updateSelected.visibility = View.GONE
            deleteSelected.visibility = View.GONE
            viewSelected.visibility = View.VISIBLE
            hideAllMsg()
        }

        insertBtn.setOnClickListener {
            val name = insertName.text.toString().trim()
            val ageText = insertAge.text.toString().trim()
            val branch = insertBranch.text.toString().trim()

            if (name.isEmpty() || ageText.isEmpty() || branch.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val age = ageText.toIntOrNull()
            if(age == null){
                Toast.makeText(this, "Enter a valid age", Toast.LENGTH_SHORT). show()
                return@setOnClickListener
            }
            val id = db.insertStudent(name, age, branch)
            if (id != -1L) {
                insertMSG.text = "Student Inserted Successfully With ID: $id"
            } else {
                insertMSG.text = "Insert Failed"
            }
            insertName.text.clear()
            insertAge.text.clear()
            insertBranch.text.clear()
        }
        updateBtn.setOnClickListener {
            val idText = updateId.text.toString().trim()
            val name = updateName.text.toString().trim()
            val ageText = updateAge.text.toString().trim()
            val branch = updateBranch.text.toString().trim()

            if (idText.isEmpty() || name.isEmpty() || ageText.isEmpty() || branch.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = idText.toIntOrNull()
            val age = ageText.toIntOrNull()

            if (id == null) {
                Toast.makeText(this, "Enter valid ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (age == null) {
                Toast.makeText(this, "Enter valid age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = db.updateStudent(id, name, age, branch)
            if (result) {
                updateMSG.text = "Data Updated Successfully"
            } else {
                updateMSG.text = "Update Failed / ID not found"
            }
            updateId.text.clear()
            updateName.text.clear()
            updateAge.text.clear()
            updateBranch.text.clear()
        }
        deleteBtn.setOnClickListener {
            val idText = deleteId.text.toString().trim()
            if(idText.isEmpty()){
                Toast.makeText(this, "Enter ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = idText.toIntOrNull()
            if (id == null){
                Toast.makeText(this, "Enter valid ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val result = db.deleteStudent(id)
            if (result){
                deleteMSG.text = "Student Deleted Successfully"
            } else {
                deleteMSG.text = "Delete Failed / ID not found"
            }

        }
        viewBtn.setOnClickListener {
            val idText = viewId.text.toString().trim()
            if (idText.isEmpty()) {
                Toast.makeText(this, "Enter ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = idText.toIntOrNull()
            if (id == null) {
                Toast.makeText(this, "Enter valid ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val studentData = db.getStudent(id)
            viewMSG.text = studentData
        }
    }
}