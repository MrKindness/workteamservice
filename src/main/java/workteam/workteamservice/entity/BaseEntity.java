package workteam.workteamservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    private static final String GENERATOR_NAME = "UUID";
    private static final String GENERATOR_STRATEGY = "org.hibernate.id.UUIDGenerator";

    @Id
    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    @GeneratedValue(generator = GENERATOR_NAME)
    @GenericGenerator(name = GENERATOR_NAME, strategy = GENERATOR_STRATEGY)
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}