package com.example.spisok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spisok.DBHelper.Companion.KEY_DATE
import com.example.spisok.DBHelper.Companion.KEY_FIRSTNAME
import com.example.spisok.DBHelper.Companion.KEY_ID
import com.example.spisok.DBHelper.Companion.KEY_NAME
import com.example.spisok.DBHelper.Companion.KEY_TELE

class MainActivity : AppCompatActivity() {
    var number = ""
    val list = mutableListOf<Todo>()
    private val dbHelper = DBHelper(this)

    companion object {
        const val EXTRA_KEY = "EXTRA"
    }

    private lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        var num = ""
        list.addAll(dbHelper.getAll())
        adapter = RecyclerAdapter(list) {


            val intent = Intent(this, ViewActivity::class.java)
            intent.putExtra(KEY_NAME, list[it].name)
            intent.putExtra(KEY_FIRSTNAME, list[it].firstname)
            intent.putExtra(KEY_DATE, list[it].date)
            intent.putExtra(KEY_TELE, list[it].tele)
            intent.putExtra(KEY_ID, it)
            intent.putExtra(EXTRA_KEY, list[it].id)
            startActivity(intent)


        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val number = findViewById<EditText>(R.id.number)
        number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                num = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val buttonAdd = findViewById<Button>(R.id.button)
        buttonAdd.setOnClickListener {
            val s = number.text.toString()
            val id = dbHelper.add(num,"0","0","0")
            adapter.notifyItemInserted(list.lastIndex)
            number.text.clear()
        }

        adapter.notifyDataSetChanged()
    }
}




