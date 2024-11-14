import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class CircularTransformation(private val bitmapPool: BitmapPool) : BitmapTransformation() {

    override fun transform(bitmapPool: BitmapPool, source: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val size = Math.min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val bitmap = bitmapPool.get(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint()
        val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader

        val rectF = RectF(0f, 0f, size.toFloat(), size.toFloat())
        canvas.drawOval(rectF, paint)

        return bitmap
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other?.javaClass == javaClass
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}
}