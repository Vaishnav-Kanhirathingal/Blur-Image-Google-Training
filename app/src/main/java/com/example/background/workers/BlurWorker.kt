package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.R

class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    private val TAG = "BlurWorker"
    override fun doWork(): Result {
        val appContext = applicationContext
        makeStatusNotification("image is being blurred", appContext)

        return try {
            val picture = BitmapFactory.decodeResource(
                appContext.resources, R.drawable.android_cupcake
            )
            val output = blurBitmap(picture, applicationContext)
            val outputUri = writeBitmapToFile(applicationContext, output)
            makeStatusNotification("output is $outputUri", appContext)
            Result.success()
        } catch (t: Throwable) {
            Log.e(TAG, "error applying blur")
            Result.failure()
        }
    }
}