package com.soumya.lore.data

import kotlinx.coroutines.delay

/** Number of stages the Loading screen shows. */
const val RETRIEVAL_STEP_COUNT = 4

/**
 * Simulates the retrieval pipeline advancing one stage at a time.
 *
 * This is the ONLY thing that will need to change once a real backend
 * exists — e.g. swap the delay loop for collecting progress events from
 * the PC over the network. [onStepChange] and the screen that calls this
 * stay untouched.
 */
suspend fun runMockRetrieval(onStepChange: (step: Int) -> Unit) {
    for (step in 0 until RETRIEVAL_STEP_COUNT) {
        onStepChange(step)
        delay(1000)
    }
}
