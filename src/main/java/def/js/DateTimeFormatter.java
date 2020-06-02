package def.js;

public class DateTimeFormatter {

	/**
     * function overloading for {@link DateTimeFormatter.parse}
     *
     * if called with one arg {@link DateTimeFormatter.parse1} is called
     * otherwise {@link DateTimeFormatter.parse2}
     *
     * @param {string} text
     * @param {TemporalQuery} type
     * @return {TemporalAccessor}
     */
    static native TemporalAccessor parse(CharSequence text, TemporalQuery type);
}
