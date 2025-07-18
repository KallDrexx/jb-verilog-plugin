package com.github.kalldrexx.jbverilogplugin

import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType

class VerilogLexer : LexerBase() {
    private var buffer: CharSequence = ""
    private var startOffset = 0
    private var endOffset = 0
    private var currentOffset = 0
    private var currentToken: IElementType? = null
    
    private val keywords = mapOf(
        "module" to VerilogTokenTypes.MODULE,
        "endmodule" to VerilogTokenTypes.ENDMODULE,
        "input" to VerilogTokenTypes.INPUT,
        "output" to VerilogTokenTypes.OUTPUT,
        "inout" to VerilogTokenTypes.INOUT,
        "wire" to VerilogTokenTypes.WIRE,
        "reg" to VerilogTokenTypes.REG,
        "always" to VerilogTokenTypes.ALWAYS,
        "assign" to VerilogTokenTypes.ASSIGN,
        "begin" to VerilogTokenTypes.BEGIN,
        "end" to VerilogTokenTypes.END,
        "if" to VerilogTokenTypes.IF,
        "else" to VerilogTokenTypes.ELSE,
        "case" to VerilogTokenTypes.CASE,
        "endcase" to VerilogTokenTypes.ENDCASE,
        "for" to VerilogTokenTypes.FOR,
        "while" to VerilogTokenTypes.WHILE,
        "posedge" to VerilogTokenTypes.POSEDGE,
        "negedge" to VerilogTokenTypes.NEGEDGE
    )
    
    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.startOffset = startOffset
        this.endOffset = endOffset
        this.currentOffset = startOffset
        advance()
    }
    
    override fun getState(): Int = 0
    
    override fun getTokenType(): IElementType? = currentToken
    
    override fun getTokenStart(): Int = startOffset
    
    override fun getTokenEnd(): Int = currentOffset
    
    override fun advance() {
        startOffset = currentOffset
        
        if (currentOffset >= endOffset) {
            currentToken = null
            return
        }
        
        val ch = buffer[currentOffset]
        
        when {
            ch.isWhitespace() -> {
                while (currentOffset < endOffset && buffer[currentOffset].isWhitespace()) {
                    currentOffset++
                }
                currentToken = VerilogTokenTypes.WHITESPACE
            }
            ch == '/' && currentOffset + 1 < endOffset && buffer[currentOffset + 1] == '/' -> {
                currentOffset += 2
                while (currentOffset < endOffset && buffer[currentOffset] != '\n') {
                    currentOffset++
                }
                currentToken = VerilogTokenTypes.LINE_COMMENT
            }
            ch == '/' && currentOffset + 1 < endOffset && buffer[currentOffset + 1] == '*' -> {
                currentOffset += 2
                while (currentOffset < endOffset - 1) {
                    if (buffer[currentOffset] == '*' && buffer[currentOffset + 1] == '/') {
                        currentOffset += 2
                        break
                    }
                    currentOffset++
                }
                if (currentOffset >= endOffset - 1) {
                    currentOffset = endOffset
                }
                currentToken = VerilogTokenTypes.BLOCK_COMMENT
            }
            ch == '"' -> {
                currentOffset++
                while (currentOffset < endOffset && buffer[currentOffset] != '"') {
                    if (buffer[currentOffset] == '\\' && currentOffset + 1 < endOffset) {
                        currentOffset += 2
                    } else {
                        currentOffset++
                    }
                }
                if (currentOffset < endOffset) currentOffset++
                currentToken = VerilogTokenTypes.STRING
            }
            ch.isDigit() || (ch == '\'' && currentOffset + 1 < endOffset) -> {
                while (currentOffset < endOffset && 
                       (buffer[currentOffset].isLetterOrDigit() || 
                        buffer[currentOffset] == '\'' || buffer[currentOffset] == '_' ||
                        buffer[currentOffset] in "bhdxzBHDXZ")) {
                    currentOffset++
                }
                currentToken = VerilogTokenTypes.NUMBER
            }
            ch == '`' -> {
                currentOffset++
                while (currentOffset < endOffset && 
                       (buffer[currentOffset].isLetterOrDigit() || 
                        buffer[currentOffset] == '_')) {
                    currentOffset++
                }
                val text = buffer.subSequence(startOffset + 1, currentOffset).toString()
                currentToken = if (text == "default_nettype") {
                    VerilogTokenTypes.DEFAULT_NETTYPE
                } else {
                    VerilogTokenTypes.COMPILER_DIRECTIVE
                }
            }
            ch.isLetter() || ch == '_' || ch == '$' -> {
                while (currentOffset < endOffset && 
                       (buffer[currentOffset].isLetterOrDigit() || 
                        buffer[currentOffset] == '_' || buffer[currentOffset] == '$')) {
                    currentOffset++
                }
                val text = buffer.subSequence(startOffset, currentOffset).toString()
                currentToken = keywords[text] ?: VerilogTokenTypes.IDENTIFIER
            }
            else -> {
                currentToken = when (ch) {
                    '=' -> {
                        currentOffset++
                        if (currentOffset < endOffset && buffer[currentOffset] == '=') {
                            currentOffset++
                            VerilogTokenTypes.EQUALS
                        } else {
                            VerilogTokenTypes.ASSIGN_OP
                        }
                    }
                    '!' -> {
                        currentOffset++
                        if (currentOffset < endOffset && buffer[currentOffset] == '=') {
                            currentOffset++
                            VerilogTokenTypes.NOT_EQUALS
                        } else {
                            VerilogTokenTypes.NOT
                        }
                    }
                    '&' -> { currentOffset++; VerilogTokenTypes.AND }
                    '|' -> { currentOffset++; VerilogTokenTypes.OR }
                    '+' -> { currentOffset++; VerilogTokenTypes.PLUS }
                    '-' -> { currentOffset++; VerilogTokenTypes.MINUS }
                    '*' -> { currentOffset++; VerilogTokenTypes.MULTIPLY }
                    '/' -> { currentOffset++; VerilogTokenTypes.DIVIDE }
                    ';' -> { currentOffset++; VerilogTokenTypes.SEMICOLON }
                    ',' -> { currentOffset++; VerilogTokenTypes.COMMA }
                    '.' -> { currentOffset++; VerilogTokenTypes.DOT }
                    '(' -> { currentOffset++; VerilogTokenTypes.LPAREN }
                    ')' -> { currentOffset++; VerilogTokenTypes.RPAREN }
                    '[' -> { currentOffset++; VerilogTokenTypes.LBRACKET }
                    ']' -> { currentOffset++; VerilogTokenTypes.RBRACKET }
                    '{' -> { currentOffset++; VerilogTokenTypes.LBRACE }
                    '}' -> { currentOffset++; VerilogTokenTypes.RBRACE }
                    else -> {
                        currentOffset++
                        VerilogTokenTypes.IDENTIFIER
                    }
                }
            }
        }
    }
    
    override fun getBufferSequence(): CharSequence = buffer
    
    override fun getBufferEnd(): Int = endOffset
}