package com.github.kalldrexx.jbverilogplugin

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class VerilogSyntaxHighlighter : SyntaxHighlighterBase() {
    
    companion object {
        val KEYWORD = TextAttributesKey.createTextAttributesKey(
            "VERILOG_KEYWORD", 
            DefaultLanguageHighlighterColors.KEYWORD
        )
        
        val COMMENT = TextAttributesKey.createTextAttributesKey(
            "VERILOG_COMMENT", 
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )
        
        val STRING = TextAttributesKey.createTextAttributesKey(
            "VERILOG_STRING", 
            DefaultLanguageHighlighterColors.STRING
        )
        
        val NUMBER = TextAttributesKey.createTextAttributesKey(
            "VERILOG_NUMBER", 
            DefaultLanguageHighlighterColors.NUMBER
        )
        
        val IDENTIFIER = TextAttributesKey.createTextAttributesKey(
            "VERILOG_IDENTIFIER", 
            DefaultLanguageHighlighterColors.IDENTIFIER
        )
        
        val OPERATOR = TextAttributesKey.createTextAttributesKey(
            "VERILOG_OPERATOR", 
            DefaultLanguageHighlighterColors.OPERATION_SIGN
        )
        
        val PUNCTUATION = TextAttributesKey.createTextAttributesKey(
            "VERILOG_PUNCTUATION", 
            DefaultLanguageHighlighterColors.PARENTHESES
        )
    }
    
    override fun getHighlightingLexer(): Lexer = VerilogLexer()
    
    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        return when (tokenType) {
            VerilogTokenTypes.MODULE, VerilogTokenTypes.ENDMODULE,
            VerilogTokenTypes.INPUT, VerilogTokenTypes.OUTPUT, VerilogTokenTypes.INOUT,
            VerilogTokenTypes.WIRE, VerilogTokenTypes.REG,
            VerilogTokenTypes.ALWAYS, VerilogTokenTypes.ASSIGN,
            VerilogTokenTypes.BEGIN, VerilogTokenTypes.END,
            VerilogTokenTypes.IF, VerilogTokenTypes.ELSE,
            VerilogTokenTypes.CASE, VerilogTokenTypes.ENDCASE,
            VerilogTokenTypes.FOR, VerilogTokenTypes.WHILE,
            VerilogTokenTypes.POSEDGE, VerilogTokenTypes.NEGEDGE,
            VerilogTokenTypes.DEFAULT_NETTYPE, VerilogTokenTypes.COMPILER_DIRECTIVE -> arrayOf(KEYWORD)
            
            VerilogTokenTypes.LINE_COMMENT, VerilogTokenTypes.BLOCK_COMMENT -> arrayOf(COMMENT)
            
            VerilogTokenTypes.STRING -> arrayOf(STRING)
            
            VerilogTokenTypes.NUMBER -> arrayOf(NUMBER)
            
            VerilogTokenTypes.IDENTIFIER -> arrayOf(IDENTIFIER)
            
            VerilogTokenTypes.ASSIGN_OP, VerilogTokenTypes.EQUALS, VerilogTokenTypes.NOT_EQUALS,
            VerilogTokenTypes.AND, VerilogTokenTypes.OR, VerilogTokenTypes.NOT,
            VerilogTokenTypes.PLUS, VerilogTokenTypes.MINUS,
            VerilogTokenTypes.MULTIPLY, VerilogTokenTypes.DIVIDE -> arrayOf(OPERATOR)
            
            VerilogTokenTypes.SEMICOLON, VerilogTokenTypes.COMMA, VerilogTokenTypes.DOT,
            VerilogTokenTypes.LPAREN, VerilogTokenTypes.RPAREN,
            VerilogTokenTypes.LBRACKET, VerilogTokenTypes.RBRACKET,
            VerilogTokenTypes.LBRACE, VerilogTokenTypes.RBRACE -> arrayOf(PUNCTUATION)
            
            else -> emptyArray()
        }
    }
}