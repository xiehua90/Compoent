package com.example.xh.kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.example.xh.kotlin.R

import androidx.appcompat.app.AppCompatActivity

class TransactionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.frame)

        val name = intent?.getStringExtra(FRAG_NAME)
        if (name != null) {
            val className = "com.example.xh.kotlin.fragment.$name"
            val instance = Class.forName(className).newInstance()
            title = name
            if (instance is androidx.fragment.app.Fragment) {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, instance).commit()
            } else if (instance is android.app.Fragment) {
                fragmentManager.beginTransaction().replace(R.id.frameLayout, instance).commit()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val FRAG_NAME = "FRAG_NAME"
        fun startIntent(context: Context, fragName: String) {
            val intent = Intent(context, TransactionsActivity::class.java)
            intent.putExtra(FRAG_NAME, fragName)
            context.startActivity(intent)
        }
    }

}