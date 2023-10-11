//package org.h2.value;
//
//import org.h2.util.StringUtils;
//
//import java.util.Objects;
//
///**
// * Implementation of the Contact data type.
// */
//public class ValueContact extends ValueStringBase {
//
//    public Contact contact;
//
//    private ValueContact(String contactValue){
//        this.contact = new Contact(contactValue);
//    }
//
//    public ValueContact(Contact contact) throws Exception {
//        this.contact = contact;
//    }
//
//    public static ValueContact get(Contact c)  throws Exception {
//        ValueContact valContactObject = new ValueContact(c);
//        return valContactObject;
//
//
//    /**
//     * Appends the SQL statement of this object to the specified builder.
//     *
//     * @param builder  string builder
//     * @param sqlFlags formatting flags
//     * @return the specified string builder
//     */
//    public String getSQL(int sqlFlags) {
//        return StringUtils.quoteStringSQL(builder, value);
//    }
//
//    @Override
//    public TypeInfo getType() { return TypeInfo.TYPE_CONTACT;}
//
//    /**
//     * Get the value type.
//     *
//     * @return the value type
//     */
//    @Override
//    public int getValueType() {
//        return CONTACT;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        ValueContact that = (ValueContact) o;
//        return contact.equals(that.contact);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), contact);
//    }
//}
/*
 * Copyright 2004-2022 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (https://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.value;

import org.h2.engine.SysProperties;
import org.h2.util.StringUtils;

import java.util.Objects;

/**
 * Implementation of the CHARACTER data type.
 */
public final class ValueContact extends ValueStringBase {

    public Contact contact;

    private ValueContact(String contactValue){
        this.value = contactValue;
        this.contact = new Contact(contactValue);
    }

    public ValueContact(Contact contact) throws Exception {
        this.contact = contact;
    }

    public static ValueContact get(Contact c)  throws Exception {
        ValueContact valContactObject = new ValueContact(c);
        return valContactObject;
    }

    @Override
    public int getValueType() {
        return CONTACT;
    }

    @Override
    public StringBuilder getSQL(StringBuilder builder, int sqlFlags) {
        if ((sqlFlags & NO_CASTS) == 0) {
            int length = value.length();
            return StringUtils.quoteStringSQL(builder.append("CAST("), value).append(" AS CHAR(")
                    .append(length > 0 ? length : 1).append("))");
        }
        return StringUtils.quoteStringSQL(builder, value);
    }

    /**
     * Get or create a CONTACT value for the given string.
     *
     * @param s the string
     * @return the value
     */
    public static ValueContact get(String s) {
        ValueContact obj = new ValueContact(StringUtils.cache(s));
        if (s.length() > SysProperties.OBJECT_CACHE_MAX_PER_ELEMENT_SIZE) {
            return obj;
        }
        return (ValueContact) Value.cache(obj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ValueContact that = (ValueContact) o;
        return contact.equals(that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contact);
    }
}
