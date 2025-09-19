package com.manager1700.soccer.ui.components.input

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manager1700.soccer.R
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorGrey_3b
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.PreviewApp
import java.io.File

@Composable
fun PhotoPickerField(
    imageUrl: String?,
    onPhotoPickerClick: () -> Unit,
    onDeletePhotoClick: () -> Unit,
    showUploadButton: Boolean = true,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    if (imageUrl != null) {
        // Show selected photo with delete button
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onPhotoPickerClick() }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(File(imageUrl))
                    .build(),
                contentDescription = "Player photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.75f),
                contentScale = ContentScale.Crop
            )

            // Delete button in top right corner
            IconButton(
                onClick = onDeletePhotoClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(36.dp)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_delete),
                    contentDescription = "Delete photo",
                )
            }
        }
    } else {
        // Show upload button
        if (showUploadButton) {
            Box(
                modifier = Modifier
                    .width(75.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorWhite)
                    .border(1.dp, colorGrey_3b, RoundedCornerShape(8.dp))
                    .clickable { onPhotoPickerClick() },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_upload),
                        contentDescription = "Upload photo",
                    )
                }
            }
        }
    }
}

@PreviewApp
@Composable
fun PhotoPickerFieldPreview() {
    SoccerManagerTheme {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .padding(16.dp)
        ) {
            PhotoPickerField(
                imageUrl = null,
                onPhotoPickerClick = {},
                onDeletePhotoClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            PhotoPickerField(
                imageUrl = "https://example.com/photo.jpg",
                onPhotoPickerClick = {},
                onDeletePhotoClick = {}
            )
        }
    }
}
