package com.david.ORMappingSortedSet;

import java.util.Comparator;

public class MyComparatorClass implements Comparator<Certificate> {
    public int compare(Certificate certificate1, Certificate certificate2) {
        final int BEFORE = -1;
        final int AFTER = 1;

        /* To reverse the sorting order, multiply by -1 */
        if (certificate2 == null) {
            return BEFORE * -1;
        }

        Comparable thisCertificate = certificate1.getName();
        Comparable thatCertificate = certificate2.getName();

        if (thisCertificate == null) {
            return AFTER * 1;
        } else if (thatCertificate == null) {
            return BEFORE * -1;
        } else {
            return thisCertificate.compareTo(thatCertificate) * -1;
        }
    }
}