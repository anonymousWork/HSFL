/*
 * Joda Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2004 Stephen Colebourne.  
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Joda project (http://www.joda.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Joda" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact licence@joda.org.
 *
 * 5. Products derived from this software may not be called "Joda",
 *    nor may "Joda" appear in their name, without prior written
 *    permission of the Joda project.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE JODA AUTHORS OR THE PROJECT
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Joda project and was originally 
 * created by Stephen Colebourne <scolebourne@joda.org>. For more
 * information on the Joda project, please see <http://www.joda.org/>.
 */
package org.joda.time.convert;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.BuddhistChronology;
import org.joda.time.chrono.GJChronology;

/**
 * CalendarConverter converts a java util Calendar to an instant or partial.
 * The Calendar is converted to milliseconds and the chronology that best
 * matches the calendar.
 *
 * @author Stephen Colebourne
 * @since 1.0
 */
final class CalendarConverter extends AbstractConverter
        implements InstantConverter, PartialConverter {

    /**
     * Singleton instance.
     */
    static final CalendarConverter INSTANCE = new CalendarConverter();

    /**
     * Restricted constructor.
     */
    protected CalendarConverter() {
        super();
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the chronology.
     * <p>
     * If a chronology is specified then it is used.
     * Otherwise, it is the GJChronology if a GregorianCalendar is used,
     * BuddhistChronology if a BuddhistCalendar is used or ISOChronology otherwise.
     * The time zone is extracted from the calendar if possible, default used if not.
     * 
     * @param object  the Calendar to convert, must not be null
     * @param chrono  the chronology to use, null means use Calendar
     * @return the chronology, never null
     * @throws NullPointerException if the object is null
     * @throws ClassCastException if the object is an invalid type
     */
    public Chronology getChronology(Object object, Chronology chrono) {
        if (chrono != null) {
            return chrono;
        }
        Calendar cal = (Calendar) object;
        DateTimeZone zone = null;
        try {
            zone = DateTimeZone.getInstance(cal.getTimeZone());
            
        } catch (IllegalArgumentException ex) {
            zone = DateTimeZone.getDefault();
        }
        return getChronology(cal, zone);
    }

    /**
     * Gets the chronology, which is the GJChronology if a GregorianCalendar is used,
     * BuddhistChronology if a BuddhistCalendar is used or ISOChronology otherwise.
     * The time zone specified is used in preference to that on the calendar.
     * 
     * @param object  the Calendar to convert, must not be null
     * @param zone  the specified zone to use, null means default zone
     * @return the chronology, never null
     * @throws NullPointerException if the object is null
     * @throws ClassCastException if the object is an invalid type
     */
    public Chronology getChronology(Object object, DateTimeZone zone) {
        if (object.getClass().getName().endsWith(".BuddhistCalendar")) {
            return BuddhistChronology.getInstance(zone);
        } else if (object instanceof GregorianCalendar) {
            GregorianCalendar gc = (GregorianCalendar) object;
            long cutover = gc.getGregorianChange().getTime();
            if (cutover == Long.MIN_VALUE) {
                return Chronology.getGregorian(zone);
            } else if (cutover == Long.MAX_VALUE) {
                return Chronology.getJulian(zone);
            } else {
                return GJChronology.getInstance(zone, cutover, 4);
            }
        } else {
            return Chronology.getISO(zone);
        }
    }

    /**
     * Gets the millis, which is the Calendar millis value.
     * 
     * @param object  the Calendar to convert, must not be null
     * @param chrono  the chronology result from getChronology, non-null
     * @return the millisecond value
     * @throws NullPointerException if the object is null
     * @throws ClassCastException if the object is an invalid type
     */
    public long getInstantMillis(Object object, Chronology chrono) {
        return ((Calendar) object).getTime().getTime();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns Calendar.class.
     * 
     * @return Calendar.class
     */
    public Class getSupportedType() {
        return Calendar.class;
    }

}
