package com.soumya.lore.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** The three states a retrieval stage can be in. Pure UI state — no timing here. */
enum class StageStatus { PENDING, ACTIVE, COMPLETE }

data class StageUiModel(val label: String, val status: StageStatus)

/**
 * One row of the Loading screen's stage list. Fully driven by [stage] —
 * no knowledge of timing, coroutines, or where the data comes from.
 */
@Composable
fun StageRow(stage: StageUiModel, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (stage.status) {
            StageStatus.COMPLETE -> Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Complete",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )

            StageStatus.ACTIVE -> CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )

            StageStatus.PENDING -> Icon(
                imageVector = Icons.Outlined.Circle,
                contentDescription = "Pending",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = stage.label,
            style = MaterialTheme.typography.bodyLarge,
            color = if (stage.status == StageStatus.PENDING) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
