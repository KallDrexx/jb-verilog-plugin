package com.github.kalldrexx.jbverilogplugin

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext

class VerilogCompletionContributor : CompletionContributor() {
    
    companion object {
        private val VERILOG_KEYWORDS = listOf(
            "module", "endmodule",
            "input", "output", "inout",
            "wire", "reg",
            "always", "assign",
            "begin", "end",
            "if", "else",
            "case", "endcase",
            "for", "while",
            "posedge", "negedge",
            "default", "nettype",
            "initial", "function", "endfunction",
            "task", "endtask",
            "parameter", "localparam",
            "integer", "time", "real",
            "and", "or", "not", "nand", "nor", "xor", "xnor",
            "buf", "bufif0", "bufif1",
            "notif0", "notif1",
            "pullup", "pulldown",
            "supply0", "supply1",
            "tri", "triand", "trior", "tri0", "tri1",
            "wand", "wor",
            "casex", "casez",
            "default",
            "disable",
            "forever",
            "repeat",
            "wait",
            "fork", "join"
        )
    }
    
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement().inFile(PlatformPatterns.psiFile().withLanguage(VerilogLanguage.INSTANCE)),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    VERILOG_KEYWORDS.forEach { keyword ->
                        result.addElement(
                            LookupElementBuilder.create(keyword)
                                .withTypeText("keyword")
                                .withBoldness(true)
                                .withCaseSensitivity(false)
                        )
                    }
                }
            }
        )
    }
}