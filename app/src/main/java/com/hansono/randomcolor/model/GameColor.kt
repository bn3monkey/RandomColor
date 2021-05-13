package com.hansono.randomcolor.model

import android.graphics.Color
import android.graphics.ColorSpace
import kotlin.random.Random

class GameColor {
    enum class Type {
        Red,
        Green,
        Blue,
    }


    // Color Make
    companion object {
        private const val range = 2 shl 4
        private const val half_range = range / 2
    }

    private fun makeIndividual() : Int {
        return Random.nextInt(0, half_range)
    }

    private fun make(type : Type) : IntArray {

        val colorSet = IntArray(3) {
            makeIndividual()
        }
        colorSet[type.ordinal] += half_range
        return colorSet
    }
    private fun make(type : Type, array : IntArray) {
        for (index in array.indices)
        {
            array[index] = makeIndividual()
        }
        array[type.ordinal] += half_range
    }

    private val buttonColors = Type.values().associate {
        it to make(it)
    }

    private fun equalColor(from : IntArray, to : IntArray) : Boolean
    {
        for (type in Type.values())
        {
            if(from[type.ordinal] != to[type.ordinal])
                return false
        }
        return true
    }


    // Color Getter
    private fun normalize(colorStack : Int) : Float {
        return colorStack.toFloat() / range.toFloat()
    }
    fun get(type : Type) : Color
    {
        val buttonColor = buttonColors[type]!!
        val red = normalize(buttonColor[Type.Red.ordinal])
        val green = normalize(buttonColor[Type.Green.ordinal])
        val blue = normalize(buttonColor[Type.Blue.ordinal])
        return Color.valueOf(red, green ,blue)
    }

    // Color Function
    fun refresh() {
        for (type in Type.values())
        {
            val buttonColor = buttonColors[type]!!
            make(type, buttonColor)
        }
    }
    fun reinforce_all(type : Type) {
        val ordinal = type.ordinal
        for (buttonColor in buttonColors.values) {
            buttonColor[ordinal] += 1
            buttonColor[ordinal] %= range
        }

        val chosenButtonColor = buttonColors[type]!!
        chosenButtonColor[ordinal] += 2
        chosenButtonColor[ordinal] %= range
    }

    fun sharpen() : Type
    {
        val allTypes = Type.values()
        val chosenType = allTypes[Random.nextInt(0, allTypes.size)]

        val chosenButtonColor = buttonColors[chosenType]!!
        val color = chosenButtonColor[chosenType.ordinal]
        Type.values().forEach {
            chosenButtonColor[it.ordinal] = 0
        }
        chosenButtonColor[chosenType.ordinal] = color

        return chosenType
    }

    fun judge() : Boolean {
        val redButtonColor = buttonColors[Type.Red]!!
        val greenButtonColor = buttonColors[Type.Green]!!
        val blueButtonColor = buttonColors[Type.Blue]!!
        val first =  equalColor(redButtonColor, greenButtonColor)
        val second = equalColor(greenButtonColor, blueButtonColor)
        return first && second
    }
}