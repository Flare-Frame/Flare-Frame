package com.flareframe.ui.screens.upload

//
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ImageSearch
import androidx.compose.material.icons.twotone.PhotoCamera
import androidx.compose.material.icons.twotone.Tag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.flareframe.ui.screens.HashTagChip
import com.flareframe.ui.states.UserState
import com.flareframe.viewmodels.UploadPostViewModel


@Composable
fun UploadScreen(
    modifier: Modifier = Modifier,
    viewModel: UploadPostViewModel,
    onOpenCamera: () -> Unit,
    onPostUploaded: () -> Unit,
    userState: UserState,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val hashTags by viewModel.tags.collectAsStateWithLifecycle()
    val pickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            viewModel.galleryPicker(uri)
            Log.w("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    LaunchedEffect(uiState.isUploaded, uiState.isLoading) {
        if (uiState.isUploaded == true && uiState.isLoading == false) {


            onPostUploaded()
        }
    }
    OutlinedCard(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ////////////////////////////////////////////////////////////
            Row(
                modifier = Modifier,
            ) {
                UploadHeadingCard("Upload")
            }
            ////////////////////////////////////////////////////////
            Spacer(Modifier.padding(vertical = 10.dp))
            Box(
                Modifier.size(width = 200.dp, height = 200.dp)
            ) {
                AsyncImage(
                    model = if (uiState.pictureTaken != null) {
                        uiState.pictureTaken
                    } else {
                        uiState.uploadUri
                    },
                    contentDescription = "Image to post.", contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .aspectRatio(1f)
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            OutlinedTextField(
                value = uiState.caption,
                onValueChange = { newString ->
                    viewModel.updateCaption(newString)
                },
                isError = uiState.captionErrorMessage.isNotEmpty(),
                supportingText = { Text(uiState.captionErrorMessage) },
                modifier = Modifier,
                placeholder = { Text(text = "Post caption") },
                maxLines = 3
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            OutlinedTextField(
                placeholder = { Text(text = "Add hashtags") },
                value = uiState.currentTag,
                onValueChange = { newString -> viewModel.updateCurrentTag(newString) },
                isError = uiState.tagErrorMessage.isNotEmpty(), // if empty its  an error
                supportingText = { Text(uiState.tagErrorMessage) },
                modifier = Modifier,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.updateTags()
                        },
                        modifier = Modifier,

                        ) {
                        Icon(Icons.TwoTone.Tag, "Add tag")
                    }
                }
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            LazyRow {
                items(hashTags.size) { hashTag ->
                    HashTagChip(
                        hashTag = hashTags[hashTag],
                        onRemove = { viewModel.removeTag(hashTag) },
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                OutlinedButton(
                    onClick = {
                        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageAndVideo))
                    }, modifier = Modifier

                        .padding(start = 10.dp)
                ) {
                    Icon(Icons.TwoTone.ImageSearch, "Upload media")

                }
                ExtendedFloatingActionButton(onClick = {
                    Log.d("Upload","Upload clicked.")
                    viewModel.makePost(userState) }) {     // this will be handled by VM
                    Text("Post")
                }
                OutlinedButton(
                    onClick = {
                        viewModel.resetImages()
                        onOpenCamera()

                    }, modifier = Modifier


                ) {
                    Icon(Icons.TwoTone.PhotoCamera, "Camera")

                }


            }

        }
    }


}

@Composable
fun UploadHeadingCard(heading: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,

            ),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = heading,
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )


        }
    }
}




