package com.empty.gradlefetch

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.ui.ComboBox
import org.jetbrains.annotations.Nls
import java.awt.GridBagConstraints
import java.awt.GridBagLayout

import javax.swing.*
import javax.swing.event.ChangeListener

/**
 * Created by mr3mpty on 21.09.2015.
 */
public class SettingsUI : Configurable {
    @Nls
    override fun getDisplayName(): String {
        return Constants.APP_NAME
    }

    override fun getHelpTopic(): String? {
        return null
    }

    override fun createComponent(): JComponent? {
        var panel = JPanel(GridBagLayout())
        var c = GridBagConstraints()
        c.anchor = GridBagConstraints.WEST
        panel.add(JLabel("Items per query: "), c)

        val slider = JSlider(SwingConstants.HORIZONTAL, 1, 50, 5)
        val amount = JLabel("5")
        slider.addChangeListener({amount.text = slider.value.toString()})
        panel.add(slider, c)
        panel.add(amount, c)

        addAdditionalSpacing(panel, c)

        return panel
    }

    private fun addAdditionalSpacing(panel: JPanel, contraints: GridBagConstraints) {
        contraints.gridx = 0
        contraints.gridy = 2
        contraints.gridwidth = 3
        contraints.weightx = 1.0
        contraints.weighty = 1.0
        panel.add(JPanel(), contraints)
    }

    override fun isModified(): Boolean {
        return false
    }

    @Throws(ConfigurationException::class)
    override fun apply() {

    }

    override fun reset() {

    }

    override fun disposeUIResources() {

    }
}
