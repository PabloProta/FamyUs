package com.famy.us.invite

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.famy.us.core.utils.barcode.BarcodeGenerator
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Composable
fun InviteScreenContainer(viewModel: InviteScreenViewModel = koinViewModel()) {
    val familyInvite by remember {
        viewModel.familyInviteScreen
    }
    InviteScreen(familyInvite = familyInvite)
}

@Composable
internal fun InviteScreen(familyInvite: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Convide um membro:",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
        )
        Text(
            "Leia o QrCode abaixo do celular do novo membro",
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.size(64.dp))
        if (familyInvite.isNotEmpty()) {
            Image(
                painter = qrCodePainter(
                    bitmap = generateQrCode(
                        familyInvite,
                        600.dp,
                    ),
                ),
                contentDescription = "",
            )
        }
        Spacer(modifier = Modifier.size(80.dp))
        Text(text = "Siga as seguinte etapas para vincular um membro com a sua família:")
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = """
                 - Baixe o app no membro que quer adicionar
                 - Ao abrir o app clique em "vincular a uma nova família"
                 - leia o qr code a partir do celular do novo membro
            """.trimIndent(),
        )
    }
}

@Composable
private fun qrCodePainter(bitmap: Bitmap) = BitmapPainter(bitmap.asImageBitmap())

private fun generateQrCode(content: String, size: Dp): Bitmap = BarcodeGenerator
    .Builder()
    .setContent(content)
    .setSize(size.value.roundToInt())
    .encodeAsQrCode()
    .build()

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun InviteScreenPreview() {
    InviteScreen(familyInvite = "SAJHDKASHDKSHAJHDJ4343y4bf")
}
