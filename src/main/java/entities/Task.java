package entities;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by Андрей on 07.08.2017.
 */
@Entity
public class Task {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
}
