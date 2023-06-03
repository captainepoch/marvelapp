package com.adolfo.marvel.common.navigation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adolfo.marvel.R
import com.adolfo.marvel.R.string

@Composable
fun InformationView(
    modifier: Modifier = Modifier,
    drawableId: Int,
    title: String,
    description: String,
    onAcceptText: String,
    onAccept: () -> Unit,
    onDeclineText: String,
    onDecline: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = drawableId), contentDescription = "Error Logo")
            Text(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = title, style = MaterialTheme.typography.h5
            )
            Text(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = description
            )
        }

        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { onAccept() }) {
                Text(text = onAcceptText)
            }
            Button(onClick = { onDecline() }) {
                Text(text = onDeclineText)
            }
        }
    }
}

@Preview(
    name = "PIXEL_4_XL",
    device = Devices.NEXUS_5,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun InformationViewPreview() {
    InformationView(
        drawableId = R.drawable.ic_error_outline,
        title = "Error Title",
        description = "Error Description",
        onAcceptText = "Accept",
        onAccept = {},
        onDeclineText = "Decline Long Text",
        onDecline = {}
    )
}

@Composable
fun GenericErrorView(
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    return InformationView(
        drawableId = R.drawable.ic_error_outline,
        title = stringResource(id = string.unknown_error_title),
        description = stringResource(id = string.unknown_error_title),
        onAcceptText = stringResource(id = string.button_retry),
        onAccept = { onAccept() },
        onDeclineText = stringResource(id = string.button_exit),
        onDecline = { onDecline() }
    )
}

@Composable
fun NetworkErrorView(
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    return InformationView(
        drawableId = R.drawable.ic_error_outline,
        title = stringResource(id = string.network_connection_error_title),
        description = stringResource(id = string.network_connection_error_desc),
        onAcceptText = stringResource(id = string.button_retry),
        onAccept = { onAccept() },
        onDeclineText = stringResource(id = string.button_exit),
        onDecline = { onDecline() }
    )
}

@Composable
fun ServerErrorView(
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    return InformationView(
        drawableId = R.drawable.ic_error_outline,
        title = stringResource(id = string.server_error_title),
        description = stringResource(id = string.server_error_desc),
        onAcceptText = stringResource(id = string.button_retry),
        onAccept = { onAccept() },
        onDeclineText = stringResource(id = string.button_exit),
        onDecline = { onDecline() }
    )
}
