package capstone.test.sampledep;

import javax.persistence.*;

@Entity
@Table(name = "person2")
public class Person2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "firstname")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private int age;

    @Column(name = "covidinfo")
    private String covidInfo;
}