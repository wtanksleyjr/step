/*******************************************************************************
 * Copyright (c) 2012, Directors of the Tyndale STEP Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions 
 * are met:
 * 
 * Redistributions of source code must retain the above copyright 
 * notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimer in 
 * the documentation and/or other materials provided with the 
 * distribution.
 * Neither the name of the Tyndale House, Cambridge (www.TyndaleHouse.com)  
 * nor the names of its contributors may be used to endorse or promote 
 * products derived from this software without specific prior written 
 * permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING 
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
 * THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package com.tyndalehouse.step.core.utils.language;

import java.util.Locale;

/**
 * Utilities for doing Hebrew transliteration
 * 
 * @author chrisburrell
 * 
 */
public final class ContemporaryLanguageUtils {
    /** prevent instantiation */
    private ContemporaryLanguageUtils() {
        // do nothing
    }

    /**
     * Gets the locale from tag, for e.g. zh-TW constructs new Locale(zh, TW). Only deals with 2 parts at the
     * moment
     * 
     * @param tag the tag
     * @return the locale from tag
     */
    public static Locale getLocaleFromTag(final String tag) {
        final String[] tagParts = tag.split("[-_]");
        if (tagParts.length == 1) {
            return new Locale(tag);
        } else {
            return new Locale(tagParts[0], tagParts[1]);
        }
    }

    /**
     * Capitalises the first letter, hoping this works for all Locales. To be revisited otherwise
     * 
     * @param originalLanguageName the original language name
     * @return the string
     */
    public static String capitaliseFirstLetter(final String originalLanguageName) {
        final char codePointAt = originalLanguageName.charAt(0);
        final char firstLetterTitle = Character.toTitleCase(codePointAt);
        return firstLetterTitle + originalLanguageName.substring(1);
    }
}
