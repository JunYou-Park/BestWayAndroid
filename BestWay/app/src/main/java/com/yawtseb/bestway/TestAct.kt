package com.yawtseb.bestway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yawtseb.bestway.databinding.ActivityTestBinding
import com.yawtseb.bestway.ui.dialog.pincode.CreatePinCodeDialog

class TestAct : AppCompatActivity() {

    private val binding: ActivityTestBinding by lazy { ActivityTestBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lvTest.create()

        binding.btnTestShowPinDialog.setOnClickListener {
            binding.lvTest.isVisible()
        }
    }
}