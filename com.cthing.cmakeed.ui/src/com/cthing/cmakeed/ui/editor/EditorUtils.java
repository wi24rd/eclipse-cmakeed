/* *****************************************************************************
 * Copyright 2007 C Thing Software
 * All Rights Reserved.
 ******************************************************************************/

package com.cthing.cmakeed.ui.editor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.rules.ICharacterScanner;

import com.cthing.cmakeed.core.commands.CMakeCommand;
import com.cthing.cmakeed.core.commands.CMakeCommands;
import com.cthing.cmakeed.core.properties.CMakeProperty;
import com.cthing.cmakeed.core.properties.CMakeProperties;
import com.cthing.cmakeed.core.variables.CMakeVariable;
import com.cthing.cmakeed.core.variables.CMakeVariables;
import com.cthing.cmakeed.ui.UIPlugin;

/**
 * Utilities for various common editor tasks.
 */
public final class EditorUtils
{
    /** Command arguments opening delimiter. */
    public static final char START_ARGS = '(';
    /** Command arguments closing delimiter. */
    public static final char END_ARGS = ')';
    
    /**
     * Not to be instantiated.
     */
    private EditorUtils()
    {
    }
    
    /**
     * Indicates whether the specified offset is within a CMake command's
     * argument list.
     * 
     * @param doc  Document representing the CMake file.
     * @param offset  Location in the document to test.
     * @return <code>true</code> if the specified offset is within a CMake
     *      command's argument list.
     */
    public static boolean inArguments(final IDocument doc, final int offset)
    {
        try {
            for (int off = offset; off >= 0; off--) {
                final char ch = doc.getChar(off);
                if (ch == START_ARGS) {
                    return true;
                }
                if (ch == END_ARGS) {
                    return false;
                }
            }
        }
        catch (final BadLocationException e) {
            UIPlugin.logError(EditorUtils.class, e);
        }
        
        return false;
    }
    
    /**
     * Obtains the command corresponding to the specified viewer and document
     * offset. The offset must be somewhere in a COMMAND_CONTENT_TYPE or
     * DEP_COMMAND_CONTENT_TYPE partition.
     * 
     * @param viewer Viewer hosting the document in which the command is
     *        contained.
     * @param offset Offset into a command or deprecated command partition.
     * @return Command corresponding to the specified command partition, or
     *         <code>null</code> if the specified offset is not within a
     *         command or deprecated command partition.
     */
    public static CMakeCommand getCommand(final ITextViewer viewer,
                                          final int offset)
    {
        return getCommand(viewer.getDocument(), offset);
    }
    
    /**
     * Obtains the command corresponding to the specified document offset. The
     * offset must be somewhere in a COMMAND_CONTENT_TYPE or
     * DEP_COMMAND_CONTENT_TYPE partition.
     * 
     * @param doc Document in which the command is contained.
     * @param offset Offset into a command or deprecated command partition.
     * @return Command name corresponding to the specified command partition, or
     *         <code>null</code> if the specified offset is not within a
     *         command or deprecated command partition.
     */
    public static CMakeCommand getCommand(final IDocument doc, final int offset)
    {
        return CMakeCommands.getCommand(getCommandName(doc, offset));
    }
    
    public static CMakeProperty getProperty(final IDocument doc, final int offset)
    {
    	return CMakeProperties.getCommand(getPropertyName(doc, offset));
    }
    
    public static String getPropertyName(final ITextViewer viewer, final int offset)
    {
    	return getPropertyName(viewer.getDocument(), offset);
    }
    
    public static String getPropertyName(final IDocument doc, final int offset)
    {
        String cmd = null;

        try {
            final String contentType = doc.getContentType(offset);
            
            if (CMakePartitionScanner.isProperty(contentType)) {
                final ITypedRegion region = doc.getPartition(offset);
                cmd = doc.get(region.getOffset(), region.getLength());
            }
        }
        catch (final BadLocationException e) {
            UIPlugin.logError(EditorUtils.class, e);
        }
        
        return cmd;
    }
    
    /**
     * 
     * @param doc
     * @param offset
     * @return
     */
    public static CMakeVariable getVariable(final IDocument doc, final int offset)
    {
    	return CMakeVariables.getCommand(getVariableName(doc, offset));
    }
    
    public static String getVariableName(final ITextViewer viewer, final int offset)
    {
    	return getVariableName(viewer.getDocument(), offset);
    }
    
    public static String getVariableName(final IDocument doc, final int offset)
    {
        String cmd = null;

        try {
            final String contentType = doc.getContentType(offset);
            
            if (CMakePartitionScanner.isVariable(contentType)) {
                final ITypedRegion region = doc.getPartition(offset);
                cmd = doc.get(region.getOffset(), region.getLength());
            }
        }
        catch (final BadLocationException e) {
            UIPlugin.logError(EditorUtils.class, e);
        }
        
        return cmd;
    }
    
    
    
    /**
     * Obtains the command name corresponding to the specified viewer and
     * document offset. The offset must be somewhere in a COMMAND_CONTENT_TYPE
     * or DEP_COMMAND_CONTENT_TYPE partition.
     * 
     * @param viewer Viewer hosting the document in which the command is
     *        contained.
     * @param offset Offset into a command or deprecated command partition.
     * @return Command name corresponding to the specified command partition, or
     *         <code>null</code> if the specified offset is not within a
     *         command or deprecated command partition.
     */
    public static String getCommandName(final ITextViewer viewer,
                                        final int offset)
    {
        return getCommandName(viewer.getDocument(), offset);
    }
    
    /**
     * Obtains the command name corresponding to the specified document offset.
     * The offset must be somewhere in a COMMAND_CONTENT_TYPE or
     * DEP_COMMAND_CONTENT_TYPE partition.
     * 
     * @param doc Document in which the command is contained.
     * @param offset Offset into a command or deprecated command partition.
     * @return Command name corresponding to the specified command partition, or
     *         <code>null</code> if the specified offset is not within a
     *         command or deprecated command partition.
     */
    public static String getCommandName(final IDocument doc, final int offset)
    {
        String cmd = null;

        try {
            final String contentType = doc.getContentType(offset);
            
            if (CMakePartitionScanner.isAnyCommand(contentType)) {
                final ITypedRegion region = doc.getPartition(offset);
                cmd = doc.get(region.getOffset(), region.getLength());
            }
        }
        catch (final BadLocationException e) {
            UIPlugin.logError(EditorUtils.class, e);
        }
        
        return cmd;
    }
    
    /**
     * Attempts to find the command containing the specified offset.
     * 
     * @param doc  Document in which the command is contained.
     * @param offset  Offset in the document which contains some aspect of the
     *      command (e.g. arguments, opening parenthesis, command string)
     * @return Command containing the specified offset or <code>null</code>
     *      if a command could not be found.
     */
    public static CMakeCommand findContainingCommand(final IDocument doc,
                                                     final int offset)
    {
        try {
            int pos = offset;
            
            while (pos >= 0) {
                final String contentType = doc.getContentType(pos);
                if (CMakePartitionScanner.isComment(contentType) ||
                        CMakePartitionScanner.isArgsClose(contentType)) {
                    break;
                }
                
                final CMakeCommand cmd = getCommand(doc, pos);
                if (cmd != null) {
                    return cmd;
                }
                
                final ITypedRegion region = doc.getPartition(pos);
                pos = region.getOffset() - 1;
            }
        }
        catch (final BadLocationException e) {
            UIPlugin.logError(EditorUtils.class, e);
        }
        
        return null;        
    }
    
    /**
     * Attempts to find the property containing the specified offset.
     * 
     * @param doc  Document in which the property is contained.
     * @param offset  Offset in the document which contains some aspect of the
     *      property (e.g. arguments, opening parenthesis, command string)
     * @return Property containing the specified offset or <code>null</code>
     *      if a property could not be found.
     */
    public static CMakeProperty findContainingProperty(final IDocument doc,
                                                     final int offset)
    {
        try {
            int pos = offset;
            
            while (pos >= 0) {
                final String contentType = doc.getContentType(pos);
                if (CMakePartitionScanner.isComment(contentType) ||
                        CMakePartitionScanner.isArgsClose(contentType)) {
                    break;
                }
                
                final CMakeProperty cmd = getProperty(doc, pos);
                if (cmd != null) {
                    return cmd;
                }
                
                final ITypedRegion region = doc.getPartition(pos);
                pos = region.getOffset() - 1;
            }
        }
        catch (final BadLocationException e) {
            UIPlugin.logError(EditorUtils.class, e);
        }
        
        return null;        
    }
    
    /**
     * Attempts to find the variable containing the specified offset.
     * 
     * @param doc  Document in which the variable is contained.
     * @param offset  Offset in the document which contains some aspect of the
     *      variable (e.g. arguments, opening parenthesis, command string)
     * @return Property containing the specified offset or <code>null</code>
     *      if a variable could not be found.
     */
    public static CMakeVariable findContainingVariable(final IDocument doc,
                                                     final int offset)
    {
        try {
            int pos = offset;
            
            while (pos >= 0) {
                final String contentType = doc.getContentType(pos);
                if (CMakePartitionScanner.isComment(contentType) ||
                        CMakePartitionScanner.isArgsClose(contentType)) {
                    break;
                }
                
                final CMakeVariable cmd = getVariable(doc, pos);
                if (cmd != null) {
                    return cmd;
                }
                
                final ITypedRegion region = doc.getPartition(pos);
                pos = region.getOffset() - 1;
            }
        }
        catch (final BadLocationException e) {
            UIPlugin.logError(EditorUtils.class, e);
        }
        
        return null;        
    }
    
    
    
    /**
     * Unread a buffer's worth of characters.
     * 
     * @param scanner  Scanner to unread
     * @param buf  Buffer to be unread
     */
    public static void unread(final ICharacterScanner scanner,
                              final StringBuilder buf)
    {
        int len = buf.length();
        while (len-- > 0) {
            scanner.unread();
        }
    }
}
