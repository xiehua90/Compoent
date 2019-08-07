package com.example.xh.kotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.xh.kotlin.R
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Transformations
import com.example.xh.kotlin.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val list = listOf("CameraFragment", "GroupListFragment", "SpinnerFragment", "ViewPageFragment",
            "WidgetFragment", "TecentPlayerFragment","ConstraintFragment","RicheditorFragment","CacheFragment",
            "Timber"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = MainAdapter(this)
        recyclerView.adapter = adapter
        adapter.listener = object : MainAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                TransactionsActivity.startIntent(this@MainActivity, list[position])
            }
        }
        adapter.submitList(list)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)

        }
    }
}
