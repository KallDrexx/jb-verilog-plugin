package com.github.kalldrexx.jbverilogplugin

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class VerilogFileType : LanguageFileType(VerilogLanguage.INSTANCE) {
    
    override fun getName(): String = "Verilog"
    
    override fun getDescription(): String = "Verilog file"
    
    override fun getDefaultExtension(): String = "v"
    
    override fun getIcon(): Icon? = null
    
    companion object {
        @JvmStatic
        val INSTANCE = VerilogFileType()
    }
}