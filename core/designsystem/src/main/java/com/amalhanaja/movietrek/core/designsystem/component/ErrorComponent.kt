package com.amalhanaja.movietrek.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import com.amalhanaja.movietrek.core.designsystem.Constants
import com.amalhanaja.movietrek.core.designsystem.spacings

@Composable
fun ErrorComponent(
    title: String,
    actionText: String,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    illustration: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            illustration?.let {
                it.invoke()
                Spacer(modifier = Modifier.height(MaterialTheme.spacings.l))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                maxLines = Constants.SINGLE_LINE,
                overflow = TextOverflow.Clip,
            )
            description?.let {
                Spacer(modifier = Modifier.height(MaterialTheme.spacings.s))
                Text(
                    text = description,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.testTag("error-description")
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacings.m))
            TextButton(
                onClick = onActionClick,
            ) {
                Text(text = actionText)
            }
        }
    }
}