package com.example.xh.kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.xh.kotlin.R
import kotlinx.android.synthetic.main.spinnner.view.*

class SpinnerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.spinnner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = view.findViewById(R.id.spinner)
        val array = activity?.resources?.getStringArray(R.array.city_array)

        spinner.adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, array)
    }
}