/**
 * Email Slicer application for parsing email addresses into username
 * and domain components.
 *
 * <p>This package contains the core classes for parsing email addresses:
 * <ul>
 *   <li>{@link com.emailslicer.EmailAddress} - An immutable value object
 *   representing a parsed email address</li>
 *   <li>{@link com.emailslicer.EmailSlicer} - The parser class that
 *   extracts username and domain from raw email strings</li>
 * </ul>
 *
 * <p>This is a Java 17 migration of the original Python email slicer
 * tool, providing basic email parsing functionality with improved
 * structure and testability.
 */
package com.emailslicer;
