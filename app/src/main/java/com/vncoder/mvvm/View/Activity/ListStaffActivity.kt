package com.vncoder.mvvm.View.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vncoder.mvvm.R
import com.vncoder.mvvm.ViewModel.MainViewModel
import com.vncoder.retrofit2_employee.Adapter.AdapterEmployee
import com.vncoder.retrofit2_employee.Model.Contact
import kotlinx.android.synthetic.main.activity_main.*


class ListStaffActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModel.NoteViewModelFactory(this.application)
        )[MainViewModel::class.java]
    }

    private val ActivityRequestCode2 = 2
    private lateinit var adapterEmployee: AdapterEmployee
    private lateinit var recyclerView: RecyclerView
    private lateinit var swiperefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv_cyclerview)
        swiperefresh = findViewById(R.id.swiperefresh)
        progressBar = findViewById(R.id.progressBar)

        rv_cyclerview.isLongClickable=true

        initControls()

        btn_Create.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }

        swiperefresh.setOnRefreshListener { initControls() }

    }

    private fun initControls() {
        progressBar.visibility = View.VISIBLE
        adapterEmployee = AdapterEmployee(this, onItemClick)
        rv_cyclerview.setHasFixedSize(true)
        rv_cyclerview.layoutManager = LinearLayoutManager(this)
        rv_cyclerview.adapter = adapterEmployee
        mainViewModel.getData().observe(this, Observer {
            adapterEmployee.setList(it as ArrayList<Contact>)
        })
        progressBar.visibility = View.GONE
    }

    private val onItemClick: (Contact)->Unit={
        val replyintent = Intent(this@ListStaffActivity, infoActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("detailEmployee", it)
            replyintent.putExtras(bundle)
            startActivityForResult(replyintent, ActivityRequestCode2)
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
                    listOf<Contact>()
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
                    listOf<Contact>()
                } else {
                    adapterEmployee.exampleList.filter {
                        it.FirstName?.toLowerCase()!!.contains(
                            newText.toString()
                        )
                    }
                }
                mainViewModel?.getData()?.observe(this@ListStaffActivity, Observer {
                    adapterEmployee.setList(it as ArrayList<Contact>)
                })
                adapterEmployee.notifyDataSetChanged()
                return false
            }
        })
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        return when (id) {
//            R.id.action_sort -> {
//                adapterEmployee?.exampleList.sortBy  { it.Email }
//                adapterEmployee.notifyDataSetChanged()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun onResume() {
        initControls()
        super.onResume()
    }

}

