package com.example.qrcodescanner

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class genqrcode: AppCompatActivity() {

    private lateinit var entered_text: EditText
    private lateinit var genButton: Button
    private lateinit var QRImage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTION_BAR)
        actionBar?.hide()

        setContentView(R.layout.gen_qr_code)

        entered_text = findViewById(R.id.textfield)
        genButton = findViewById(R.id.generateButton)
        QRImage = findViewById(R.id.genedQRcode)

        genButton.setOnClickListener(){
            //generate the QR Code for the entered text here
            val string_to_convert = entered_text.text.toString().trim()
            if(string_to_convert==""){
                Toast.makeText(this@genqrcode, "Enter some text!", Toast.LENGTH_SHORT).show()
            }else{
                //generate QR Code
                val writer = QRCodeWriter()
                try{
                    val bitMatrix = writer.encode(string_to_convert, BarcodeFormat.QR_CODE, 512, 512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                    for(x in 0 until width){
                        for (y in 0 until height){
                            bmp.setPixel(x, y, if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    QRImage.setImageBitmap(bmp)

                }catch(e: WriterException){
                    e.printStackTrace()
                }
            }
        }

    }
}