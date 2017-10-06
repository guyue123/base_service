/*******************************************************************************
 * Copyright (c) 2008 - 2012 Oracle Corporation. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Linda DeMichiel - Java Persistence 2.1
 *     Linda DeMichiel - Java Persistence 2.0
 *
 ******************************************************************************/
package com.base.job.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies the primary table for the annotated entity. Additional tables may
 * be specified using {@link SecondaryTable} or {@link SecondaryTables}
 * annotation.
 *
 * <p>
 * If no <code>Table</code> annotation is specified for an entity class, the
 * default values apply.
 *
 * <pre>
 *    Example:
 * 
 *    &#064;Entity
 *    &#064;Table(name="CUST", schema="RECORDS")
 *    public class Customer { ... }
 * </pre>
 *
 * @since Java Persistence 1.0
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Cache {

    /**
     * 默认操作，可选值：update, delete
     * 
     * @return
     */
    String type() default "update";

    /**
     * 缓存ID
     */
    String[] id();
}
