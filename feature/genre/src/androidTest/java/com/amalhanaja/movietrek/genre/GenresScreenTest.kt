package com.amalhanaja.movietrek.genre

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class GenresScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val genres = listOf(
        DisplayableGenre(1, "Action"),
        DisplayableGenre(2, "Comedy"),
        DisplayableGenre(3, "Adventure"),
        DisplayableGenre(4, "Horror"),
    )

    @Test
    fun testLoadingState_shouldShowLoadingComponent() {
        // Arrange
        with(composeTestRule) {
            val state = GenresUiState.Loading

            // Act
            setContent {
                GenresScreen(genresUiState = state, onItemClick = {})
            }

            // Assert
            onNodeWithTag("state-loading").assertExists()
        }
    }

    @Test
    fun testShownState_shouldShowGenreList() {
        // Arrange
        with(composeTestRule) {
            val state = GenresUiState.Shown(genres)

            // Act
            setContent {
                GenresScreen(genresUiState = state, onItemClick = {})
            }

            // Assert
            onNodeWithTag("state-shown")
                .assertExists()
                .onChildren()
                .assertAll(hasClickAction())
                .assertCountEquals(4)
        }
    }

    @Test
    fun testClickItem_shouldTriggerOnItemClick() {
        // Arrange
        var selectedItem: DisplayableGenre? = null
        with(composeTestRule) {
            val state = GenresUiState.Shown(genres)
            setContent {
                GenresScreen(genresUiState = state, onItemClick = { selectedItem = it })
            }

            // Act
            onNodeWithText("Comedy").performClick()

            // Assert
            assertEquals(DisplayableGenre(2, "Comedy"), selectedItem)
        }
    }
}