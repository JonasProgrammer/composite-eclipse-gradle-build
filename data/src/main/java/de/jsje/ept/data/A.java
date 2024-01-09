package de.jsje.ept.data;

import org.mapstruct.Mapper;

import de.jsje.ept.annot.FooAnnotation;

@FooAnnotation
public class A {
	private String foo;
	private int n;

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	@org.mapstruct.Mapper
	public interface Mapper {
		A a(A a);
	}
}
