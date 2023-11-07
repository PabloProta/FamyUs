package com.famy.us.feature.note.createNote.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun DefineNoteScoreScreen(
    modifier: Modifier = Modifier,
    onDefine: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(24.dp)
            .padding(top = 40.dp),
    ) {
        var score by remember {
            mutableFloatStateOf(0f)
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 32.dp),
            text = "Defina uma pontuação para a tarefa:",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = score.roundToInt().toString(),
            style = MaterialTheme.typography.displayMedium
        )
        Slider(
            value = score,
            valueRange = 1f.rangeTo(100f),
            onValueChange = {
                score = it
            },
        )

        Spacer(modifier = Modifier.weight(1f))

        ElevatedButton(
            modifier = Modifier
                .align(Alignment.End),
            onClick = {
                onDefine(score.roundToInt())
            },
        ) {
            Text(text = "Salvar")
        }
    }
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun DefineNoteScoreScreenPreview() {
    DefineNoteScoreScreen(modifier = Modifier.fillMaxSize(), {})
}
