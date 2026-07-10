package com.soumya.lore.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soumya.lore.data.RETRIEVAL_STEP_COUNT
import com.soumya.lore.data.runMockRetrieval
import com.soumya.lore.ui.components.StageRow
import com.soumya.lore.ui.components.StageStatus
import com.soumya.lore.ui.components.StageUiModel
import com.soumya.lore.ui.theme.LoreTheme

private val stageLabels = listOf(
    "Request received",
    "Searching your knowledge base",
    "Ranking relevant sources",
    "Generating answer"
)

/**
 * Pure derivation of stage UI state from a single integer. No timing,
 * no coroutines — safe to unit test and safe to reuse once progress is
 * driven by real backend events instead of [runMockRetrieval].
 */
private fun stagesFor(currentStep: Int): List<StageUiModel> =
    stageLabels.mapIndexed { index, label ->
        val status = when {
            index < currentStep -> StageStatus.COMPLETE
            index == currentStep -> StageStatus.ACTIVE
            else -> StageStatus.PENDING
        }
        StageUiModel(label, status)
    }

/**
 * Shown while a query is being resolved. Progress is represented by a single
 * [currentStep] value — everything on screen is a pure function of it, and
 * the only side-effecting code is the [LaunchedEffect] that drives it.
 */
@Composable
fun LoadingScreen(
    query: String,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentStep by remember { mutableIntStateOf(0) }

    LaunchedEffect(query) {
        runMockRetrieval { step -> currentStep = step }
        onComplete()
    }

    val progress by animateFloatAsState(
        targetValue = (currentStep + 1) / RETRIEVAL_STEP_COUNT.toFloat(),
        label = "retrieval-progress"
    )

    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "LORE",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Searching your memory...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // The checklist + progress bar are one visual module, positioned
            // slightly above center within the remaining space (not glued
            // to the header, not dead-centered).
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = BiasAlignment(horizontalBias = 0f, verticalBias = -0.4f)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(32.dp))

                    stagesFor(currentStep).forEach { stage ->
                        StageRow(stage = stage)
                    }

                    LinearProgressIndicator(
                        progress = { progress },
                        color = MaterialTheme.colorScheme.primary,
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    LoreTheme {
        LoadingScreen(query = "embeddings", onComplete = {})
    }
}
