/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.orm.test.mapping.embeddable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.testing.orm.junit.EntityManagerFactoryScope;
import org.hibernate.testing.orm.junit.JiraKey;
import org.hibernate.testing.orm.junit.Jpa;
import org.hibernate.testing.orm.junit.Setting;
import org.junit.jupiter.api.Test;

/**
 * Test passes if Author#name is renamed to Author#aname because it will be read before house (alphabetical order).
 * The issue occurs if the nested embedded is read first, due to the table calculation (ComponentPropertyHolder#addProperty) used by the embedded, which retrieves the table from the first property.
 *
 * @author Vincent Bouthinon
 */
@Jpa(
		annotatedClasses = {
				NestedEmbeddedObjectWithASecondaryTableTest.Author.class,
				NestedEmbeddedObjectWithASecondaryTableTest.Book.class,
				NestedEmbeddedObjectWithASecondaryTableTest.House.class
		},
		integrationSettings = @Setting(name = JdbcSettings.SHOW_SQL, value = "true")
)
@JiraKey("HHH-19272")
class NestedEmbeddedObjectWithASecondaryTableTest {

	@Test
	void testExceptionEmbeddedHasPropertiesMappedToTwoDifferentTables(EntityManagerFactoryScope scope) {

		scope.inTransaction(
				entityManager -> {
					// Nothing
				}
		);
	}

	@Entity(name = "book")
	@Table(name = "TBOOK")
	@SecondaryTable(name = "TSECONDARYTABLE")
	public static class Book {

		@Id
		@GeneratedValue
		private Long id;

		@AttributeOverride(name = "name", column = @Column(name = "authorName", table = "TSECONDARYTABLE"))
		@Embedded
		private Author author;

	}

	@Embeddable
	public static class Author {

		@AttributeOverride(name = "name", column = @Column(name = "houseName", table = "TSECONDARYTABLE"))
		@Embedded
		private House house;

		private String name;
	}

	@Embeddable
	public static class House {
		private String name;
	}
}
