package org.mitre.cpe.common;

/**
 * This class represents a Logical Value.  
 * 
 * @see <a href="http://cpe.mitre.org">cpe.mitre.org</a> for more information. 
 * @author JKRAUNELIS
 * @email jkraunelis@mitre.org
 */
public class LogicalValue {

    private boolean any = false;
    private boolean na = false;

    // Object must be constructed with the String "ANY" or "NA".  
    public LogicalValue(String type) {
        if (type.equals("ANY")) {
            this.any = true;
        } else if (type.equals("NA")) {
            this.na = true;
        } else {
            throw new IllegalArgumentException("LogicalValue must be ANY or NA");
        }
    }

    public boolean isANY() {
        return this.any;
    }

    public boolean isNA() {
        return this.na;
    }

    public String toString() {
        if (this.any) {
            return "ANY";
        }
        return "NA";
    }
}