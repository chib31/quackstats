package co.uk.cbradbury.quackstats.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "team")
public class Team {

    @Id
    private final UUID id;

    private String name;

    public Team() {
        this.id = UUID.randomUUID();
    }

    public Team(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
