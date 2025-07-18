package com.github.kalldrexx.jbverilogplugin

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

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
                    // Add keywords
                    VERILOG_KEYWORDS.forEach { keyword ->
                        result.addElement(
                            LookupElementBuilder.create(keyword)
                                .withTypeText("keyword")
                                .withBoldness(true)
                                .withCaseSensitivity(false)
                        )
                    }
                    
                    // Add wire and reg identifiers
                    val file = parameters.originalFile
                    val identifiers = collectWireAndRegIdentifiers(file)
                    identifiers.forEach { identifier ->
                        result.addElement(
                            LookupElementBuilder.create(identifier.name)
                                .withTypeText(identifier.type)
                                .withIcon(null)
                        )
                    }
                }
            }
        )
    }
    
    private fun collectWireAndRegIdentifiers(file: PsiElement): List<VerilogIdentifier> {
        val identifiers = mutableListOf<VerilogIdentifier>()
        val text = file.text
        
        // Parse wire declarations: wire [width] name1, name2, ...;
        val wirePattern = Regex("""wire\s*(?:\[[^\]]*\])?\s*([^;]+);""")
        wirePattern.findAll(text).forEach { match ->
            val declarations = match.groupValues[1]
            extractIdentifierNames(declarations).forEach { name ->
                identifiers.add(VerilogIdentifier(name, "wire"))
            }
        }
        
        // Parse reg declarations: reg [width] name1, name2, ...;
        val regPattern = Regex("""reg\s*(?:\[[^\]]*\])?\s*([^;]+);""")
        regPattern.findAll(text).forEach { match ->
            val declarations = match.groupValues[1]
            extractIdentifierNames(declarations).forEach { name ->
                identifiers.add(VerilogIdentifier(name, "reg"))
            }
        }
        
        // Parse input/output wire declarations: input wire [width] name1, name2, ...;
        val inputWirePattern = Regex("""input\s+wire\s*(?:\[[^\]]*\])?\s*([^;,)]+)""")
        inputWirePattern.findAll(text).forEach { match ->
            val name = match.groupValues[1].trim()
            if (name.isNotBlank()) {
                identifiers.add(VerilogIdentifier(name, "input wire"))
            }
        }
        
        val outputWirePattern = Regex("""output\s+wire\s*(?:\[[^\]]*\])?\s*([^;,)]+)""")
        outputWirePattern.findAll(text).forEach { match ->
            val name = match.groupValues[1].trim()
            if (name.isNotBlank()) {
                identifiers.add(VerilogIdentifier(name, "output wire"))
            }
        }
        
        // Parse output reg declarations: output reg [width] name1, name2, ...;
        val outputRegPattern = Regex("""output\s+reg\s*(?:\[[^\]]*\])?\s*([^;,)]+)""")
        outputRegPattern.findAll(text).forEach { match ->
            val name = match.groupValues[1].trim()
            if (name.isNotBlank()) {
                identifiers.add(VerilogIdentifier(name, "output reg"))
            }
        }
        
        return identifiers.distinctBy { it.name }
    }
    
    private fun extractIdentifierNames(declarations: String): List<String> {
        return declarations.split(',').mapNotNull { part ->
            val trimmed = part.trim()
            // Remove any assignment (= value) and extract just the identifier
            val name = trimmed.split('=')[0].trim()
            if (name.matches(Regex("[a-zA-Z_][a-zA-Z0-9_]*"))) name else null
        }
    }
}

data class VerilogIdentifier(val name: String, val type: String)