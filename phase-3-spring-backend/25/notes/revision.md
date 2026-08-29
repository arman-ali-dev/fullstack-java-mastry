An annotation is only metadata attached to a program element.

- A custom annotation is declared using @interface

Questions:
<br>
- Where the annotation can be used
- How long the annotation remains available

These rules are defined through meta-annotations.

@Target defines where an annotation is legally allowed to appear.
- ElementType.METHOD : The annotation can be applied to methods
- ElementType.TYPE : The annotation can be applied to classes, interfaces, enums, records and annotation interfaces.

@Retention defines how long annotation information is preserved.
- RetentionPolicy.SOURCE : The annotation exists only in the source code and is discarded during compilation.
- RetentionPolicy.CLASS : The annotation is stored in the compiled .class file but is not normally available through runtime reflection.
- RetentionPolicy.RUNTIME : The annotation remains available while the application is running.

@Documented tells Java documentation tools to include the annotation in generated Javadoc.

- Marker annotation : A marker annotation has no properties
- Annotation with properties : An annotation can also carry values:
