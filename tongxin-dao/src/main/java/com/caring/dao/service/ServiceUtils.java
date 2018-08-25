package com.caring.dao.service;

/**
 *
 * @author james
 */
public class ServiceUtils {

    public static boolean equalsNotNull(Long id1, Long id2) {
        return id1 != null && id2 != null && id1.equals(id2);
    }

    public static boolean notEquals(Long id1, Long id2) {
        return (id1 == null && id2 != null)
                || (id1 != null && id2 == null)
                || !id1.equals(id2);
    }

    public static boolean isTrue(Boolean value) {
        return value != null && value;
    }

    public static boolean isFalse(Boolean value) {
        return value == null || !value;
    }
}
