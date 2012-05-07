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
package com.tyndalehouse.step.rest.controllers;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.Validate.notEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.tyndalehouse.step.core.data.entities.Session;
import com.tyndalehouse.step.core.data.entities.User;
import com.tyndalehouse.step.core.models.BibleVersion;
import com.tyndalehouse.step.core.models.ClientSession;
import com.tyndalehouse.step.core.models.EnrichedLookupOption;
import com.tyndalehouse.step.core.models.LookupOption;
import com.tyndalehouse.step.core.models.OsisWrapper;
import com.tyndalehouse.step.core.service.BibleInformationService;
import com.tyndalehouse.step.rest.framework.Cacheable;

/**
 * The controller for retrieving information on the bible or texts from the bible
 * 
 * @author Chris
 * 
 */
@Singleton
public class BibleController {
    private static final long serialVersionUID = -5176839737814243641L;
    private static final Logger LOGGER = LoggerFactory.getLogger(BibleController.class);
    private final BibleInformationService bibleInformation;
    private final Provider<Session> serverSession;
    private final Provider<ClientSession> clientSession;

    /**
     * creates the controller giving access to bible information
     * 
     * @param bibleInformation the service allowing access to biblical material
     * @param serverSession server-side stored session
     * @param clientSession clientSession given on the request
     * 
     */
    @Inject
    public BibleController(final BibleInformationService bibleInformation,
            final Provider<Session> serverSession, final Provider<ClientSession> clientSession) {
        this.bibleInformation = bibleInformation;
        this.serverSession = serverSession;
        this.clientSession = clientSession;
        LOGGER.debug("Created Bible Controller");
    }

    /**
     * a REST method that returns version of the Bible that are available
     * 
     * @return all versions of modules that are considered to be Bibles.
     */
    @Cacheable(true)
    public List<BibleVersion> getBibleVersions() {
        return this.bibleInformation.getAvailableBibleVersions(true, null);
    }

    /**
     * a REST method that returns version of the Bible that are available
     * 
     * @param allVersions boolean to indicate whether all versions should be returned
     * @return all versions of modules that are considered to be Bibles.
     */
    @Cacheable(true)
    public List<BibleVersion> getBibleVersions(final String allVersions) {
        final User user = this.serverSession.get().getUser();
        final String language = user == null ? this.clientSession.get().getLanguage() : user.getLanguage();
        return this.bibleInformation.getAvailableBibleVersions(Boolean.valueOf(allVersions), language);
    }

    /**
     * a REST method that returns text from the Bible
     * 
     * @param version the initials identifying the version
     * @param reference the reference to lookup
     * @return the text to be displayed, formatted as HTML
     */
    @Cacheable(true)
    public OsisWrapper getBibleText(final String version, final String reference) {
        return getBibleText(version, reference, null, null);
    }

    /**
     * a REST method that returns text from the Bible
     * 
     * @param version the initials identifying the version
     * @param reference the reference to lookup
     * @param options the list of options to be passed through and affect the retrieval process
     * @return the text to be displayed, formatted as HTML
     */
    @Cacheable(true)
    public OsisWrapper getBibleText(final String version, final String reference, final String options) {
        return getBibleText(version, reference, options, null);
    }

    /**
     * a REST method that returns
     * 
     * @param version the initials identifying the version
     * @param reference the reference to lookup
     * @param options a list of options to be passed in
     * @param interlinearVersion the interlinear version if provided adds lines under the text
     * @return the text to be displayed, formatted as HTML
     */
    @Cacheable(true)
    public OsisWrapper getBibleText(final String version, final String reference, final String options,
            final String interlinearVersion) {
        notEmpty(version, "You need to provide a version");
        notEmpty(reference, "You need to provide a reference");

        String[] userOptions = null;
        if (isNotBlank(options)) {
            userOptions = options.split(",");
        }

        final List<LookupOption> lookupOptions = new ArrayList<LookupOption>();
        if (userOptions != null) {
            for (final String o : userOptions) {
                lookupOptions.add(LookupOption.valueOf(o.toUpperCase(Locale.ENGLISH)));
            }
        }

        return this.bibleInformation.getPassageText(version, reference, lookupOptions, interlinearVersion);
    }

    /**
     * a REST method that returns version of the Bible that are available
     * 
     * @param version the version initials or full version name to retrieve the versions for
     * @return all versions of modules that are considered to be Bibles.
     */
    @Cacheable(true)
    public List<LookupOption> getFeatures(final String version) {
        return this.bibleInformation.getFeaturesForVersion(version);
    }

    /**
     * retrieves the list of features currently supported by the application
     * 
     * @return a list of features currently supported by the application
     */
    @Cacheable(true)
    public List<EnrichedLookupOption> getAllFeatures() {
        return this.bibleInformation.getAllFeatures();
    }

    /**
     * 
     * @param bookStart the phrase input so far in a textbox to use for the lookup
     * @param version the version to lookup upon
     * @return a list of items
     */
    @Cacheable(true)
    public List<String> getBibleBookNames(final String bookStart, final String version) {
        return this.bibleInformation.getBibleBookNames(bookStart, version);
    }

    /**
     * ascertains the next reference to lookup
     * 
     * @param reference the current ref
     * @param version the current version
     * @return the next reference
     */
    @Cacheable(true)
    public String getNextChapter(final String reference, final String version) {
        return this.bibleInformation.getSiblingChapter(reference, version, false);
    }

    /**
     * ascertains the previous reference to lookup
     * 
     * @param reference the current ref
     * @param version the current version
     * @return the previous reference
     */
    @Cacheable(true)
    public String getPreviousChapter(final String reference, final String version) {
        return this.bibleInformation.getSiblingChapter(reference, version, true);
    }

}
