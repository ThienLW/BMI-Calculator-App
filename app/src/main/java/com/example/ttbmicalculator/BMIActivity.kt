package com.example.ttbmicalculator

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttbmicalculator.databinding.ActivityBmiactivityBinding
import java.text.DecimalFormat

class BMIActivity : AppCompatActivity() {

    lateinit var binding: ActivityBmiactivityBinding
    var selectedMetric:String? = null
    var selectedGender:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val metricOptions = arrayOf(Constants.METRIC_OPT1, Constants.METRIC_OPT2)
        val adapterMetric = ArrayAdapter(this, R.layout.simple_spinner_item, metricOptions)
        adapterMetric.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.metricSpinner.adapter = adapterMetric

        binding.metricSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(this@BMIActivity, "Selected ${Constants.METRIC_OPTIONS[p2]}", Toast.LENGTH_SHORT).show()
                selectedMetric = metricOptions[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val genderOptions = arrayOf(Constants.GENDER_OPT1, Constants.GENDER_OPT2, Constants.GENDER_OPT3)
        val adapterGender = ArrayAdapter(this, R.layout.simple_spinner_item, genderOptions)
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genderSpinner.adapter = adapterGender

        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(this@BMIActivity, "Selected ${Constants.GENDER_OPTIONS[p2]}", Toast.LENGTH_SHORT).show()
                selectedGender = p2

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        normalDisplay()

//        BMI Calculate
        binding.btnCalc.setOnClickListener {
            getInfor()
            calculateBMI()
            binding.tvAppear.visibility = View.GONE
            binding.tvInfor.visibility = View.VISIBLE
            binding.tvBMIRange.visibility = View.VISIBLE
            binding.tvCategory.visibility = View.VISIBLE
        }

//        Reset Button
        binding.btnReset.setOnClickListener {
            clearDisplay()
            binding.tvAppear.visibility = View.VISIBLE
            binding.tvInfor.visibility = View.GONE
            binding.tvBMIRange.visibility = View.GONE
            binding.tvCategory.visibility = View.GONE
        }

//        Back Buntton
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
//            val name:String? = null
//            intent.putExtra(Constants.NAME, name)
            startActivity(intent)
        }




    }

    private fun normalDisplay() {
        binding.tvAppear.visibility = View.VISIBLE
        binding.tvInfor.visibility = View.GONE
        binding.tvBMIRange.visibility = View.GONE
        binding.tvCategory.visibility = View.GONE
    }

    private fun getInfor() {
        var userName:String? = null
        var age:Int? = null
        var gender:String? = null


        userName = intent.getStringExtra(Constants.NAME)

        try {
            age = binding.etAge.text.toString().toInt()
        }
        catch (ex: NumberFormatException){
            Toast.makeText(this, "Please enter correct number format for age", Toast.LENGTH_SHORT).show()
            age = 0
        }
        gender = selectedGender?.let { binding.genderSpinner.getItemAtPosition(it).toString() }

        binding.tvInfor.setText("$userName, age: $age, gender: $gender")

    }

    private fun clearDisplay() {
        binding.etAge.setText("")
        binding.etWeight.setText("")
        binding.etHeight.setText("")
    }

    private fun calculateBMI() {

        var weight: Float = 0.0f
        var height: Float = 0.0f

        val df = DecimalFormat("#.##")
        var BMI: Float = 0.0f

        try {
            var weight: Float = binding.etWeight.text.toString().toFloat()
            var height: Float = binding.etHeight.text.toString().toFloat()


            BMI = if (selectedMetric.equals(Constants.METRIC_OPT1)){
                height/=100.0f
                weight/(height*height)
            } else
                703*(weight/(height*height))

            if (BMI<16.0f)
                binding.tvCategory.setText("Severe Thinness")
            else if((BMI>=16.0f) && (BMI<17.0f))
                binding.tvCategory.setText("Moderate Thinness")
            else if((BMI>=17.0f) && (BMI<18.5f))
                binding.tvCategory.setText("Mild Thinness")
            else if((BMI>=18.5f) && (BMI<25.0f))
                binding.tvCategory.setText("Normal")
            else if((BMI>=25.0f) && (BMI<30.0f))
                binding.tvCategory.setText("Overweight")
            else if((BMI>=30.0f) && (BMI<35.0f))
                binding.tvCategory.setText("Overweight Class I")
            else if((BMI>=35.0f) && (BMI<40.0f))
                binding.tvCategory.setText("Overweight Class II")
            else
                binding.tvCategory.setText("Overweight Class III")

            binding.tvBMIRange.setText("${df.format(BMI)}")

        } catch (ex: NumberFormatException) {
            Toast.makeText(this, "Please enter correct number format for weight and height", Toast.LENGTH_SHORT).show()
        }

    }
}