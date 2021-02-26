package com.yawtseb.bestway.ui.frag.account

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.yawtseb.bestway.BuildConfig
import com.yawtseb.bestway.R
import com.yawtseb.bestway.base.BaseFrag
import com.yawtseb.bestway.databinding.FragmentAppInfoBinding

class AppInfoFrag : BaseFrag() {

    private val binding: FragmentAppInfoBinding by lazy { FragmentAppInfoBinding.inflate(layoutInflater) }
    private val appVersion = BuildConfig.VERSION_NAME

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appVersionText = "${getString(R.string.app_info)} $appVersion"
        binding.tvAppInfoVersion.text = appVersionText

        binding.tvAppInfoOpenSourceLibrary.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvAppInfoOpenSourceLibrary.text = getString(R.string.open_source_licenses)

        binding.tvAppInfoOpenSourceLibrary.setOnClickListener {
            startActivity(Intent(activity, OssLicensesMenuActivity::class.java))
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.open_source_licenses))
        }

    }

}