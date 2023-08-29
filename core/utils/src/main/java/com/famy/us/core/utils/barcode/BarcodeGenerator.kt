package com.famy.us.core.utils.barcode

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

/**
 * Class to generate a barcode.
 */
class BarcodeGenerator {

    class Builder {
        private var current: BarcodeModel = BarcodeModel.EMPTY

        /**
         * Method to set the text to be encoded.
         */
        fun setText(content: String): Builder {
            current = current.copy(text = content)
            return this
        }

        fun setContent(content: Any?): Builder {
            current = current.copy(text = content.toString())
            return this
        }

        /**
         * Method to set how should encode the bitmap.
         */
        fun encodeAsQrCode(): Builder {
            current = current.copy(type = BarcodeFormat.QR_CODE)
            return this
        }

        /**
         * Method to set the color of the QrCode.
         *
         * @param color hexadecimal value representing a color.
         */
        fun setColor(color: Int): Builder {
            current = current.copy(color = color)
            return this
        }

        /**
         * Method to set the size of the qrCode.
         *
         * @param size the size of the qrCode.
         */
        fun setSize(size: Int): Builder {
            current = current.copy(size = size)
            return this
        }

        /**
         * Method to build the barcode.
         */
        fun build(): Bitmap {
            checkContent()
            return current.run {
                buildQrCode(text, type, size, color)
            }
        }

        private fun buildQrCode(text: String, type: BarcodeFormat, size: Int, color: Int): Bitmap {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(text, type, size, size)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val pixels = IntArray(width * height)
            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    val shouldColorPixel = bitMatrix?.get(x, y) ?: false
                    val pixelColor: Int = if (shouldColorPixel) {
                        color
                    } else {
                        Color.WHITE
                    }
                    pixels[offset + x] = pixelColor
                }
            }
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        }

        private fun checkContent() {
            if (current.text.isEmpty()) {
                throw QrContentIsMissing()
            }
        }
    }

    private class QrContentIsMissing : Exception(
        """
        Qrcode content is missing, call 'setText()' or the 'setContent()'
        before build call.
        """.trimIndent(),
    )
}
