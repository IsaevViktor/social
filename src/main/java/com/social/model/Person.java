package com.social.model;

import javax.persistence.*;

@Entity
@NamedQuery(name = "findByName", query = "select p from Person p where p.name = :name")
@Table(name="Person")
public class Person {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    @JoinColumn(name = "address", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Address address;

    public Person(String name) {
        this.name = name;
    }

    public Person(){};

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "id="+id+", name="+name+", address="+ address;
    }
}