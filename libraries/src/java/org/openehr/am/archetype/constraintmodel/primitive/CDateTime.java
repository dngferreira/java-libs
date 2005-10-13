/*
 * component:   "openEHR Reference Implementation"
 * description: "Class CDateTime"
 * keywords:    "archetype"
 *
 * author:      "Rong Chen <rong@acode.se>"
 * support:     "Acode HB <support@acode.se>"
 * copyright:   "Copyright (c) 2004 Acode HB, Sweden"
 * license:     "See notice at bottom of class"
 *
 * file:        "$URL$"
 * revision:    "$LastChangedRevision$"
 * last_change: "$LastChangedDate$"
 */
package org.openehr.am.archetype.constraintmodel.primitive;

import org.openehr.am.archetype.constraintmodel.DVObjectCreationException;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.support.basic.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Constraint on instances of Date. Immutable.
 *
 * @author Rong Chen
 * @version 1.0
 */
public final class CDateTime extends CPrimitive {

    /**
     * Constructs a DateConstraint
     *
     * @param pattern
     * @param interval Interval<DvDateTime>
     * @param list     List<DvDateTime>
     * @throws IllegalArgumentException if both pattern and interval null
     *                                  or not null
     */
    public CDateTime(String pattern, Interval<DvDateTime> interval,
                     List<DvDateTime> list) {
        if (interval == null && pattern == null && list == null) {
            throw new IllegalArgumentException(
                    "pattern, interval and list can't be all null");
        }
        this.pattern = pattern;
        this.interval = interval;
        this.list = ( list == null ? null : new ArrayList<DvDateTime>(list) );
    }

    /**
     * Return the primitive type this constraint is applied on
     *
     * @return name of the type
     */
    public String getType() {
        return "DvDateTime";
    }

    /**
     * True if value is valid with respect to constraint
     *
     * @param value
     * @return true if valid
     */
    public boolean validValue(Object value) {
        // todo: validate by pattern
        final DvDateTime date = (DvDateTime) value;
        return ( interval != null && interval.has(date) )
                || ( list != null && list.contains(date) );
    }

    /**
     * Create an DvDateTime from a string value
     *
     * @param value
     * @return a DvDateTime
     */
    public DvDateTime createObject(String value)
            throws DVObjectCreationException {
        DvDateTime date = null;
        try {
            date = new DvDateTime(value);
        } catch (IllegalArgumentException e) {
            throw DVObjectCreationException.BAD_FORMAT;
        }
        if (interval != null && !interval.has(date)) {
            throw DVObjectCreationException.BAD_VALUE;
        }
        if (list != null && !list.contains(date)) {
            throw DVObjectCreationException.BAD_VALUE;
        }
        // todo: pattern?
        return date;
    }

    /**
     * Return true if the constraint has limit the possible value to
     * be only one, which means the value has been assigned by the archetype
     * author at design time
     *
     * @return true if has
     */
    public boolean hasAssignedValue() {
        return list != null && list.size() == 1;
    }

    /**
     * Return assigned value as data value instance
     *
     * @return DvDateTime or null if not assigned
     */
    public DvDateTime assignedValue() {
        if(list == null || list.size() != 1) {
            return null;
        }
        return list.get(0);
    }


    /**
     * Syntactic pattern defining constraint on dates.
     *
     * @return pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Interval of Date specifying constraint
     *
     * @return Interval<DvDateTime> or null
     */
    public Interval<DvDateTime> getInterval() {
        return interval;
    }

    /**
     * List of specified values
     *
     * @return unmodifiable List<DvDateTime> or null
     */
    public List<DvDateTime> getList() {
        return list == null ? null : Collections.unmodifiableList(list);
    }

    /* fields */
    private final String pattern;
    private final Interval<DvDateTime> interval;
    private final List<DvDateTime> list;
}

/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the 'License'); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an 'AS IS' basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is CDateTime.java
 *
 *  The Initial Developer of the Original Code is Rong Chen.
 *  Portions created by the Initial Developer are Copyright (C) 2003-2004
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 * Software distributed under the License is distributed on an 'AS IS' basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 *  ***** END LICENSE BLOCK *****
 */