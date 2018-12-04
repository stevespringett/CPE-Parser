package org.mitre.cpe.common;

import java.text.ParseException;
import java.util.Hashtable;

/**
 * The WellFormedName class represents a Well Formed Name, as defined
 * in the CPE Specification version 2.3.  
 * 
 * @see <a href="http://cpe.mitre.org">cpe.mitre.org</a> for details.
 * @author jkraunelis
 * @email jkraunelis@mitre.org
 */
public class WellFormedName {
    // Underlying wfn representation.
    // String -> String.
    Hashtable wfn = null;
    // All permissible WFN attributes as defined by specification.
    final String[] attributes = {"part", "vendor", "product", "version",
        "update", "edition", "language", "sw_edition", "target_sw",
        "target_hw", "other"};

    /**
     * Constructs a new WellFormedName object, with all components set to the 
     * default value "ANY".
     */
    public WellFormedName() throws ParseException {
        this.wfn = new Hashtable();

        for (String a : attributes) {
            // don't set part to ANY
            if (!a.equals("part")) {
                this.set(a, new LogicalValue("ANY"));
            }
        }
    }

    /**
     * Constructs a new WellFormedName object, setting each component to the 
     * given parameter value.  If a parameter is null, the component is set to 
     * the default value "ANY".
     * @param part string representing the part component
     * @param vendor string representing the vendor component
     * @param product string representing the product component
     * @param version string representing the version component
     * @param update string representing the update component
     * @param edition string representing the edition component
     * @param language string representing the language component
     * @param sw_edition string representing the sw_edition component
     * @param target_sw string representing the target_sw component
     * @param target_hw string representing the target_hw component
     * @param other string representing the other component 
     */
    public WellFormedName(Object part, Object vendor, Object product,
            Object version, Object update, Object edition, Object language, Object sw_edition, Object target_sw, Object target_hw, Object other) throws ParseException {
        this.wfn = new Hashtable();
        this.set("part", part);
        this.set("vendor", vendor);
        this.set("product", product);
        this.set("version", version);
        this.set("update", update);
        this.set("edition", edition);
        this.set("language", language);
        this.set("sw_edition", sw_edition);
        this.set("target_sw", target_sw);
        this.set("target_hw", target_hw);
        this.set("other", other);
    }

    /** 
     * @param attribute String representing the component value to get
     * @return the String value of the given component, or default value "ANY"
     * if the component does not exist
     */
    public Object get(String attribute) {
        if (this.wfn.containsKey(attribute)) {
            return this.wfn.get(attribute);
        } else {
            return new LogicalValue("ANY");
        }
    }

    /** 
     * Sets the given attribute to value, if the attribute is in the list of 
     * permissible components
     * @param attribute String representing the component to set
     * @param value Object representing the value of the given component
     */
    public final void set(String attribute, Object value) throws ParseException {
        // Iterate over permissible attributes.
        for (String a : attributes) {
            // If the argument is a valid attribute, set that attribute's value.
            if (attribute.equals(a)) {
                // check to see if we're setting a LogicalValue ANY or NA
                if (value instanceof LogicalValue) {
                    // don't allow logical values in part component
                    if (attribute.equals("part")) {
                        throw new ParseException("Error! part component cannot be a logical value", 0);
                    }
                    // put the Object in the ht and break
                    this.wfn.put(attribute, value);
                    break;
                }
                if (value == null || ((String) value).equals("")) {
                    // if value is null or blank, set attribute to default logical ANY
                    this.wfn.put(attribute, new LogicalValue("ANY"));
                    break;
                }
                String svalue = (String) value;
                // Reg exs
                // check for printable characters - no control characters
                if (!svalue.matches("\\p{Print}*")) {
                    throw new ParseException("Error! encountered non printable character in: " + svalue, 0);
                }
                // svalue has whitespace
                if (svalue.matches(".*\\s+.*")) {
                    throw new ParseException("Error! component cannot contain whitespace: " + svalue, 0);
                }
                // svalue has more than one unquoted star
                if (svalue.matches("\\*{2,}.*") || svalue.matches(".*\\*{2,}")) {
                    throw new ParseException("Error! component cannot contain more than one * in sequence: " + svalue, 0);
                }
                // svalue has unquoted punctuation embedded
                if (svalue.matches(".*(?<!\\\\)[\\!\\\"\\#\\$\\%\\&\\\'\\(\\)\\+\\,\\.\\/\\:\\;\\<\\=\\>\\@\\[\\]\\^\\`\\{\\|\\}\\~\\-].*")) {
                    throw new ParseException("Error! component cannot contain unquoted punctuation: " + svalue, 0);
                }
                // svalue has an unquoted *
                if (svalue.matches(".+(?<!\\\\)[\\*].+")) {
                    throw new ParseException("Error! component cannot contain embedded *: " + svalue, 0);
                }
                // svalue has embedded unquoted ?
                // this will catch a single unquoted ?, so make sure we deal with that
                //if (svalue.matches("\\?*[\\p{Graph}&&[^\\?]]*(?<!\\\\)[\\?][\\p{Graph}&&[^\\?]]*\\?*")) {
                if (svalue.contains("?")) {
                    if (svalue.equals("?")) {
                        // single ? is valid
                        this.wfn.put(attribute, svalue);
                        break;
                    }
                    // remove leading and trailing ?s
                    StringBuffer v = new StringBuffer(svalue);
                    while (v.indexOf("?") == 0) {
                        // remove all leading ?'s
                        v.deleteCharAt(0);
                    }
                    v = v.reverse();
                    while (v.indexOf("?") == 0) {
                        // remove all trailing ?'s (string has been reversed)
                        v.deleteCharAt(0);
                    }
                    // back to normal
                    v = v.reverse();
                    // after leading and trailing ?s are removed, check if value
                    // contains unquoted ?s
                    if (v.toString().matches(".+(?<!\\\\)[\\?].+")) {
                        throw new ParseException("Error! component cannot contain embedded ?: " + svalue, 0);
                    }
                }
                // single asterisk is not allowed
                if (svalue.equals("*")) {
                    throw new ParseException("Error! component cannot be a single *: " + svalue, 0);
                }
                // quoted hyphen not allowed by itself
                if (svalue.equals("-")) {
                    throw new ParseException("Error! component cannot be quoted hyphen: " + svalue, 0);
                }
                // part must be a, o, or h
                if (attribute.equals("part")) {
                    if (!svalue.equals("a") && !svalue.equals("o") && !svalue.equals("h")) {
                        throw new ParseException("Error! part component must be one of the following: 'a', 'o', 'h': " + svalue, 0);
                    }
                }
                // should be good to go
                this.wfn.put(attribute, svalue);
                break;
            }
        }
    }

    /**
     * 
     * @return String representation of the WellFormedName
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("wfn:[");
        for (String attr : attributes) {
            sb.append(attr);
            sb.append("=");
            Object o = wfn.get(attr);
            if (o instanceof LogicalValue) {
                sb.append(o);
                sb.append(", ");
            } else {
                sb.append("\"");
                sb.append(o);
                sb.append("\", ");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        return sb.toString();
    }
}
