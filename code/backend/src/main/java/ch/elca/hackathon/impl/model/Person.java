package ch.elca.hackathon.impl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Person implements IdentifiableEntity {

    @Id
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    private Long id;

    @Version
    private Long version;

    // validation checks
    @Length(min = 2)
    @Column(nullable = false)
    @NotNull
    @NonNull
    private String name;

}
