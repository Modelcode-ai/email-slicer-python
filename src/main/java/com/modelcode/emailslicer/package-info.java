/**
 * Email Slicer - Parse email addresses into username and domain.
 *
 * <p>This package provides a simple email parsing utility that extracts
 * the username (local part) and domain from email addresses using basic
 * string operations.
 *
 * <p>Key components:
 * <ul>
 *   <li>{@link com.modelcode.emailslicer.EmailSlicerApp} - CLI entry point
 *       and user interface</li>
 *   <li>{@link com.modelcode.emailslicer.EmailParser} - Core parsing
 *       service with validation logic</li>
 *   <li>{@link com.modelcode.emailslicer.EmailParts} - Immutable result
 *       container for parsed email components</li>
 *   <li>{@link com.modelcode.emailslicer.InvalidEmailException} - Exception
 *       thrown when validation fails</li>
 * </ul>
 *
 * @since 1.0.0
 */
package com.modelcode.emailslicer;
