package com.empty.gradlefetch

import com.empty.gradlefetch.api.MavenApiClient
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns.string
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import org.jetbrains.plugins.gradle.codeInsight.AbstractGradleCompletionContributor
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.literals.GrLiteral
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.path.GrMethodCallExpression
import java.util.regex.Pattern

/**
 * Based on GradleDependenciesHelperPlugin it's little faster then standard Base completion type
 * for gradle dependencies. It's just simple test project, still require settingsUI module to
 * be implemented.
 *
 * Created by mr3mpty on 22.09.2015.
 */
public class GradleFetchCompletion : AbstractGradleCompletionContributor() {

    init {
        extend(
                CompletionType.SMART,
                psiElement(PsiElement::class.java)
                        .and(AbstractGradleCompletionContributor.GRADLE_FILE_PATTERN)
                        .withParent(GrLiteral::class.java)
                        .withSuperParent(5, psiElement(GrMethodCallExpression::class.java)
                                .withText(string().contains("dependencies"))),
                CompletionParamProvider()
        )
    }

    private class CompletionParamProvider : CompletionProvider<CompletionParameters>() {

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

                val result = api.searchRepository(text, 10).execute()

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
}