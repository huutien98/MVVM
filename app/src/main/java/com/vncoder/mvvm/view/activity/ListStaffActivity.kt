package com.vncoder.mvvm.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vncoder.mvvm.R
import com.vncoder.mvvm.ViewModel.MainViewModel
import com.vncoder.mvvm.model.Contact
import com.vncoder.retrofit2_employee.Adapter.AdapterContact
 import kotlinx.android.synthetic.main.activity_main.*


class ListStaffActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModel.MainViewModelFactory(this.application)
        )[MainViewModel::class.java]
    }

    private val ActivityRequestCode2 = 2
    private lateinit var adapterContact: AdapterContact
    private lateinit var recyclerView: RecyclerView
    private lateinit var swiperefresh: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv_cyclerview)
        swiperefresh = findViewById(R.id.swiperefresh)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btn_Create.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }

        refreshData()
    }

    private fun initControls() {
        rv_cyclerview.isLongClickable = true
        adapterContact = AdapterContact(onCLickItem)
        rv_cyclerview.setHasFixedSize(true)
        rv_cyclerview.layoutManager = LinearLayoutManager(this)
        rv_cyclerview.adapter = adapterContact
        mainViewModel.getMutableLiveData(this).observe(this, Observer {
            adapterContact.contact = (it as ArrayList<Contact>)
            adapterContact.notifyDataSetChanged()
        })
    }

    private fun refreshData(){
        swiperefresh.setOnRefreshListener {
            initControls()
            swiperefresh.isRefreshing = false
        }
    }

    private var onCLickItem = object : AdapterContact.onItemClick{
        override fun onItemClick(contact: Contact) {
            val replyintent = Intent(this@ListStaffActivity, InfoActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("detailEmployee", contact)
            replyintent.putExtras(bundle)
            startActivityForResult(replyintent, ActivityRequestCode2)
        }

        override fun onLongItemClick(contact: Contact) {
            val builder = androidx.appcompat.app.AlertDialog.Builder(this@ListStaffActivity)
            builder.setTitle("notification")
            builder.setMessage("Do you want delete item ?")
            builder.setCancelable(false)
            builder.setPositiveButton("Yes")
            { _, i ->
                llProgressBarMain.visibility = View.VISIBLE
                mainViewModel.deleteData(contact.contact_id.toString(),this@ListStaffActivity)
                llProgressBarMain.visibility = View.GONE
            }
            builder.setNegativeButton("No")
            { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
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
                    initControls()
                } else {
                    adapterContact.contact.filter {
                        it.Name?.toLowerCase()!!.contains(query.toString())
                    }
                }
                adapterContact.contact = listResult as ArrayList<Contact>
                adapterContact.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var listResult = if (newText!!.isEmpty()) {
                     initControls()
                } else {
                    adapterContact.contact.filter {
                        it.FirstName?.toLowerCase()!!.contains(
                            newText.toString()
                        )
                    }
                }
//                adapterContact.exampleList = listResult as ArrayList<Contact>
                adapterContact.notifyDataSetChanged()
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return when (id) {
            R.id.action_sort -> {
                mainViewModel.getMutableLiveData(this).observe(this,{ it ->
                    it.sortedBy { it.Email }
                    adapterContact.contact = (it as ArrayList<Contact>)
                })
                adapterContact.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {

        initControls()

        super.onResume()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            initControls()
        }
    }
}

