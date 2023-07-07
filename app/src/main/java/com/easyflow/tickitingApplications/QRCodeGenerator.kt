package com.easyflow.tickitingApplications

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


const val width = 800
const val height = 800
class QRCodeGenerator {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getQrCode(tripID: String, passengerCount: Int): Bitmap{
        val writer = QRCodeWriter()
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        try {
            //don't pass time from user phone instead make the machine put its own time.
            val bitMatrix = writer.encode("{\"trip_id\":\"${tripID}\", \"count\":\"${passengerCount}\"}", BarcodeFormat.QR_CODE, width, height)
            for(x in 0 until width){
                for(y in 0 until height){
                    bmp.setPixel(x, y, if(bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }

        }
        catch (e : WriterException){
            e.printStackTrace()
        }
        return bmp
    }
}