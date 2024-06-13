package ru.blackmirrror.myplaces.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.blackmirrror.myplaces.common.R

@Composable
fun ErrorNotification(
    visibility: Boolean,
    message: String,
    onRefresh: () -> Unit
) {
    AnimatedVisibility(
        visible = visibility
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = message,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(R.drawable.icon_refresh_24),
                    contentDescription = stringResource(R.string.content_description_refresh),
                    modifier = Modifier.clickable {
                        onRefresh()
                    }
                )
            }
        }
    }
}