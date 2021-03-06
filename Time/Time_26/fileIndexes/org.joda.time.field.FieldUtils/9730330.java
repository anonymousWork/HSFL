/*
 *  Copyright 2001-2005 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.time.field;

import org.joda.time.DateTimeField;

/**
 * General utilities that don't fit elsewhere.
 * <p>
 * FieldUtils is thread-safe and immutable.
 *
 * @author Stephen Colebourne
 * @since 1.0
 */
public class FieldUtils {

    /**
     * Restricted constructor.
     */
    private FieldUtils() {
        super();
    }
    
    //------------------------------------------------------------------------
    /**
     * Add two values throwing an exception if overflow occurs.
     * 
     * @param val1  the first value
     * @param val2  the second value
     * @return the new total
     */
    public static int safeAdd(int val1, int val2) {
        long total = ((long) val1) + ((long) val2);
        if (total < Integer.MIN_VALUE || total > Integer.MAX_VALUE) {
            throw new ArithmeticException("The calculation caused an overflow: " + val1 +" + " + val2);
        }
        return (int) total;
    }
    
    /**
     * Add two values throwing an exception if overflow occurs.
     * 
     * @param val1  the first value
     * @param val2  the second value
     * @return the new total
     */
    public static long safeAdd(long val1, long val2) {
        long total = val1 + val2;
        if (val1 > 0 && val2 > 0 && total < 0) {
            throw new ArithmeticException("The calculation caused an overflow: " + val1 +" + " + val2);
        }
        if (val1 < 0 && val2 < 0 && total > 0) {
            throw new ArithmeticException("The calculation caused an overflow: " + val1 +" + " + val2);
        }
        return total;
    }
    
    /**
     * Subtracts two values throwing an exception if overflow occurs.
     * 
     * @param val1  the first value, to be taken away from
     * @param val2  the second value, the amount to take away
     * @return the new total
     */
    public static long safeSubtract(long val1, long val2) {
        if (val2 == Long.MIN_VALUE) {
            if (val1 <= 0L) {
                return (val1 - val2);
            }
            throw new ArithmeticException("The calculation caused an overflow: " + val1 +" - " + val2);
        }
        return safeAdd(val1, -val2);
    }
    
    /**
     * Multiply two values throwing an exception if overflow occurs.
     * 
     * @param val1  the first value
     * @param val2  the second value
     * @return the new total
     */
    public static long safeMultiply(long val1, long val2) {
        if (val1 == 0  || val2 == 0) {
            return 0L;
        }
        long total = val1 * val2;
        if (total / val2 != val1) {
            throw new ArithmeticException("The calculation caused an overflow: " + val1 +" * " + val2);
        }
        return total;
    }
    
    /**
     * Casts to an int throwing an exception if overflow occurs.
     * 
     * @param value  the value
     * @return the value as an int
     */
    public static int safeToInt(long value) {
        if (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE) {
            return (int) value;
        }
        throw new ArithmeticException("Value cannot fit in an int: " + value);
    }
    
    /**
     * Multiply two values to return an int throwing an exception if overflow occurs.
     * 
     * @param val1  the first value
     * @param val2  the second value
     * @return the new total
     */
    public static int safeMultiplyToInt(long val1, long val2) {
        long val = FieldUtils.safeMultiply(val1, val2);
        return FieldUtils.safeToInt(val);
    }
    
    /**
     * Verify that input values are within specified bounds.
     * 
     * @param value  the value to check
     * @param lowerBound  the lower bound allowed for value
     * @param upperBound  the upper bound allowed for value
     * @throws IllegalArgumentException if value is not in the specified bounds
     */
    public static void verifyValueBounds(DateTimeField field, 
                                         int value, int lowerBound, int upperBound) {
        if ((value < lowerBound) || (value > upperBound)) {
            throw new IllegalArgumentException(
                "Value "
                    + value
                    + " for "
                    + field.getName()
                    + " must be in the range ["
                    + lowerBound
                    + ','
                    + upperBound
                    + ']');
        }
    }

    /**
     * Verify that input values are within specified bounds.
     * 
     * @param value  the value to check
     * @param lowerBound  the lower bound allowed for value
     * @param upperBound  the upper bound allowed for value
     * @throws IllegalArgumentException if value is not in the specified bounds
     */
    public static void verifyValueBounds(String fieldName,
                                         int value, int lowerBound, int upperBound) {
        if ((value < lowerBound) || (value > upperBound)) {
            throw new IllegalArgumentException(
                "Value "
                    + value
                    + " for "
                    + fieldName
                    + " must be in the range ["
                    + lowerBound
                    + ','
                    + upperBound
                    + ']');
        }
    }

    /**
     * Utility method used by addWrapField implementations to ensure the new
     * value lies within the field's legal value range.
     *
     * @param currentValue the current value of the data, which may lie outside
     * the wrapped value range
     * @param wrapValue  the value to add to current value before
     *  wrapping.  This may be negative.
     * @param minValue the wrap range minimum value.
     * @param maxValue the wrap range maximum value.  This must be
     *  greater than minValue (checked by the method).
     * @return the wrapped value
     * @throws IllegalArgumentException if minValue is greater
     *  than or equal to maxValue
     */
    public static int getWrappedValue(int currentValue, int wrapValue,
                                      int minValue, int maxValue) {
        return getWrappedValue(currentValue + wrapValue, minValue, maxValue);
    }

    /**
     * Utility method that ensures the given value lies within the field's
     * legal value range.
     * 
     * @param value  the value to fit into the wrapped value range
     * @param minValue the wrap range minimum value.
     * @param maxValue the wrap range maximum value.  This must be
     *  greater than minValue (checked by the method).
     * @return the wrapped value
     * @throws IllegalArgumentException if minValue is greater
     *  than or equal to maxValue
     */
    public static int getWrappedValue(int value, int minValue, int maxValue) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("MIN > MAX");
        }

        int wrapRange = maxValue - minValue + 1;
        value -= minValue;

        if (value >= 0) {
            return (value % wrapRange) + minValue;
        }

        int remByRange = (-value) % wrapRange;

        if (remByRange == 0) {
            return 0 + minValue;
        }
        return (wrapRange - remByRange) + minValue;
    }

}
