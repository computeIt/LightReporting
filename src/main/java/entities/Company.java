package entities;

import javax.persistence.Entity;

/**
 * Created by Андрей on 07.08.2017.
 */
@Entity
public class Company {
    private User owner;
    private String name;
    private String address;
    private Worker[] workers;
    //private String ownerPhone ???
}
