package ru.zatsoft.roomuu1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.zatsoft.roomuu1.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


class MainActivity : AppCompatActivity(), ContactAdapter.ContactClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolBar: Toolbar
    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolBar = binding.toolbarMain
        setSupportActionBar(toolBar)
        title = " "

//        val inputKeyboard = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        binding.listView.layoutManager = LinearLayoutManager(this)
        val adapter = ContactAdapter(this, this)
        binding.listView.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ContactViewModel::class.java]
        viewModel.contactes.observe(this, { list ->
            list?.let { adapter.updateList(it) }
        })
        binding.listView.addItemDecoration(MyItemDecoration(this, R.drawable.divider))
        binding.listView.setHasFixedSize(false)
    }

    override fun onResume() {
        super.onResume()
        binding.btnSave.setOnClickListener {
            val formatTime = formatMilliseconds(Date().time)
            val contact = Contact(
                binding.edName.text.toString(), binding.edPhone.text.toString(),
                formatTime
            )
            if (contact.nameDB.isNotEmpty()) {
                viewModel.insertContact(contact)
                Toast.makeText(
                    this,
                    "${contact.nameDB} ${contact.phoneDB} добавлен",
                    Toast.LENGTH_LONG
                ).show()
            }
            binding.edName.text.clear()
            binding.edPhone.text.clear()
        }
    }

    fun formatMilliseconds(time: Long): String {
        val formatTime = SimpleDateFormat("dd.MM.yy HH:mm")
        formatTime.timeZone = TimeZone.getTimeZone("GMT+03")
        return formatTime.format(Date(time))
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

    override fun onItemClicked(contact: Contact) {
        viewModel.deleteContact(contact)
        Toast.makeText(this, "${contact.nameDB} удален", Toast.LENGTH_LONG).show()
    }
}