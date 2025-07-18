package com.github.kalldrexx.jbverilogplugin

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class VerilogParserDefinition : ParserDefinition {
    
    companion object {
        val FILE = IFileElementType(VerilogLanguage.INSTANCE)
    }
    
    override fun createLexer(project: Project?): Lexer = VerilogLexer()
    
    override fun createParser(project: Project?): PsiParser {
        // Return a dummy parser since we're only doing basic completion
        return object : PsiParser {
            override fun parse(root: com.intellij.psi.tree.IElementType, builder: com.intellij.lang.PsiBuilder): ASTNode {
                val marker = builder.mark()
                while (!builder.eof()) {
                    builder.advanceLexer()
                }
                marker.done(root)
                return builder.treeBuilt
            }
        }
    }
    
    override fun getFileNodeType(): IFileElementType = FILE
    
    override fun getCommentTokens(): TokenSet = TokenSet.create(
        VerilogTokenTypes.LINE_COMMENT,
        VerilogTokenTypes.BLOCK_COMMENT
    )
    
    override fun getStringLiteralElements(): TokenSet = TokenSet.create(VerilogTokenTypes.STRING)
    
    override fun createElement(node: ASTNode): PsiElement {
        return VerilogPsiElement(node)
    }
    
    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return VerilogFile(viewProvider)
    }
}

class VerilogPsiElement(node: ASTNode) : com.intellij.extapi.psi.ASTWrapperPsiElement(node)

class VerilogFile(viewProvider: FileViewProvider) : com.intellij.extapi.psi.PsiFileBase(viewProvider, VerilogLanguage.INSTANCE) {
    override fun getFileType(): com.intellij.openapi.fileTypes.FileType = VerilogFileType.INSTANCE
}