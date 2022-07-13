package com.example.qrcodescanner

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.webkit.URLUtil
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode

class scanqr: AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var decodedCodeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTION_BAR)
        actionBar?.hide()

        setContentView(R.layout.scan_qr_activity)

        decodedCodeText = findViewById(R.id.decodedCode)

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)==
                PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 123)

        }else{
            startScanning()
        }

    }

    private fun startScanning() {
        val scannerView: CodeScannerView = findViewById(R.id.scanner)
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scanning complete!", Toast.LENGTH_SHORT).show()
                val string1 = it.text
                if(URLUtil.isValidUrl(string1)){
                    //url is valid
                    decodedCodeText.setText(string1)
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.text)))
                }else{
                    decodedCodeText.setText(string1)
                }
            }
        }
        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Camera Error!", Toast.LENGTH_SHORT).show()
            }
        }
        scannerView.setOnClickListener{
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Camera permission Granted", Toast.LENGTH_SHORT).show()
                startScanning()
            }else{
                Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized){
            codeScanner?.startPreview()
        }
    }

    override fun onPause() {
        if(::codeScanner.isInitialized){
            codeScanner?.releaseResources()
        }
        super.onPause()
    }

}