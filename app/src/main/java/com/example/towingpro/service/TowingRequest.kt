package com.example.towingpro.service

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.towingpro.databinding.RequestTowingBinding
import com.example.towingpro.map.MapActivity
import com.google.android.material.snackbar.Snackbar

class TowingRequest : Fragment() {
    private lateinit var binding: RequestTowingBinding
    private val REQUEST_CODE_LOCATION = 101
    private var map = HashMap<String, String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RequestTowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ccp.registerCarrierNumberEditText(binding.etPhone)

        binding.tvLocation.setOnClickListener {
            val intent = Intent(context, MapActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_LOCATION)
        }

        binding.btnConfirm.setOnClickListener {

            if (TextUtils.isEmpty(binding.etName.text) || TextUtils.isEmpty(binding.etPhone.text) || TextUtils.isEmpty(
                    binding.etCarModel.text
                )
                || TextUtils.isEmpty(binding.etDescription.text) || TextUtils.isEmpty(binding.tvAddress.text)
                || TextUtils.isEmpty(binding.etCarType.text)
            ) {
                showMessage("Complete missing fields")
            } else {

                map["name"] = binding.etName.text.toString()
                map["phone"] = binding.etPhone.text.toString()
                map["model"] = binding.etCarModel.text.toString()
                map["type"] = binding.etCarType.text.toString()
                map["description"] = binding.etDescription.text.toString()

//                viewModel.requestTow(map)

            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (data != null) {

            if (requestCode == REQUEST_CODE_LOCATION) {

                showMessage("Address saved")

                map["latitude"] = data.getStringExtra("lat").toString()
                map["longitude"] = data.getStringExtra("lng").toString()
                map["address"] = data.getStringExtra("address").toString()

                binding.tvAddress.text = data.getStringExtra("address").toString()

            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireContext(),
            requireView(),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}