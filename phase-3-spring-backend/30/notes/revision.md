In Java, objects reference each other. In the database, Hibernate turns that into foreign keys/join tables.

- A relational database cannot place a Java object inside a column. It represents the same relationship using a foreign key
- Relationship mapping is the configuration that tells JPA how an object reference should be represented in the database

---

Relationship Cardinality - How many entities on one side can be associated with how many entities on the other side?
<br>
JPA supports four basic cardinalities:
1. @OneToOne : One entity is associated with one other entity
2. @OneToMany : One entity is associated with many entities
3. @ManyToOne : Many entities are associated with one entity
3. @ManyToMany : Many entities on both sides can be associated

- Unidirectional Relationship : Only one entity knows about the other. You can access it from one side only.
- Bidirectional Relationship : Both entities know about each other. You can access it from either side.

- Owning side : The entity that actually controls the foreign key in the database. It's responsible for saving/updating the relationship.
- Inverse side : it does NOT control the foreign key. It uses mappedBy to say "the other side owns this."
- mappedBy = "department" refers to the name of the Java field in Student :
