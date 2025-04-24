package com.flareframe.ui.navBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.StructureKind
@ExperimentalSerializationApi
data class BottomNavigationItem(
    val route: Any,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNotis: Boolean,
    val badgeCount: Int?,
)
