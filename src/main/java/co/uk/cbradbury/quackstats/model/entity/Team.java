package co.uk.cbradbury.quackstats.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JsonValue
    private final UUID uuid = UUID.randomUUID();

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "mainTeam")
    private Set<Player> playerSet;

    public Team() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Player> getPlayerSet() {
        return playerSet;
    }

    public void setPlayerSet(Set<Player> playerSet) {
        this.playerSet = playerSet;
    }
}
