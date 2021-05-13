package com.hansono.randomcolor.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansono.randomcolor.R
import com.hansono.randomcolor.model.GameColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val gameColor = GameColor()

    fun judge(view : MainFragment)
    {
        viewModelScope.launch(Dispatchers.Default) {
            val won = gameColor.judge()
            if (won) {
                withContext(Dispatchers.Main) {
                    view.guide.text = view.context?.getString(R.string.win)
                }
            }
        }
    }

    fun refresh(view : MainFragment)
    {
        viewModelScope.launch(Dispatchers.Default) {
            gameColor.refresh()
            withContext(Dispatchers.Main) {
                view.guide.text = view.context?.getString(R.string.guide)
                judge(view)
            }
        }
    }

    fun pressColorButton(type : GameColor.Type, view : MainFragment)
    {
        viewModelScope.launch(Dispatchers.Default) {
            gameColor.reinforce_all(type)
            val redColor = gameColor.get(GameColor.Type.Red).toArgb()
            val greenColor = gameColor.get(GameColor.Type.Green).toArgb()
            val blueColor = gameColor.get(GameColor.Type.Blue).toArgb()
            withContext(Dispatchers.Main) {
                view.redButton.setBackgroundColor(redColor)
                view.greenButton.setBackgroundColor(greenColor)
                view.blueButton.setBackgroundColor(blueColor)
                judge(view)
            }
        }
    }

    fun pressNonColorButton(view : MainFragment)
    {
        viewModelScope.launch(Dispatchers.Default) {
            val chosenType = gameColor.sharpen()
            val chosenColor = gameColor.get(chosenType).toArgb()
            withContext(Dispatchers.Main) {
                when(chosenType)
                {
                    GameColor.Type.Red -> view.redButton.setBackgroundColor(chosenColor)
                    GameColor.Type.Green -> view.greenButton.setBackgroundColor(chosenColor)
                    GameColor.Type.Blue -> view.blueButton.setBackgroundColor(chosenColor)
                }
                judge(view)
            }
        }
    }


    fun attach(view : MainFragment)
    {
        view.redButton.setOnClickListener {
            pressColorButton(GameColor.Type.Red, view)
        }

        view.greenButton.setOnClickListener {
            pressColorButton(GameColor.Type.Green, view)
        }

        view.blueButton.setOnClickListener {
            pressColorButton(GameColor.Type.Blue, view)
        }

        view.nonColorButton.setOnClickListener {
            pressNonColorButton(view)
        }

        view.shuffle.setOnClickListener {
            refresh(view)
        }

        refresh(view)
    }
}