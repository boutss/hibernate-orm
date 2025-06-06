/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;

/**
 * @author Steve Ebersole
 */
public interface AttributeMapping {
	String getName();
	String getAccess();
}
