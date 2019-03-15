package com.masbubulkarim.facedetectionapp

import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.FaceDetector
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.RectF
import android.util.SparseArray
import com.google.android.gms.vision.face.Face


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn = button
        btn.setOnClickListener {

            //bitmap for image
            var myImageView = imgview
            var options = BitmapFactory.Options()
            options.inMutable = true
            var myBitmap = BitmapFactory.decodeResource(applicationContext.resources,
                    R.drawable.test1, options)

            //creating stroke
            var myRectPaint = Paint()
            myRectPaint.strokeWidth = 5f
            myRectPaint.color = Color.RED
            myRectPaint.style = Paint.Style.STROKE

            //creating canvas
            var tempBitmap = Bitmap.createBitmap(myBitmap.width, myBitmap.height,
                    Bitmap.Config.RGB_565)
            var tempCanvas = Canvas(tempBitmap)
            tempCanvas.drawBitmap(myBitmap, 0f, 0f, null)

            //face detector
            var faceDetector = FaceDetector.Builder(applicationContext).setTrackingEnabled(false)
                    .build()
            if(!faceDetector.isOperational){
                AlertDialog.Builder(btn.context).setMessage("Could not set up the face detector!").show()
                return@setOnClickListener
            }

            var frame = Frame.Builder().setBitmap(myBitmap).build()
            var faces: SparseArray<Face> = faceDetector.detect(frame)
            for (i in 0 until faces.size()) {
                val thisFace = faces.valueAt(i)
                val x1 = thisFace.position.x
                val y1 = thisFace.position.y
                val x2 = x1 + thisFace.width
                val y2 = y1 + thisFace.height
                tempCanvas.drawRoundRect(RectF(x1, y1, x2, y2), 2f, 2f, myRectPaint)
            }
            myImageView.setImageDrawable(BitmapDrawable(resources, tempBitmap))
        }
    }


}
