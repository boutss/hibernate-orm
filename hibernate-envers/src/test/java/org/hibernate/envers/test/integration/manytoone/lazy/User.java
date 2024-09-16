/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.envers.test.integration.manytoone.lazy;

import org.hibernate.envers.NotAudited;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 * @author Luke Chen
 */
@Entity
@Table(name = "user_tbl")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id = 0;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
	@NotAudited
	private List<Shipment> shipmentList;

	User() {
	}

	public long getId() {
		return id;
	}
}
