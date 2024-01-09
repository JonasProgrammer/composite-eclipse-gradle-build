package de.jsje.ept.data;

import org.mapstruct.factory.Mappers;

public interface Helper {
	BWrapper make(String foo, int n);

	static Helper impl() {
		return new Helper() {
			private final B.Mapper bm = Mappers.getMapper(B.Mapper.class);
			private final BWrapper.Mapper bwm = Mappers.getMapper(BWrapper.Mapper.class);

			@Override
			public BWrapper make(String foo, int n) {
				final var a = new A();
				a.setFoo(foo);
				a.setN(n);

				return bwm.wrap(bm.copy(bm.wrap(a)));
			}
		};
	}
}
