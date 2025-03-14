package com.example.mosubookshelf.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavigationItemType(val item: BottomNavigationItem) {
    NEW(BottomNavigationItem("New!", Icons.Rounded.ShoppingCart)),
    SEARCH(BottomNavigationItem("Search", Icons.Rounded.Search))
}

data class BottomNavigationItem(val title: String, val icon: ImageVector)