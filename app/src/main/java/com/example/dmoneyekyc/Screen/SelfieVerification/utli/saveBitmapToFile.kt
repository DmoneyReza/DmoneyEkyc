import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.google.mlkit.vision.common.InputImage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri? {
    try {
        // Create a file in the cache directory for the image
        val file = File(context.cacheDir, "output_image.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out) // Compress bitmap as JPEG
        }

        // Return the Uri using FileProvider (assuming it's set up in AndroidManifest)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider", // authority from your AndroidManifest
            file
        )
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}

fun inputImageToBitmap(inputImage: InputImage): Bitmap? {
    // Extract Bitmap from InputImage if possible
    return inputImage.bitmapInternal
}

// Function to save InputImage as JPEG
fun saveInputImageAsJpeg(context: Context, inputImage: InputImage): Uri? {
    val bitmap = inputImageToBitmap(inputImage)

    if (bitmap != null) {
        // Save the bitmap as a JPEG file and return the Uri
        return saveBitmapToFile(context, bitmap)
    } else {
        // Handle the case when Bitmap is not available
        return null
    }
}
