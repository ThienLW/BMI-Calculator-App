package com.example.ttbmicalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttbmicalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            if (binding.etName.text?.isEmpty() == true)
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
            else{
                val intent = Intent(this, BMIActivity::class.java)
//                val upperName: String = binding.etName.text.toString().capitalize()
                val words: MutableList<String> = binding.etName.text.toString().split(" ").toMutableList()
                var upperName: String = ""
                for(word in words){
                    upperName += word.capitalize() +" "
                }
                upperName = upperName.trim()

                intent.putExtra(Constants.NAME, upperName)
                startActivity(intent)
            }
        }
    }
}