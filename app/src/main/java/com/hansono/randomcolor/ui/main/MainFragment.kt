package com.hansono.randomcolor.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.hansono.randomcolor.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    lateinit var redButton : Button
    lateinit var greenButton : Button
    lateinit var blueButton : Button
    lateinit var nonColorButton : Button
    lateinit var guide : TextView
    lateinit var shuffle : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        redButton = view.findViewById(R.id.red_button)
        blueButton = view.findViewById(R.id.blue_button)
        greenButton = view.findViewById(R.id.green_button)
        nonColorButton = view.findViewById(R.id.noncolor_button)
        guide = view.findViewById(R.id.guide)
        shuffle = view.findViewById(R.id.shuffle)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.attach(this)
    }

}