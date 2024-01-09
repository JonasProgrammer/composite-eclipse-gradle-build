package de.jsje.ept.annot;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.google.auto.service.AutoService;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@SupportedAnnotationTypes("de.jsje.ept.annot.FooAnnotation")
public class FooProcessor extends AbstractProcessor {

	public static final String MAPPING_CLASS = Mapping.class.getName();
	public static final String MAPPER_CLASS = Mapper.class.getName();

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		boolean foo = false, unclaimed = false;
		for (final var annotation : annotations) {
			switch (annotation.getSimpleName().toString()) {
			case "FooAnnotation":
				foo = true;
				writeOutput(annotation, roundEnv);
				break;
			default:
				unclaimed = true;
			}
		}
		return foo && !unclaimed;
	}

	private void writeOutput(TypeElement annotation, RoundEnvironment roundEnv) {
		final var types = processingEnv.getTypeUtils();
		final var elements = processingEnv.getElementUtils();
		final var messager = processingEnv.getMessager();
		final var filer = processingEnv.getFiler();

		roundEnv.getElementsAnnotatedWith(annotation).forEach(elem -> {
			if (!(elem instanceof TypeElement te)) return;
			final var fqn = te.getQualifiedName().toString();
			final var pkg = fqn.replaceFirst("\\.[^.]*$", "");
			final var clazz = fqn.replaceFirst("^.*\\.", "");
			final var wrapperClazz = clazz + "Wrapper";

			try {
				final var f = filer.createSourceFile(fqn + "Wrapper", elem);
				try (final var writer = f.openWriter()) {
					writer.write("package " + pkg + ";\n");
					writer.write("public class " + wrapperClazz + " {\n");
					writer.write("public " + clazz + " wrapped;\n");
					writer.write("public int tag;\n");
					writer.write("@" + MAPPER_CLASS + "\n");
					writer.write("public interface Mapper {\n");
					writer.write("@" + MAPPING_CLASS + "(target = \"wrapped\", source = \"obj\")\n");
					writer.write("@" + MAPPING_CLASS + "(target = \"tag\", source = \"id\")\n");
					writer.write(wrapperClazz + " wrap(" + clazz + " obj, int id);\n");
					writer.write("default " + wrapperClazz + " wrap(" + clazz + " obj) { return wrap(obj, obj.hashCode()); }\n");
					writer.write("}\n");
					writer.write("}\n");
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}
}
