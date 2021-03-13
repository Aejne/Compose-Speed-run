/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@Composable
fun HomeScreen() {

    var searchQuery by remember { mutableStateOf(TextFieldValue()) }
    val tabs = Tabs.values()

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                BottomNavigation(
                    Modifier
                        .navigationBarsHeight(additional = 56.dp),
                    backgroundColor = MaterialTheme.colors.background
                ) {
                    tabs.forEach { tab ->
                        BottomNavigationItem(
                            modifier = Modifier.navigationBarsPadding(bottom = true),
                            icon = { Icon(modifier = Modifier.size(18.dp), imageVector = tab.icon, contentDescription = null) },
                            label = {
                                Text(
                                    text = tab.title.toUpperCase(),
                                    style = MaterialTheme.typography.caption
                                )
                            },
                            selected = false,
                            onClick = { /*TODO*/ }
                        )
                    }
                }

                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = -28.dp),
                    onClick = { /*TODO*/ },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(
                        modifier = Modifier.requiredSize(24.dp),
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            TextField(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .height(56.dp)
                    .fillMaxWidth(),
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Row {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)

                        Spacer(modifier = Modifier.requiredWidth(4.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxHeight()
                                .align(Alignment.CenterVertically),
                            text = "Search",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                )
            )

            Text(
                modifier = Modifier
                    .paddingFromBaseline(top = 40.dp)
                    .padding(start = 16.dp),
                text = "Favorite collection".toUpperCase(),
                style = MaterialTheme.typography.h2
            )

            LazyRow(
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(collectionItems.chunked(2)) { (item1, item2) ->
                    Column {
                        CollectionItem(item = item1)
                        Spacer(modifier = Modifier.height(8.dp))
                        CollectionItem(item = item2)
                    }
                }
            }

            ExerciseRow(
                modifier = Modifier.padding(top = 32.dp),
                title = "Align your body",
                items = bodyExerciseItems
            )

            ExerciseRow(
                modifier = Modifier.padding(top = 32.dp),
                title = "Align your mind",
                items = mindExerciseItems
            )

            Spacer(modifier = Modifier.height(180.dp))
        }
    }
}

@Composable
fun CollectionItem(modifier: Modifier = Modifier, item: CollectionItem) {
    Card(
        modifier = modifier
            .requiredHeight(56.dp)
            .requiredWidth(192.dp),
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.requiredSize(56.dp),
                painter = painterResource(id = item.drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = item.title,
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
fun ExerciseRow(modifier: Modifier = Modifier, title: String, items: List<CollectionItem>) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onSurface,
            text = title.toUpperCase()
        )

        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(items) { item ->
                BodyExerciseItem(modifier = Modifier.padding(horizontal = 4.dp), item = item)
            }
        }
    }
}

@Preview
@Composable
fun CollectionItemPreview() {
    MyTheme {
        CollectionItem(item = collectionItems[2])
    }
}

@Composable
fun BodyExerciseItem(modifier: Modifier = Modifier, item: CollectionItem) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .requiredSize(88.dp)
                .clip(CircleShape),
            painter = painterResource(id = item.drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(
            modifier = Modifier
                .paddingFromBaseline(top = 24.dp),
            text = item.title,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun BodyExercicePreview() {
    MyTheme {
        BodyExerciseItem(modifier = Modifier.requiredWidth(88.dp), item = bodyExerciseItems.first())
    }
}

data class CollectionItem(val title: String, val drawable: Int)

private val collectionItems = listOf(
    CollectionItem("Short mantras", R.drawable.img_short_mantras),
    CollectionItem("Nature meditations", R.drawable.img_meditation),
    CollectionItem("Stress and anxiety", R.drawable.img_anxiety),
    CollectionItem("Self-massage", R.drawable.img_self_massage),
    CollectionItem("Overwhelmed", R.drawable.img_overwhelmed),
    CollectionItem("Nightly winddown", R.drawable.img_nightly_winddown)
)

private val bodyExerciseItems = listOf(
    CollectionItem("Inversions", R.drawable.img_inversions),
    CollectionItem("Quick yoga", R.drawable.img_quick_yoga),
    CollectionItem("Stretching", R.drawable.img_stretching),
    CollectionItem("Tabata", R.drawable.img_tabata),
    CollectionItem("HIIT", R.drawable.img_hiit),
    CollectionItem("Pre-natal yoga", R.drawable.img_pre_natal_yoga)
)

private val mindExerciseItems = listOf(
    CollectionItem("Meditate", R.drawable.img_meditation),
    CollectionItem("With kids", R.drawable.img_with_kids),
    CollectionItem("Aromatherapy", R.drawable.img_aromatherapy),
    CollectionItem("On the go", R.drawable.img_on_the_go),
    CollectionItem("With pets", R.drawable.img_with_pets),
    CollectionItem("High stress", R.drawable.img_high_stress),
)

private enum class Tabs(
    val title: String,
    val icon: ImageVector
) {
    HOME("Home", Icons.Filled.Spa),
    PROFILE("Profile", Icons.Filled.AccountCircle),
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightHomePreview() {
    MyTheme {
        HomeScreen()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkHomePreview() {
    MyTheme(darkTheme = true) {
        HomeScreen()
    }
}
