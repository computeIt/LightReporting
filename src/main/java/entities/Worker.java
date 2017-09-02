package entities;

import javax.persistence.Entity;

/**
 * Created by Андрей on 07.08.2017.
 */
@Entity
public class Worker {
    private String phone;   //uniqID
    private String name;
    private Task[] tasks;
}

