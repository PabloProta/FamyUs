package com.famy.us.authentication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.famy.us.authentication.R

@Composable
internal fun SignInButton(
    isLoadingProvider: () -> Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(
                onClick = onClick,
            ),
    ) {
        if (isLoadingProvider()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(ImageVector.vectorResource(id = R.drawable.ic_google).defaultWidth),
            )
        } else {
            Image(
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                    ),
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = null,
            )
        }
    }
}
