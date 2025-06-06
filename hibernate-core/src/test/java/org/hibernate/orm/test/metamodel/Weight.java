/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.orm.test.metamodel;

import jakarta.persistence.Embeddable;

/**
 * @author Marco Belladelli
 */
@Embeddable
public class Weight extends Measurement {
	private float weight;
}
