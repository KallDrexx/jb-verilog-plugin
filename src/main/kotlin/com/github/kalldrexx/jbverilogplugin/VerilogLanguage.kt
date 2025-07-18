package com.github.kalldrexx.jbverilogplugin

import com.intellij.lang.Language

class VerilogLanguage : Language("Verilog") {
    companion object {
        @JvmStatic
        val INSTANCE = VerilogLanguage()
    }
}