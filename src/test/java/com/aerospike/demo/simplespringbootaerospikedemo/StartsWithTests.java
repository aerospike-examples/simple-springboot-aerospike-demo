package com.aerospike.demo.simplespringbootaerospikedemo;

import com.aerospike.demo.simplespringbootaerospikedemo.sample.Address;
import com.aerospike.demo.simplespringbootaerospikedemo.sample.Person;
import com.aerospike.demo.simplespringbootaerospikedemo.sample.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the "Starts with" repository query. Keywords: StartingWith, IsStartingWith, StartsWith.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StartsWithTests extends BaseBlockingIntegrationTests {

    protected static final Person dave = Person.builder()
            .id("1")
            .firstName("Dave")
            .lastName("Matthews")
            .age(42)
//            .strings(List.of("str0", "str1", "str2"))
            .address(new Address("Foo Street 1", 1, "C0123", "Bar"))
            .build();
    protected static final Person donny = Person.builder()
            .id("2")
            .firstName("Donny")
            .lastName("Macintire")
            .age(39)
//            .strings(List.of("str1", "str2", "str3"))
//            .stringMap(AsCollections.of("key1", "val1"))
            .build();
    protected static final Person oliver = Person.builder()
            .id("3")
            .firstName("Oliver August")
            .lastName("Matthews")
            .age(14)
//            .ints(List.of(425, 550, 990))
            .build();
    protected static final Person alicia = Person.builder()
            .id("4")
            .firstName("Alicia")
            .lastName("Keys")
            .age(30)
//            .ints(List.of(550, 600, 990))
            .build();
    protected static final Person carter = Person.builder()
            .id("5")
            .firstName("Carter")
            .lastName("Beauford")
            .age(49)
//            .intMap(AsCollections.of("key1", 0, "key2", 1))
            .address(new Address("Foo Street 2", 2, "C0124", "C0123"))
            .build();
    protected static final Person boyd = Person.builder()
            .id("6")
            .firstName("Boyd")
            .lastName("Tinsley")
            .age(45)
//            .stringMap(AsCollections.of("key1", "val1", "key2", "val2"))
            .address(new Address(null, null, null, null))
            .build();
    protected static final Person stefan = Person.builder()
            .id("7")
            .firstName("Stefan")
            .lastName("Lessard")
            .age(34)
            .build();
    protected static final Person leroi = Person.builder()
            .id("8")
            .firstName("Leroi")
            .lastName("Moore")
            .age(44)
//            .intArray(new int[]{5, 6, 7, 8, 9, 10})
            .build();
    protected static final Person leroi2 = Person.builder()
            .id("9")
            .firstName("Leroi")
            .lastName("Moore")
            .age(25)
            .build();
    protected static final Person matias = Person.builder()
            .id("10")
            .firstName("Matias")
            .lastName("Craft")
            .age(24)
//            .intArray(new int[]{1, 2, 3, 4, 5})
            .build();
    protected static final Person douglas = Person.builder()
            .id("11")
            .firstName("Douglas")
            .lastName("Ford")
            .age(25)
            .build();
    protected static final List<Person> allPersons = List.of(dave, donny, oliver, alicia, carter, boyd, stefan,
            leroi, leroi2, matias, douglas);

    @BeforeAll
    void beforeAll() {
        repository.saveAll(allPersons);
    }

    @AfterEach
    void afterEach() {
        repository.deleteAll(allPersons);
    }

    @Autowired
    protected PersonRepository<Person> repository;
    @Test
    void findBySimplePropertyStartingWith_String() {
        repository.saveAll(allPersons);
        List<Person> result = repository.findByFirstNameStartsWith("D");

        assertThat(result).contains(dave, donny, douglas);
    }
}
