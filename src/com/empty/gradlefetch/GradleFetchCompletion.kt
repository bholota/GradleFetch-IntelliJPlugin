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
                                .withText(string().contains(Constants.DEPENDENCIES_CLOSURE))),
                CompletionParamProvider()
        )
    }
}