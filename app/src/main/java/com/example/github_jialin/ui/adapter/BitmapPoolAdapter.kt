package com.example.github_jialin.ui.adapter

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool

class BitmapPoolAdapter : BitmapPool {
    override fun get(width: Int, height: Int, config: Bitmap.Config): Bitmap {
        return Bitmap.createBitmap(width, height, config)
    }

    override fun put(bitmap: Bitmap) {
        bitmap.recycle()
    }

    override fun clearMemory() {
    }

    override fun trimMemory(level: Int) {
    }

    override fun getMaxSize(): Long {
        return 512 * 1024
    }
    override fun setSizeMultiplier(sizeMultiplier: Float) {

    }
    override fun getDirty(width: Int, height: Int, config: Bitmap.Config): Bitmap {
        return Bitmap.createBitmap(width, height, config)
    }
}