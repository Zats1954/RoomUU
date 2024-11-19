package ru.zatsoft.roomuu1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import ru.zatsoft.roomuu1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolBar: Toolbar
    private lateinit var adapter: CustomAdapter
    private lateinit var contacts: MutableList<Contacts>
    var db: ContactDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolBar = binding.toolbarMain
        setSupportActionBar(toolBar)
        title = " "

        db = ContactDatabase.getDatabase(this)

        val inputKeyboard = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        contacts = mutableListOf()
        adapter = CustomAdapter(contacts)
        binding.listView.adapter = adapter


        binding.listView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.listView.addItemDecoration(MyItemDecoration(this, R.drawable.divider))
        binding.listView.setHasFixedSize(false)
//        adapter.setOnContactsClickListener(
//            object : CustomAdapter.OnContactClickListener {
//                override fun onContactClick(contacts: Contacts, position: Int) {
//                    itemPosition = position
//                    val intent = Intent(this@MainActivity, ItemActivity::class.java)
//                    intent.putExtra("contacts", contacts)
//                    startActivity(intent)
//                }
//            }
//        )

    }

    override fun onResume(){
        super.onResume()
        binding.btnSave.setOnClickListener {
            val contact = Contact(binding.edName.text.toString(), binding.edPhone.text.toString())
            addContact(db!!, contact)
            readDatabase(db!!)
//            try {
//                contacts.add(Contacts(
//                    binding.edName.text.toString(),
//                    binding.edPhone.text.toString() ))
//
//                adapter.notifyDataSetChanged()
//                clearView()
//                inputKeyboard.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
//            } catch (e: NumberFormatException) {
//                Toast.makeText(this, "Неправильный ввод", Toast.LENGTH_LONG).show()
//            }
        }
    }

    private fun clearView() {
        binding.edName.text.clear()
        binding.edName .text.clear()
        binding.edPhone .text?.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exit)
            finish()
        return super.onOptionsItemSelected(item)
    }

    private fun addContact (db: ContactDatabase, contact: Contact) =
        GlobalScope.async{
            db.getContactDao().insert(contact)
        }
    private fun readDatabase(db: ContactDatabase) =
        GlobalScope.async{
            var contacts = StringBuffer()
//            binding.tvList.text = ""
            val list = db.getContactDao().getAllContacts()
            println("------- ${list.size}")
            for(i in list.indices){contacts.append("${list[i].nameDB} ${list[i].phoneDB} \n" )}
            println(list.toString())
        runOnUiThread {binding.tvList.text = contacts.toString()}
        }
}