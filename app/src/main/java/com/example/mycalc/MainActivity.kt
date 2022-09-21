package com.example.mycalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException
import java.nio.file.attribute.AttributeView

class MainActivity : AppCompatActivity() {
    var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)

    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastNumeric = true

    }

    fun onClrScreen(view: View) {
        tvInput?.text = ""
        lastDot = false
        lastNumeric = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperatorAdded(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }

        }
    }

    fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-"))
        }


    }

    fun removeExtraZero(value: String): String {
        var result = value
        if (value.endsWith(".0")) {
            result = value.substring(0, value.length - 2)
        }
        return result


    }

    fun onEqual(view: View) {


        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                when {
                    tvValue.contains("-") -> {
                        val splitedValue = tvValue.split("-")
                        var one = splitedValue[0]
                        val two = splitedValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvInput?.text =
                            removeExtraZero((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {
                        val splitedValue = tvValue.split("+")
                        var one = splitedValue[0]
                        val two = splitedValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvInput?.text =
                            removeExtraZero((one.toDouble() + two.toDouble()).toString())
                    }
                    tvValue.contains("/") -> {
                        val splitedValue = tvValue.split("/")
                        var one = splitedValue[0]
                        val two = splitedValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvInput?.text =
                            removeExtraZero((one.toDouble() / two.toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        val splitedValue = tvValue.split("*")
                        var one = splitedValue[0]
                        val two = splitedValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvInput?.text =
                            removeExtraZero((one.toDouble() * two.toDouble()).toString())
                    }


                }


            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }


    }


}
