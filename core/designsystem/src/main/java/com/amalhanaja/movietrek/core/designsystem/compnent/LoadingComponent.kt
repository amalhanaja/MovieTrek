package com.amalhanaja.movietrek.core.designsystem.compnent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import com.amalhanaja.movietrek.core.designsystem.Constants
import com.amalhanaja.movietrek.core.designsystem.spacings

@Composable
fun LoadingComponent(
    modifier: Modifier = Modifier,
    text: String? = null,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(MaterialTheme.spacings.l))
            if (text.isNullOrBlank().not()) {
                Text(
                    text = text.orEmpty(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = Constants.SINGLE_LINE,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.testTag("loading-text")
                )
            }
        }
    }
}