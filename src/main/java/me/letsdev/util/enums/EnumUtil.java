package me.letsdev.util.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Merge Simpson
 */
public final class EnumUtil {
    /**
     * <pre>
     * Returns {@code true} if the {@code getter} provides a unique value for each enum constant.
     * </pre>
     *
     * <p><strong>Example:</strong></p>
     *
     * <pre>
     * {@code
     * // where "Sample" is an enum class.
     * boolean result = EnumUtil.isFieldUnique(Sample.values(), Sample::getValue);
     * }
     * </pre>
     *
     * <p><strong>Example using {@code assert}:</strong></p>
     *
     * <pre>
     * {@code
     * // It only works when the {@code -ea} option is included in the JVM options.
     * assert EnumUtil.isFieldUnique(Sample.values(), Sample::getValue)
     *         : "The \"value\" field of Sample enum class should be unique.";
     * }
     * </pre>
     *
     * @param enumConstants Enumerated constants. (Constant objects of enum class)
     * @param valueGetter A getter method providing the value of the unique field.
     *                    Example: {@code () -> value}
     * @return {@code true} if the field is unique.
     * @param <E> Type of enum class.
     * @param <V> Type of target field.
     */
    public static <E extends Enum<E>, V> boolean isFieldUnique(E[] enumConstants, Function<E, V> valueGetter) {
        Set<V> valueSet = Arrays.stream(enumConstants)
                .map(valueGetter)
                .collect(Collectors.toSet());

        return valueSet.size() == enumConstants.length;
    }

    /**
     * <pre>
     * Returns {@code true} if the {@code getter} provides a unique value for each enum constant.
     * </pre>
     *
     * <p><strong>Example:</strong></p>
     *
     * <pre>
     * {@code
     * // where "Sample" is an enum class.
     * boolean result = EnumUtil.isFieldUnique(Sample.class, Sample::getValue);
     * }
     * </pre>
     *
     * <p><strong>Example using {@code assert}:</strong></p>
     *
     * <pre>
     * {@code
     * // It only works when the {@code -ea} option is included in the JVM options.
     * assert EnumUtil.isFieldUnique(Sample.class, Sample::getValue)
     *         : "The \"value\" field of Sample enum class should be unique.";
     * }
     * </pre>
     *
     * @param enumClass The Class object that represents the runtime class of enum.
     * @param valueGetter A getter method providing the value of the unique field.
     *                    Example: {@code () -> value}
     * @return {@code true} if the field is unique.
     * @param <E> Type of enum class.
     * @param <V> Type of target field.
     */
    public static <E extends Enum<E>, V> boolean isFieldUnique(Class<E> enumClass, Function<E, V> valueGetter) {
        return isFieldUnique(enumClass.getEnumConstants(), valueGetter);
    }
}