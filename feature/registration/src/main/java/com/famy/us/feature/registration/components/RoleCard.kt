package com.famy.us.feature.registration.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RoleCard(
    roleTitle: String,
    roleDescription: String,
    onClickIt: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(148.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        onClick = onClickIt,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = roleTitle,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = roleDescription,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(
    device = "id:pixel_xl",
    backgroundColor = 0xFFFFFFFF,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
private fun ComponentPreview() {
    RoleCard(
        "Ola mundo",
        "Praesent consectetur sapien ut diam consequat, ut placerat elit pharetra." +
            "Interdum et malesuada fames ac ante ipsum primis in faucibus.",
        onClickIt = {},
    )
}
