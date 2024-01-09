package de.jsje.ept.consumer;

import java.util.UUID;

import de.jsje.ept.data.A;
import de.jsje.ept.data.Helper;

public class Main {
	public static void main(String[] args) {
		final var uuid = UUID.randomUUID();
		final var ret = Helper.impl().make(uuid.toString(), uuid.clockSequence());

		System.out.println(uuid);
		System.out.println(uuid.clockSequence());

		System.out.println(ret);
		System.out.println(ret.tag);
		System.out.println(ret.hashCode());
		System.out.println(ret.wrapped.hashCode());
		System.out.println(ret.wrapped.a);
		System.out.println(ret.wrapped.a.hashCode());
		System.out.println(ret.wrapped.a.tag);
		System.out.println(ret.wrapped.a.wrapped);
		System.out.println(ret.wrapped.a.wrapped.hashCode());
		System.out.println(ret.wrapped.a.wrapped.getN());
		System.out.println(ret.wrapped.a.wrapped.getFoo());
	}
}
