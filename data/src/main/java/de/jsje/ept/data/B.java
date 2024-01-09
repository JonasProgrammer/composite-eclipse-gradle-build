package de.jsje.ept.data;

import org.mapstruct.Mapping;

import de.jsje.ept.annot.FooAnnotation;

@FooAnnotation
public class B {
	public AWrapper a;

	@org.mapstruct.Mapper(uses = AWrapper.Mapper.class)
	public interface Mapper {
		B copy(B b);

		B wrap(A a);
	}
}
