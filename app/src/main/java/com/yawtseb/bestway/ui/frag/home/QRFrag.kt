package com.yawtseb.bestway.ui.frag.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airetefacruo.myapplication.util.crypto.Encrypt
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.yawtseb.bestway.base.BaseFrag
import com.yawtseb.bestway.databinding.FragmentQRBinding
import com.yawtseb.bestway.model.OrderMenuVo
import com.yawtseb.bestway.util.ConstData.MODEL_KEY
import com.yawtseb.bestway.util.ConstData.ORDER_KEY
import com.yawtseb.bestway.util.ShowMsg.Companion.showLog
import org.json.JSONArray


class QRFrag : BaseFrag() {

    private val binding: FragmentQRBinding by lazy { FragmentQRBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val accessToken = sessionHelper.tokenVo!!.userAccessToken
        val orderList: JSONArray = JSONArray()
        if(requireArguments().getBoolean(ORDER_KEY)){
            val orderMenuVo: OrderMenuVo = requireArguments().getParcelable(MODEL_KEY)!!
            showLog(orderMenuVo.toString())
            orderList.put(orderMenuVo)
            showLog(orderList.toString())
        }
        else{

        }
        val QRCode = "$accessToken:$orderList"
        val enQRCode = Encrypt.encrypt(QRCode, 5)
        showLog(enQRCode, "enQRCode")
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(enQRCode, BarcodeFormat.QR_CODE, 250, 250)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            binding.ivQr.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



}