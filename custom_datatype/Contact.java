package org.h2.value;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.h2.api.ErrorCode;
import org.h2.message.DbException;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;
    private String contact;

    public static boolean verifyAndInitializeContact(String c) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = null;

        String regex = "^\\([4-6]{1}[0-9]{2}\\)[0-9]{3}-[0-9]{4}$";
        Pattern contactPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher contactMatcher = contactPattern.matcher(c);
        System.out.println("Match: "+ contactMatcher.matches());
        if (contactMatcher.matches()){
            return  true;
        } else {
            return false;
        }
    }

    Contact(Contact c) {
        verifyAndInitializeContact(c);
    }

    public Contact(String contactValue) {
        this.contact = contactValue;
    }

    private void verifyAndInitializeContact(Contact c) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumber = phoneNumberUtil.parse(c.toString(), "US");
            this.contact = phoneNumber.toString();
        } catch (NumberParseException e) {
            e.printStackTrace();
            throw DbException.get(ErrorCode.INVALID_CONTACT_ERROR_CODE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact1 = (Contact) o;
        return contact.equals(contact1.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contact);
    }

    @Override
    public String toString() {
        return this.contact;
    }
}
