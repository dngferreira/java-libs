/*
 * component:   "openEHR Reference Implementation"
 * description: "Class Attestation"
 * keywords:    "demographic"
 *
 * author:      "Goran Pestana <goran@acode.se>"
 * support:     "Acode HB <support@acode.se>"
 * copyright:   "Copyright (c) 2005 Acode HB, Sweden"
 * license:     "See notice at bottom of class"
 *
 * file:        "$URL: http://svn.openehr.org/ref_impl_java/TRUNK/libraries/src/java/org/openehr/rm/demographic/PartyIdentity.java $"
 * revision:    "$LastChangedRevision: 2 $"
 * last_change: "$LastChangedDate: 2005-10-12 22:20:08 +0100 (Wed, 12 Oct 2005) $"
 */
package org.openehr.rm.demographic;

import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.common.archetyped.Archetyped;
import org.openehr.rm.common.archetyped.FeederAudit;
import org.openehr.rm.common.archetyped.Link;
import org.openehr.rm.common.archetyped.Pathable;
import org.openehr.rm.support.identification.ObjectID;
import org.openehr.rm.datastructure.itemstructure.ItemStructure;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.FullConstructor;
import org.openehr.rm.Attribute;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;
import java.util.Set;

/**
 * An identity owned by a PARTY, such as a person name or company name,
 * and which is used by the party to identify itself. Actual structure is
 * archetyped.
 *
 * @author Goran Pestana
 * @version 1.0
 */
public class PartyIdentity extends Locatable {

    protected PartyIdentity() {
    }

    /**
     * Constructs a PartyIdentity
     *
     * @param uid              null if not specified
     * @param archetypeNodeId
     * @param name
     * @param archetypeDetails null if not specified
     * @param feederAudit      null if not specified
     * @param links            null if not specified
     * @throws IllegalArgumentException if name null or archetypeNodeId null
     *                                  or links not null and empty,
     *                                  or details null
     */
    @FullConstructor
            public PartyIdentity(@Attribute(name = "uid") ObjectID uid,
                                 @Attribute(name = "archetypeNodeId", required = true) String archetypeNodeId,
                                 @Attribute(name = "name", required = true) DvText name,
                                 @Attribute(name = "archetypeDetails") Archetyped archetypeDetails,
                                 @Attribute(name = "feederAudit") FeederAudit feederAudit,
                                 @Attribute(name = "links") Set<Link> links,
                                 @Attribute(name = "parent") Locatable parent,
                                 @Attribute(name = "details", required = true) ItemStructure details) {
        super(uid, archetypeNodeId, name, archetypeDetails, feederAudit, links, parent);
        if (details == null) {
            throw new IllegalArgumentException("details null");
        }
        this.details = details;
    }

    /**
     * Return true if the path is valid with respect to the current
     * item.
     *
     * @param path
     * @return true if valid
     */
    public boolean validPath(String path) {
        try {
            itemAtPath(path);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * The item at a path that is relative to this item.
     *
     * @param path
     * @return relative path
     * @throws IllegalArgumentException if path invalid
     */
    public Object itemAtPath(String path) {
        Object ret = super.itemAtPath(path);
        if (ret != null) {
            return ret;
        }
        //String whole = whole();
        String tmp = path;
        //if (tmp.startsWith(whole)) {
          //  tmp = tmp.substring(whole.length());
        //}
        ret = checkAttribute(tmp, "details", details);
        if (ret == null) {
            throw new IllegalArgumentException("invalid path: " + path);
        }
        return ret;
    }

    public String pathOfItem(Locatable item) {
        //todo: to be implemented
        return null;
    }

    /**
     * Purpose of identity, eg legal, stagename, nickname, tribal name,
     * trading name. Taken from value of inherited name attribute.
     *
     * @return identity purpose
     */
    public DvText purpose() {
        return this.getName();
    }

    /**
     * Indentity in the form of a single string
     *
     * @return identity purpose
     */
    public String toString() {
        // todo: fix it
        return "";
    }

    /**
     * The value of the indentity. This will often taken the form of a
     * parsable string or a small structure of strings.
     *
     * @return details
     */
    public ItemStructure getDetails() {
        return details;
    }

    protected void setDetails(ItemStructure details) {
        this.details = details;
    }

    /**
     * Equals if two party identities have same values
     *
     * @param o
     * @return equals if true
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!( o instanceof PartyIdentity )) return false;
        if (!super.equals(o)) return false;

        final PartyIdentity identity = (PartyIdentity) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(details, identity.details)
            .isEquals();
    }

    /**
     * Return a hash code of this object
     *
     * @return hash code
     */
    public int hashCode() {
        return new HashCodeBuilder(13, 29)
            .appendSuper(super.hashCode())
            .append(details)
            .toHashCode();
    }
    
	@Override
	public String pathOfItem(Pathable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> itemsAtPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean pathExists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pathUnique(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* fields */
    private ItemStructure details;
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
 *  The Original Code is PartyIdentity.java
 *
 *  The Initial Developer of the Original Code is Goran Pestana.
 *  Portions created by the Initial Developer are Copyright (C) 2003-2005
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