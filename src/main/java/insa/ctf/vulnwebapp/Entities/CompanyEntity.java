package insa.ctf.vulnwebapp.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CompanyEntity {
    @Id @Setter(AccessLevel.NONE) @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompany;
    @Column(nullable = false, unique = true)
    private String companyName;
    @OneToMany(mappedBy = "company")
    private List<AppUserEntity> employees;
}
