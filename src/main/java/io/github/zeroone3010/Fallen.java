package io.github.zeroone3010;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.FileManager;

import java.io.InputStream;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparing;

public class Fallen {
  public static void main(String[] args) {
    final String filePath = args[0];
    final String unitName = args[1];
    final Model model = ModelFactory.createDefaultModel();
    final InputStream in = FileManager.get().open(filePath);
    if (in == null) {
      throw new IllegalArgumentException("File: " + filePath + " not found");
    }

    System.out.println("Starting to read the data...");
    final long startTime = System.currentTimeMillis();
    model.read(in, null);
    final long endTime = System.currentTimeMillis();
    System.out.println("It took " + (endTime - startTime) + " ms to load the data.");


    final Property unit = model.getProperty("http://ldf.fi/schema/warsa/casualties/", "unit_literal");
    final Property rank = model.getProperty("http://ldf.fi/schema/warsa/casualties/", "rank_literal");
    final Property givenName = model.getProperty("http://ldf.fi/schema/warsa/", "given_names");
    final Property familyName = model.getProperty("http://ldf.fi/schema/warsa/", "family_name");
    final Property occupation = model.getProperty("http://ldf.fi/schema/warsa/", "occupation_literal");
    final Property birth = model.getProperty("http://ldf.fi/schema/warsa/", "date_of_birth");
    final Property death = model.getProperty("http://ldf.fi/schema/warsa/", "date_of_death");
    final Property birthPlace = model.getProperty("http://ldf.fi/schema/warsa/", "municipality_of_birth_literal");
    final Property deathPlace = model.getProperty("http://ldf.fi/schema/warsa/", "place_of_death_literal");

    final ResIterator resources = model.listResourcesWithProperty(unit, model.createLiteral(unitName));
    final Iterable<Resource> iterable = () -> resources;
    StreamSupport.stream(iterable.spliterator(), false).sorted(comparing(a -> a.getProperty(death).getString()))
        .forEach(resource -> System.out.println(String.join("|",
            resource.getURI(),
            resource.getProperty(familyName).getString(),
            resource.getProperty(givenName).getString(),
            orEmpty(occupation, resource),
            resource.getProperty(rank).getString(),
            resource.getProperty(unit).getString(),
            resource.getProperty(birth).getString(),
            orEmpty(birthPlace, resource),
            resource.getProperty(death).getString(),
            orEmpty(deathPlace, resource)
        )));
  }

  private static String orEmpty(Property occupation, Resource resource) {
    return Optional.ofNullable(resource.getProperty(occupation)).map(Statement::getString).orElse("");
  }
}
