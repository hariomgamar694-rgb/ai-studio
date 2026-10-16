package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.LocalPromptRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read app_name string resource from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("HR AI Studio", appName)
    }

    @Test
    fun `verify prompt repository provides curated prompts and external tools`() = runBlocking {
        val repo = LocalPromptRepository()
        val prompts = repo.getAllPrompts().first()
        val tools = repo.getAllExternalTools().first()

        assertTrue(prompts.isNotEmpty())
        assertTrue(tools.isNotEmpty())

        val royalThrone = prompts.find { it.id == "trend_royal_wings_throne" }
        assertNotNull(royalThrone)
        assertTrue(royalThrone!!.promptText.contains("angel wings", ignoreCase = true))

        val metaAiTool = tools.find { it.id == "meta_ai" }
        assertNotNull(metaAiTool)
        assertEquals("https://www.meta.ai", metaAiTool!!.officialUrl)
    }
}
