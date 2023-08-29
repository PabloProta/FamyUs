package com.famy.us.core.utils.barcode

import android.graphics.Color
import com.google.zxing.BarcodeFormat

/**
 * Model that will be used to build a Barcode based on text content or an data class.
 *
 * @property text the content text that will be encoded.
 * @property size the encoded size for the bitmap.
 * @property type how the content should be encoded.
 */
data class BarcodeModel(
    val text: String,
    val size: Int = 256,
    val color: Int = Color.BLACK,
    val type: BarcodeFormat,
) {
    companion object {
        val EMPTY = BarcodeModel(
            text = "",
            type = BarcodeFormat.QR_CODE,
        )
    }
}
