package com.github.kalldrexx.jbverilogplugin

import com.intellij.psi.tree.IElementType

class VerilogTokenType(debugName: String) : IElementType(debugName, VerilogLanguage.INSTANCE) {
    override fun toString(): String = "VerilogTokenType." + super.toString()
}

object VerilogTokenTypes {
    @JvmField val IDENTIFIER = VerilogTokenType("IDENTIFIER")
    @JvmField val NUMBER = VerilogTokenType("NUMBER")
    @JvmField val STRING = VerilogTokenType("STRING")
    @JvmField val COMMENT = VerilogTokenType("COMMENT")
    @JvmField val LINE_COMMENT = VerilogTokenType("LINE_COMMENT")
    @JvmField val BLOCK_COMMENT = VerilogTokenType("BLOCK_COMMENT")
    @JvmField val WHITESPACE = VerilogTokenType("WHITESPACE")
    
    // Keywords
    @JvmField val MODULE = VerilogTokenType("MODULE")
    @JvmField val ENDMODULE = VerilogTokenType("ENDMODULE")
    @JvmField val INPUT = VerilogTokenType("INPUT")
    @JvmField val OUTPUT = VerilogTokenType("OUTPUT")
    @JvmField val INOUT = VerilogTokenType("INOUT")
    @JvmField val WIRE = VerilogTokenType("WIRE")
    @JvmField val REG = VerilogTokenType("REG")
    @JvmField val ALWAYS = VerilogTokenType("ALWAYS")
    @JvmField val ASSIGN = VerilogTokenType("ASSIGN")
    @JvmField val BEGIN = VerilogTokenType("BEGIN")
    @JvmField val END = VerilogTokenType("END")
    @JvmField val IF = VerilogTokenType("IF")
    @JvmField val ELSE = VerilogTokenType("ELSE")
    @JvmField val CASE = VerilogTokenType("CASE")
    @JvmField val ENDCASE = VerilogTokenType("ENDCASE")
    @JvmField val FOR = VerilogTokenType("FOR")
    @JvmField val WHILE = VerilogTokenType("WHILE")
    @JvmField val POSEDGE = VerilogTokenType("POSEDGE")
    @JvmField val NEGEDGE = VerilogTokenType("NEGEDGE")
    @JvmField val DEFAULT_NETTYPE = VerilogTokenType("DEFAULT_NETTYPE")
    @JvmField val COMPILER_DIRECTIVE = VerilogTokenType("COMPILER_DIRECTIVE")
    
    // Operators
    @JvmField val ASSIGN_OP = VerilogTokenType("ASSIGN_OP")
    @JvmField val EQUALS = VerilogTokenType("EQUALS")
    @JvmField val NOT_EQUALS = VerilogTokenType("NOT_EQUALS")
    @JvmField val AND = VerilogTokenType("AND")
    @JvmField val OR = VerilogTokenType("OR")
    @JvmField val NOT = VerilogTokenType("NOT")
    @JvmField val PLUS = VerilogTokenType("PLUS")
    @JvmField val MINUS = VerilogTokenType("MINUS")
    @JvmField val MULTIPLY = VerilogTokenType("MULTIPLY")
    @JvmField val DIVIDE = VerilogTokenType("DIVIDE")
    
    // Punctuation
    @JvmField val SEMICOLON = VerilogTokenType("SEMICOLON")
    @JvmField val COMMA = VerilogTokenType("COMMA")
    @JvmField val DOT = VerilogTokenType("DOT")
    @JvmField val LPAREN = VerilogTokenType("LPAREN")
    @JvmField val RPAREN = VerilogTokenType("RPAREN")
    @JvmField val LBRACKET = VerilogTokenType("LBRACKET")
    @JvmField val RBRACKET = VerilogTokenType("RBRACKET")
    @JvmField val LBRACE = VerilogTokenType("LBRACE")
    @JvmField val RBRACE = VerilogTokenType("RBRACE")
}