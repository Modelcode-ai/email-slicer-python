package com.emailslicer;

/**
 * Core email parsing logic.
 * <p>
 * Accepts a raw email string, trims whitespace, validates the presence and
 * position of the {@code @} symbol, and splits the input into username and
 * domain components on the first {@code @}.
 */
public class EmailSlicer {

    /**
     * Parses a raw email string into its username and domain components.
     *
     * @param rawInputEmail the raw email string (may include leading/trailing whitespace)
     * @return an {@link EmailParts} record containing the parsed username and domain
     * @throws IllegalArgumentException if the input is null, empty, or does not
     *                                  contain a valid {@code @} placement
     */
    public EmailParts parse(String rawInputEmail) {
        // TODO: implement in next task
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
