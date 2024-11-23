package insa.ctf.vulnwebapp.Service;

import insa.ctf.vulnwebapp.Entities.CompanyEntity;
import insa.ctf.vulnwebapp.Repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity addCompanyIfNotExists(String companyName) {
        CompanyEntity company = this.companyRepository.findByCompanyNameEqualsIgnoreCase(companyName);
        if (company == null) {
            company = new CompanyEntity();
            company.setCompanyName(companyName);
            this.companyRepository.save(company);
        }
        return company;
    }
}
