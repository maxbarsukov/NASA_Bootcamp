package ru.myitschool.deepspace.navigator.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import ru.myitschool.deepspace.R

class PlanetsActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planets)
    }
}