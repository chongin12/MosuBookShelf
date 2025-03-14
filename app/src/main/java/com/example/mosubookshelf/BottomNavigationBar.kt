package com.example.mosubookshelf

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mosubookshelf.models.BottomNavigationItemType


@Composable
fun BottomNavigationBar(
    navigationItems: List<BottomNavigationItemType>,
    selectedNavigationItemState: MutableState<BottomNavigationItemType>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        navigationItems.forEach { item ->
            BottomNavigationItemTypeView(
                item,
                selectedNavigationItemState.value == item,
                Modifier.clickable { selectedNavigationItemState.value = item }
            )
        }
    }
}

@Composable
fun BottomNavigationItemTypeView(type: BottomNavigationItemType, isSelected: Boolean, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(16.dp)
    ) {
        Icon(type.item.icon, "navigationIcon")

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = type.item.title,
            color = if (isSelected) Color.Black else Color.Gray
        )
    }
}

