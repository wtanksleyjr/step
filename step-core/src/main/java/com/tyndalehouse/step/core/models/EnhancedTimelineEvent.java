package com.tyndalehouse.step.core.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tyndalehouse.step.core.data.entities.timeline.TimelineEvent;

/**
 * Adds some key non-persisted fields to the event
 * 
 * @author chrisburrell
 * 
 */
public class EnhancedTimelineEvent implements Serializable {
    private static final long serialVersionUID = -1338450485439161040L;
    private TimelineEvent event;
    private List<OsisWrapper> verses;

    /**
     * for serialisation only
     */
    public EnhancedTimelineEvent() {
        // for serialisation
    }

    /**
     * @param event event to be stored
     */
    public EnhancedTimelineEvent(final TimelineEvent event) {
        this.event = event;
    }

    /**
     * @return the event
     */
    public TimelineEvent getEvent() {
        return this.event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(final TimelineEvent event) {
        this.event = event;
    }

    /**
     * Adds a verse to the list of referenced verses in this event
     * 
     * @param osisText the verse content
     */
    public void add(final OsisWrapper osisText) {
        if (this.verses == null) {
            this.verses = new ArrayList<OsisWrapper>();
        }
        this.verses.add(osisText);
    }

    /**
     * @return the verses
     */
    public List<OsisWrapper> getVerses() {
        return this.verses;
    }

    /**
     * @param verses the verses to set
     */
    public void setVerses(final List<OsisWrapper> verses) {
        this.verses = verses;
    }
}
