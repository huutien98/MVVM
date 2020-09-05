package com.vncoder.mvvm.View.Activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vncoder.mvvm.R
import com.vncoder.mvvm.ViewModel.MainViewModel
import com.vncoder.retrofit2_employee.Adapter.AdapterEmployee
import com.vncoder.retrofit2_employee.Model.Contact
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val ActivityRequestCode2 = 2
    private var mainViewModel: MainViewModel? = null
    private lateinit var adapterEmployee: AdapterEmployee
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv_cyclerview)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        getUserList()

        btn_Create.setOnClickListener {
            val intent = Intent(this,CreateActivity::class.java)
            startActivity(intent)
        }

    }

    fun getUserList() {
        mainViewModel!!.allUsers.observe(this,
            { userList -> setRecyclerView(userList as List<Contact>) })
    }

    private fun setRecyclerView(contactList: List<Contact>) {
        adapterEmployee = AdapterEmployee(onClickListener, contactList as ArrayList<Contact>)
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 4)
        }
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapterEmployee
        adapterEmployee.notifyDataSetChanged()
    }

    private var onClickListener = object : AdapterEmployee.OnItemClickListener {
        override fun onClickEmployee(contact: Contact) {

            val replyintent = Intent(this@MainActivity, infoActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("detailEmployee", contact)
            replyintent.putExtras(bundle)
            startActivityForResult(replyintent, ActivityRequestCode2)
        }

        override fun onLongClickEmployee(contact: Contact) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        var menuItem: MenuItem? = menu?.findItem(R.id.action_search)

        var searchView: SearchView = menuItem?.actionView as SearchView
        searchView.queryHint = "input text"
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var listResult = if (query!!.isEmpty()) {

                } else {
                    adapterEmployee.exampleList.filter {
                        it.Name?.toLowerCase()!!.contains(query.toString())
                    }
                }
                adapterEmployee.exampleList = listResult as ArrayList<Contact>
                adapterEmployee.notifyDataSetChanged()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                var listResult = if (newText!!.isEmpty()) {

                } else {
                    adapterEmployee.exampleList.filter {
                        it.FirstName?.toLowerCase()!!.contains(
                            newText.toString()
                        )
                    }
                }

                adapterEmployee.notifyDataSetChanged()

                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return when (id) {
            R.id.action_sort -> {
                adapterEmployee.exampleList.sortBy { it.Email }
                adapterEmployee.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

}