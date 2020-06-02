package def.js;

public abstract class Clock {
	    /**
	     * Obtains a clock that returns the current instant using the
	     * system clock, converting to date and time using the Date.getTime() UTC millis.
	     *
	     * This clock, rather than {@link systemDefaultZone}, should be used when
	     * you need the current instant without the date or time.
	     *
	     * @return {Clock} a clock that uses the system clock in the UTC zone, not null
	     */
	    public native static Clock systemUTC();

	    /**
	     * Obtains a clock that returns the current instant using the best available
	     * system clock, converting to date and time using the default time-zone.
	     *
	     * This clock is based on the available system clock using the Date.getTime() UTC millis
	     *
	     * Using this method hard codes a dependency to the default time-zone into your application.
	     *
	     * The UTC clock (see {@link systemUTC}) should be used when you need the current instant
	     * without the date or time.
	     *
	     *
	     * @return {Clock} a clock that uses the system clock in the default zone, not null
	     * @see ZoneId#systemDefault()
	     */
	    native static Clock systemDefaultZone(); 

	    /**
	     *
	     * @param {ZoneId} zone
	     * @return {Clock} a clock that uses the specified time zone
	     */
	    public native static Clock system(ZoneId zone);

	    /**
	     * Obtains a clock that always returns the same instant.
	     *
	     * This clock simply returns the specified instant.
	     * As such, it is not a clock in the conventional sense.
	     * The main use case for this is in testing, where the fixed clock ensures
	     * tests are not dependent on the current clock.
	     *
	     * @param {Instant} fixedInstant  the instant to use as the clock, not null
	     * @param {ZoneId} zoneId  the zoneOffset to use as zone Offset, not null
	     * @return {Clock} a clock that always returns the same instant, not null
	     */
	    native static Clock fixed(Instant fixedInstant, ZoneId zoneId);
	    
	    /**
	     * Obtains a clock that returns instants from the specified clock with the
	     * specified duration added
	     * <p>
	     * This clock wraps another clock, returning instants that are later by the
	     * specified duration. If the duration is negative, the instants will be
	     * earlier than the current date and time.
	     * The main use case for this is to simulate running in the future or in the past.
	     * <p>
	     * A duration of zero would have no offsetting effect.
	     * Passing zero will return the underlying clock.
	     * <p>
	     * The returned implementation is immutable, thread-safe and {@code Serializable}
	     * providing that the base clock is.
	     *
	     * @param baseClock  the base clock to add the duration to, not null
	     * @param offsetDuration  the duration to add, not null
	     * @return a clock based on the base clock with the duration added, not null
	     */
	    native static Clock offset(Clock baseClock, int duration);

	    /**
	      * Gets the current millisecond instant of the clock.
	      *
	      * This returns the millisecond-based instant, measured from 1970-01-01T00:00Z (UTC).
	      * This is equivalent to the definition of {@link Date#getTime}.
	      *
	      * Most applications should avoid this method and use {@link Instant} to represent
	      * an instant on the time-line rather than a raw millisecond value.
	      * This method is provided to allow the use of the clock in high performance use cases
	      * where the creation of an object would be unacceptable.
	      *
	      * The default implementation currently calls {@link instant}.
	      *
	      * @return the current millisecond instant from this clock, measured from
	      *  the Java epoch of 1970-01-01T00:00Z (UTC), not null
	      */
	    public native long millis();

	    /**
	     * Gets the current instant of the clock.
	     *
	     * This returns an instant representing the current instant as defined by the clock.
	     *
	     * @return {Instant} the current instant from this clock, not null
	     */
	    native Instant instant();

	    native Instant zone();
	    
	    /**
	     * Returns a copy of this clock with a different time-zone.
	     * <p>
	     * A clock will typically obtain the current instant and then convert that
	     * to a date or time using a time-zone. This method returns a clock with
	     * similar properties but using a different time-zone.
	     *
	     * @param zone  the time-zone to change to, not null
	     * @return a clock based on this clock with the specified time-zone, not null
	     */
	    native Clock withZone();
	}

	/**
	 * Implementation of a clock that always returns the latest time from
	 * {@link Date#getTime}.
	 *
	 * @private
	 */
	abstract class SystemClock extends Clock {
	    /**
	     *
	     * @param {!ZoneId} zone
	     */
		public SystemClock(ZoneId zone){}

	    /**
	     *
	     * @returns {!ZoneId}
	     */
	    native Instant zone();

	    /**
	     *
	     * @returns {number}
	     */
	   public native long  millis() ;

	    /**
	     *
	     * @returns {Instant}
	     */
	    native Instant instant() ;
	    
	    native boolean equals(Object obj);
	      
	    native Clock withZone(ZoneId zone);  

	}

	/**
	 * Implementation of a clock that always returns the same instant.
	 * This is typically used for testing.
	 * @private
	 */
	abstract class FixedClock extends Clock{
		FixedClock(Instant fixedInstant, ZoneId zoneId) {
	    }

	    native Instant instant();

	    public native long  millis();

	    native Instant zone();

	    
	    native boolean equals(Object obj);

	    native Clock withZone(ZoneId zone);  
	    
	}


	/**
	 * Implementation of a clock that adds an offset to an underlying clock.
	 */
	abstract class OffsetClock extends Clock {
		OffsetClock(Clock baseClock, int offset) {}
	   
	    native Instant zone();
	        
	    native Clock withZone(ZoneId zone);
	    
	    public native long millis();
	    
	    native Instant instant();
	        
	    native boolean equals(Object obj);
	    
	}
