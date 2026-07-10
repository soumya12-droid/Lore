package com.soumya.lore.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soumya.lore.ui.theme.LoreOutline

/**
 * A single "recent search" pill. Low visual weight on purpose — this section
 * should read as a quiet, useful shortcut, not compete with the search bar.
 */
@Composable
fun RecentSearchChip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SuggestionChip(
        onClick = onClick,
        label = { Text(label) },
        icon = {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                modifier = Modifier.padding(start = 2.dp)
            )
        },
        modifier = modifier,
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface,
            iconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = BorderStroke(1.dp, LoreOutline)
    )
}
