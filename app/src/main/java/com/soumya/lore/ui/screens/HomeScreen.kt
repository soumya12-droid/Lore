package com.soumya.lore.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soumya.lore.data.exampleQueries
import com.soumya.lore.data.recentSearches
import com.soumya.lore.ui.components.LoreSearchField
import com.soumya.lore.ui.components.RecentSearchChip
import com.soumya.lore.ui.theme.LoreTheme

/**
 * Entry screen. Owns the search-field text as local state — nothing else
 * in the app needs it, so hoisting it further up would add indirection
 * with no benefit.
 */
@Composable
fun HomeScreen(
    onSearch: (query: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }

    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
        ) {
            // --- Header ---
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
                    text = "Search your personal memory",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // --- Main search area (fills remaining space, centered) ---
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoreSearchField(
                    value = query,
                    onValueChange = { query = it },
                    onSearch = { if (query.isNotBlank()) onSearch(query) },
                    onMicClick = { /* voice input comes later */ }
                )

                Column(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)) {
                    exampleQueries.forEach { example ->
                        Text(
                            text = example,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                    }
                }

                Button(
                    onClick = { if (query.isNotBlank()) onSearch(query) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Search")
                }
            }

            // --- Recent searches + footer ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Recent Searches",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    recentSearches.forEach { search ->
                        RecentSearchChip(
                            label = search,
                            onClick = { onSearch(search) }
                        )
                    }
                }

                Text(
                    text = "Private by Architecture",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    LoreTheme {
        HomeScreen(onSearch = {})
    }
}
