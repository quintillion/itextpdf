/*
 * $Id$
 * $Name$
 *
 * Copyright 1999, 2000, 2001 by Bruno Lowagie.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Library General Public License as published
 * by the Free Software Foundation; either version 2 of the License, or any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * You should have received a copy of the GNU Library General Public License along
 * with this library; if not, write to the Free Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * http://www.lowagie.com/iText/
 *
 * ir-arch Bruno Lowagie,
 * Adolf Baeyensstraat 121
 * 9040 Sint-Amandsberg
 * BELGIUM
 * tel. +32 (0)9 228.10.97
 * bruno@lowagie.com
 *
 */

package com.lowagie.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

/**
 * A <CODE>Section</CODE> is a part of a <CODE>Document</CODE> containing
 * other <CODE>Section</CODE>s, <CODE>Paragraph</CODE>s, <CODE>List</CODE>
 * and/or <CODE>Table</CODE>s.
 * <P>
 * Remark: you can not construct a <CODE>Section</CODE> yourself.
 * You will have to ask an instance of <CODE>Section</CODE> to the
 * <CODE>Chapter</CODE> or <CODE>Section</CODE> to which you want to
 * add the new <CODE>Section</CODE>.
 * <P>
 * Example:
 * <BLOCKQUOTE><PRE>
 * Paragraph title2 = new Paragraph("This is Chapter 2", new Font(Font.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0, 255)));
 * Chapter chapter2 = new Chapter(title2, 2);
 * Paragraph someText = new Paragraph("This is some text");
 * chapter2.add(someText);
 * Paragraph title21 = new Paragraph("This is Section 1 in Chapter 2", new Font(Font.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
 * <STRONG>Section section1 = chapter2.addSection(title21);</STRONG>
 * Paragraph someSectionText = new Paragraph("This is some silly paragraph in a chapter and/or section. It contains some text to test the functionality of Chapters and Section.");
 * <STRONG>section1.add(someSectionText);</STRONG>
 * Paragraph title211 = new Paragraph("This is SubSection 1 in Section 1 in Chapter 2", new Font(Font.HELVETICA, 14, Font.BOLD, new Color(255, 0, 0)));
 * <STRONG>Section section11 = section1.addSection(40, title211, 2);<STRONG>
 * <STRONG>section11.add(someSectionText);<STRONG>
 * </PRE></BLOCKQUOTE>
 *
 * @author  bruno@lowagie.com
 */

public class Section extends ArrayList implements TextElementArray {
    
    // membervariables
    
/** This is the title of this section. */
    protected Paragraph title;
    
/** This is the number of sectionnumbers that has to be shown before the section title. */
    protected int numberDepth;
    
/** The indentation of this section on the left side. */
    protected float indentationLeft;
    
/** The indentation of this section on the right side. */
    protected float indentationRight;
    
/** The additional indentation of the content of this section. */
    protected float sectionIndent;
    
/** This is the number of subsections. */
    protected transient int subsections = 0;
    
/** This is the complete list of sectionnumbers of this section and the parents of this section. */
    protected transient ArrayList numbers;
    
    /** false if the bookmark children are not visible */
    protected boolean bookmarkOpen = true;
    
    // constructors
    
/**
 * Constructs a new <CODE>Section</CODE>.
 *
 * @param	title			a <CODE>Paragraph</CODE>
 * @param	numberDepth		the numberDepth
 */
    
    Section(Paragraph title, int numberDepth) {
        this.numberDepth = numberDepth;
        this.title = title;
    }
    
    // private methods
    
/**
 * Sets the number of this section.
 *
 * @param	number		the number of this section
 * @param	numbers		an <CODE>ArrayList</CODE>, containing the numbers of the Parent
 * @return	<CODE>void</CODE>
 */
    
    private final void setNumbers(int number, ArrayList numbers) {
        this.numbers = new ArrayList();
        this.numbers.add(new Integer(number));
        this.numbers.addAll(numbers);
    }
    
    // implementation of the Element-methods
    
/**
 * Processes the element by adding it (or the different parts) to an
 * <CODE>ElementListener</CODE>.
 *
 * @param	listener		the <CODE>ElementListener</CODE>
 * @return	<CODE>true</CODE> if the element was processed successfully
 */
    
    public final boolean process(ElementListener listener) {
        try {
            for (Iterator i = iterator(); i.hasNext(); ) {
                listener.add((Element) i.next());
            }
            return true;
        }
        catch(DocumentException de) {
            return false;
        }
    }
    
/**
 * Gets the type of the text element.
 *
 * @return	a type
 */
    
    public int type() {
        return Element.SECTION;
    }
    
/**
 * Gets all the chunks in this element.
 *
 * @return	an <CODE>ArrayList</CODE>
 */
    
    public ArrayList getChunks() {
        ArrayList tmp = new ArrayList();
        for (Iterator i = iterator(); i.hasNext(); ) {
            tmp.addAll(((Element) i.next()).getChunks());
        }
        return tmp;
    }
    
    // overriding some of the ArrayList-methods
    
/**
 * Adds a <CODE>Paragraph</CODE>, <CODE>List</CODE> or <CODE>Table</CODE>
 * to this <CODE>Section</CODE>.
 *
 * @param	index	index at which the specified element is to be inserted
 * @param	object	an object of type <CODE>Paragraph</CODE>, <CODE>List</CODE> or <CODE>Table</CODE>=
 * @return	<CODE>void</CODE>
 * @throws	ClassCastException if the object is not a <CODE>Paragraph</CODE>, <CODE>List</CODE> or <CODE>Table</CODE>
 */
    
    public void add(int index, Object o) {
        try {
            Element element = (Element) o;
            if (element.type() == Element.PARAGRAPH ||
            element.type() == Element.LIST ||
            element.type() == Element.CHUNK ||
            element.type() == Element.PHRASE ||
            element.type() == Element.ANCHOR ||
            element.type() == Element.ANNOTATION ||
            element.type() == Element.TABLE ||
            element.type() == Element.PTABLE ||
            element.type() == Element.IMGTEMPLATE ||
            element.type() == Element.GIF ||
            element.type() == Element.JPEG ||
            element.type() == Element.PNG ||
            element.type() == Element.IMGRAW) {
                super.add(index, element);
            }
            else {
                throw new ClassCastException(String.valueOf(element.type()));
            }
        }
        catch(ClassCastException cce) {
            throw new ClassCastException("Insertion of illegal Element: " + cce.getMessage());
        }
    }
    
/**
 * Adds a <CODE>Paragraph</CODE>, <CODE>List</CODE>, <CODE>Table</CODE> or another <CODE>Section</CODE>
 * to this <CODE>Section</CODE>.
 *
 * @param	object	an object of type <CODE>Paragraph</CODE>, <CODE>List</CODE>, <CODE>Table</CODE> or another <CODE>Section</CODE>
 * @return	a boolean
 * @throws	ClassCastException if the object is not a <CODE>Paragraph</CODE>, <CODE>List</CODE>, <CODE>Table</CODE> or <CODE>Section</CODE>
 */
    
    public boolean add(Object o) {
        try {
            Element element = (Element) o;
            if (element.type() == Element.PARAGRAPH ||
            element.type() == Element.LIST ||
            element.type() == Element.CHUNK ||
            element.type() == Element.PHRASE ||
            element.type() == Element.ANCHOR ||
            element.type() == Element.ANNOTATION ||
            element.type() == Element.TABLE ||
            element.type() == Element.IMGTEMPLATE ||
            element.type() == Element.PTABLE ||
            element.type() == Element.GIF ||
            element.type() == Element.JPEG ||
            element.type() == Element.PNG ||
            element.type() == Element.IMGRAW) {
                return super.add(o);
            }
            else if (element.type() == Element.SECTION) {
                Section section = (Section) o;
                section.setNumbers(++subsections, numbers);
                return super.add(section);
            }
            else {
                throw new ClassCastException(String.valueOf(element.type()));
            }
        }
        catch(ClassCastException cce) {
            throw new ClassCastException("Insertion of illegal Element: " + cce.getMessage());
        }
    }
    
/**
 * Adds a collection of <CODE>Element</CODE>s
 * to this <CODE>Section</CODE>.
 *
 * @param	collection	a collection of <CODE>Paragraph</CODE>s, <CODE>List</CODE>s and/or <CODE>Table</CODE>s
 * @return	<CODE>true</CODE> if the action succeeded, <CODE>false</CODE> if not.
 * @throws	ClassCastException if one of the objects isn't a <CODE>Paragraph</CODE>, <CODE>List</CODE>, <CODE>Table</CODE>
 */
    
    public boolean addAll(Collection collection) {
        for (Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            this.add(iterator.next());
        }
        return true;
    }
    
    // methods that return a Section
    
/**
 * Creates a <CODE>Section</CODE>, adds it to this <CODE>Section</CODE> and returns it.
 *
 * @param	indentation	the indentation of the new section
 * @param	title		the title of the new section
 * @param	numberDepth	the numberDepth of the section
 */
    
    public final Section addSection(float indentation, Paragraph title, int numberDepth) {
        Section section = new Section(title, numberDepth);
        section.setIndentation(indentation);
        add(section);
        return section;
    }
    
/**
 * Creates a <CODE>Section</CODE>, adds it to this <CODE>Section</CODE> and returns it.
 *
 * @param	indentation	the indentation of the new section
 * @param	title		the title of the new section
 */
    
    public final Section addSection(float indentation, Paragraph title) {
        Section section = new Section(title, 1);
        section.setIndentation(indentation);
        add(section);
        return section;
    }
    
/**
 * Creates a <CODE>Section</CODE>, add it to this <CODE>Section</CODE> and returns it.
 *
 * @param	title		the title of the new section
 * @param	numberDepth	the numberDepth of the section
 */
    
    public final Section addSection(Paragraph title, int numberDepth) {
        Section section = new Section(title, numberDepth);
        add(section);
        return section;
    }
    
/**
 * Creates a <CODE>Section</CODE>, adds it to this <CODE>Section</CODE> and returns it.
 *
 * @param	title		the title of the new section
 */
    
    public final Section addSection(Paragraph title) {
        Section section = new Section(title, 1);
        add(section);
        return section;
    }
    
/**
 * Adds a <CODE>Section</CODE> to this <CODE>Section</CODE> and returns it.
 *
 * @param	indentation	the indentation of the new section
 * @param	title		the title of the new section
 * @param	numberDepth	the numberDepth of the section
 */
    
    public final Section addSection(float indentation, String title, int numberDepth) {
        Section section = new Section(new Paragraph(title), numberDepth);
        section.setIndentation(indentation);
        add(section);
        return section;
    }
    
/**
 * Adds a <CODE>Section</CODE> to this <CODE>Section</CODE> and returns it.
 *
 * @param	title		the title of the new section
 * @param	numberDepth	the numberDepth of the section
 */
    
    public final Section addSection(String title, int numberDepth) {
        Section section = new Section(new Paragraph(title), numberDepth);
        add(section);
        return section;
    }
    
/**
 * Adds a <CODE>Section</CODE> to this <CODE>Section</CODE> and returns it.
 *
 * @param	indentation	the indentation of the new section
 * @param	title		the title of the new section
 */
    
    public final Section addSection(float indentation, String title) {
        Section section = new Section(new Paragraph(title), 1);
        section.setIndentation(indentation);
        add(section);
        return section;
    }
    
/**
 * Adds a <CODE>Section</CODE> to this <CODE>Section</CODE> and returns it.
 *
 * @param	title		the title of the new section
 */
    
    public final Section addSection(String title) {
        Section section = new Section(new Paragraph(title), 1);
        add(section);
        return section;
    }
    
/**
 * Creates a given <CODE>Section</CODE> following a set of attributes and adds it to this one.
 *
 * @param	attributes	the attributes
 * @return      a new section
 */
    
    public Section addSection(Properties attributes) {
        Section section = new Section(new Paragraph(""), 1);
        
        String value;
        if ((value = attributes.getProperty(ElementTags.NUMBERDEPTH)) != null) {
            section.setNumberDepth(Integer.parseInt(value));
        }
        if ((value = attributes.getProperty(ElementTags.INDENT)) != null) {
            section.setIndentation(Float.valueOf(value + "f").floatValue());
        }
        if ((value = attributes.getProperty(ElementTags.INDENTATIONLEFT)) != null) {
            section.setIndentationLeft(Float.valueOf(value + "f").floatValue());
        }
        if ((value = attributes.getProperty(ElementTags.INDENTATIONRIGHT)) != null) {
            section.setIndentationRight(Float.valueOf(value + "f").floatValue());
        }
        
        add(section);
        return section;
    }
    
    
    // public methods
    
/**
 * Alters the attributes of this <CODE>Section</CODE>.
 *
 * @param	attributes	the attributes
 */
    
    public void set(Properties attributes) {
        String value;
        if ((value = attributes.getProperty(ElementTags.NUMBERDEPTH)) != null) {
            setNumberDepth(Integer.parseInt(value));
        }
        if ((value = attributes.getProperty(ElementTags.INDENT)) != null) {
            setIndentation(Float.valueOf(value + "f").floatValue());
        }
        if ((value = attributes.getProperty(ElementTags.INDENTATIONLEFT)) != null) {
            setIndentationLeft(Float.valueOf(value + "f").floatValue());
        }
        if ((value = attributes.getProperty(ElementTags.INDENTATIONRIGHT)) != null) {
            setIndentationRight(Float.valueOf(value + "f").floatValue());
        }
    }
    
/**
 * Sets the title of this section.
 *
 * @param	title	the new title
 */
    
    public void setTitle(Paragraph title) {
        this.title = title;
    }
    
/**
 * Sets the depth of the sectionnumbers that will be shown preceding the title.
 * <P>
 * If the numberdepth is 0, the sections will not be numbered. If the numberdepth
 * is 1, the section will be numbered with their own number. If the numberdepth is
 * higher (for instance x > 1), the numbers of x - 1 parents will be shown.
 *
 * @param	numberDepth		the new numberDepth
 */
    
    public void setNumberDepth(int numberDepth) {
        this.numberDepth = numberDepth;
    }
    
/**
 * Sets the indentation of this <CODE>Section</CODE> on the left side.
 *
 * @param	indentation		the indentation
 */
    
    public final void setIndentationLeft(float indentation) {
        indentationLeft = indentation;
    }
    
/**
 * Sets the indentation of this <CODE>Section</CODE> on the right side.
 *
 * @param	indentation		the indentation
 */
    
    public final void setIndentationRight(float indentation) {
        indentationRight = indentation;
    }
    
/**
 * Sets the indentation of the content of this <CODE>Section</CODE>.
 *
 * @param	indentation		the indentation
 */
    
    public final void setIndentation(float indentation) {
        sectionIndent = indentation;
    }
    
    // methods to retrieve information
    
/**
 * Checks if this object is a <CODE>Chapter</CODE>.
 *
 * @return	<CODE>true</CODE> if it is a <CODE>Chapter</CODE>,
 *			<CODE>false</CODE> if it is a <CODE>Section</CODE>.
 */
    
    public final boolean isChapter() {
        return type() == Element.CHAPTER;
    }
    
/**
 * Checks if this object is a <CODE>Section</CODE>.
 *
 * @return	<CODE>true</CODE> if it is a <CODE>Section</CODE>,
 *			<CODE>false</CODE> if it is a <CODE>Chapter</CODE>.
 */
    
    public final boolean isSection() {
        return type() == Element.SECTION;
    }
    
/**
 * Returns the numberdepth of this <CODE>Section</CODE>.
 *
 * @return	the numberdepth
 */
    
    public final int numberDepth() {
        return numberDepth;
    }
    
/**
 * Returns the indentation of this <CODE>Section</CODE> on the left side.
 *
 * @return	the indentation
 */
    
    public final float indentationLeft() {
        return indentationLeft;
    }
    
/**
 * Returns the indentation of this <CODE>Section</CODE> on the right side.
 *
 * @return	the indentation
 */
    
    public final float indentationRight() {
        return indentationRight;
    }
    
/**
 * Returns the indentation of the content of this <CODE>Section</CODE>.
 *
 * @return	the indentation
 */
    
    public final float indentation() {
        return sectionIndent;
    }
    
/**
 * Returns the depth of this section.
 *
 * @return	the depth
 */
    
    public final int depth() {
        return numbers.size();
    }
    
/**
 * Returns the title, preceeded by a certain number of sectionnumbers.
 *
 * @return	a <CODE>Paragraph</CODE>
 */
    
    public Paragraph title() {
        if (title == null) {
            return null;
        }
        int depth = Math.min(numbers.size(), numberDepth);
        if (depth < 1) {
            return title;
        }
        StringBuffer buf = new StringBuffer(" ");
        for (int i = 0; i < depth; i++) {
            buf.insert(0, ".");
            buf.insert(0, ((Integer) numbers.get(i)).intValue());
        }
        Paragraph result = new Paragraph(title);
        result.add(0, new Chunk(buf.toString(), title.font()));
        return result;
    }
    
/**
 * Checks if a given tag corresponds with a title tag for this object.
 *
 * @param   tag     the given tag
 * @return  true if the tag corresponds
 */
    
    public static boolean isTitle(String tag) {
        return ElementTags.TITLE.equals(tag);
    }
    
/**
 * Checks if a given tag corresponds with this object.
 *
 * @param   tag     the given tag
 * @return  true if the tag corresponds
 */
    
    public static boolean isTag(String tag) {
        return ElementTags.SECTION.equals(tag);
    }
    
    /** Getter for property bookmarkOpen.
     * @return Value of property bookmarkOpen.
     */
    public boolean isBookmarkOpen() {
        return bookmarkOpen;
    }
    
    /** Setter for property bookmarkOpen.
     * @param bookmarkOpen false if the bookmark children are not
     * visible.
     */
    public void setBookmarkOpen(boolean bookmarkOpen) {
        this.bookmarkOpen = bookmarkOpen;
    }
    
}