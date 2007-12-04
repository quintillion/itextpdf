/*
 * $Id$
 * $Name$
 *
 * Copyright 2007 by Howard Shank (hgshank@yahoo.com)
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the License.
 *
 * The Original Code is 'iText, a free JAVA-PDF library'.
 *
 * The Initial Developer of the Original Code is Bruno Lowagie. Portions created by
 * the Initial Developer are Copyright (C) 1999-2006 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000-2006 by Paulo Soares. All Rights Reserved.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * LGPL license (the ?GNU LIBRARY GENERAL PUBLIC LICENSE?), in which case the
 * provisions of LGPL are applicable instead of those above.  If you wish to
 * allow use of your version of this file only under the terms of the LGPL
 * License and not to allow others to use your version of this file under
 * the MPL, indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by the LGPL.
 * If you do not delete the provisions above, a recipient may use your version
 * of this file under either the MPL or the GNU LIBRARY GENERAL PUBLIC LICENSE.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the MPL as stated above or under the terms of the GNU
 * Library General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * http://www.lowagie.com/iText/
 */
package com.lowagie.text.rtf.direct;

/**
 * <code>RtfDestination</code> is the base for destinations as defined
 * in the RTF Specification.
 * 
 * @author Howard Shank (hgshank@yahoo.com)
 *
 */
abstract interface RtfDestination {
	/**
	 * Method to handle a control word and integer parameter
	 * @param ctrlWord The control word
	 * @param param The integer parameter
	 * @return true if handled, false if not handled
	 */
	public abstract boolean handleControlWord(String ctrlWord, int param);
	/**
	 * Method to handle a control word without a parameter
	 * @param ctrlWord The control word
	 * @return true if handled, false if not handled
	 */
	public abstract boolean handleControlWord(String ctrlWord);
	/**
	 * Method to handle binary data. For example, images.
	 * @param binData Byte array containing the data
	 * @return true if handle, false if not handled
	 */
	public abstract boolean handleBinaryData(byte[] binData);
	/**
	 * Method to handle a character
	 * @param ch The character input
	 * @return true if handled, false if not handled
	 */
	public abstract boolean handleCharacter(char[] ch);
	/**
	 * Method to handle text data.
	 * This method may not be used. HGS 2007/12/04
	 * @param ch
	 * @return true if handled, false if not handled
	 */
	public abstract boolean handleText(char ch);
	/**
	 * Method to handle a text string.
	 * 
	 * @param text The string data
	 * @return true if handled, false if not handled
	 */
	public abstract boolean handleText(String text);
	/**
	 * Method to handle an open group token ({)
	 * 
	 * @return true if handled, false if not handled
	 */
	public abstract boolean handleGroupStart();
	/**
	 * Method to handle a close group token (})
	 * 
	 * @return true if handled, false if not handled
	 */
	public abstract boolean handleGroupEnd();
	/**
	 * Method to handle closing of a destination
	 * 
	 * @return true if handled, false if not handled
	 */
	public abstract boolean closeDestination();	
}
