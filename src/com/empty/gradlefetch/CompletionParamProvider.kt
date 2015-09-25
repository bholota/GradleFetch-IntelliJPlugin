package com.empty.gradlefetch

import com.empty.gradlefetch.api.MavenApiClient
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import java.util.regex.Pattern

/**
 * Created by mr3mpty on 25.09.2015.
 */
public class CompletionParamProvider : CompletionProvider<CompletionParameters>() {

    val api = MavenApiClient().api

    override fun addCompletions(
            parameters: CompletionParameters,
            context: ProcessingContext?,
            resultSet: CompletionResultSet) {

        val position = parameters.originalPosition
        position?.let {
            val text = trimQuote(it.text)

            if (isShortText(text)) {
                return
            }

            val list = text.split(splitPattern)

            if (list.size() !in 2..3) {
                resultSet.restartCompletionOnPrefixChange(text)
            }

            val result = api.searchRepository(text, Constants.DEFAULT_REQUEST_COUNT).execute()

            if (result.isSuccess) {
                resultSet.addAllElements(
                        result.body().response!!.docs!!.map {
                            LookupElementBuilder.create(it.id + ":" + it.latestVersion)
                        }.toList()
                )
                resultSet.stopHere()
            }
        }
    }

    companion object {
        val splitPattern = Pattern.compile(":")

        fun isShortText(text: String?) = (text?.length() ?: 0) < 2

        fun trimQuote(text: String) = text.trim('"', '\'').trimEnd('"', '\'')
    }
}