package bell.courses.service;

import bell.courses.dao.OfficeRepository;
import bell.courses.dao.OrganizationRepository;
import bell.courses.model.Office;
import bell.courses.model.Organization;
import bell.courses.view.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static bell.courses.dao.OfficeRepository.hasIsActive;
import static bell.courses.dao.OfficeRepository.hasOrganization;
import static bell.courses.dao.OfficeRepository.nameContains;
import static bell.courses.dao.OfficeRepository.phoneContains;

@Slf4j
@Service
public class OfficeDataService {

    private final OfficeRepository officeRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    OfficeDataService(OfficeRepository officeRepository, OrganizationRepository organizationRepository) {
        this.officeRepository = officeRepository;
        this.organizationRepository = organizationRepository;
    }


    public ResponseView get(Long id) {
        Office office;
        office = officeRepository.getById(id);
        if (office == null) {
            throw new ApiException("Office with specified ID does not exist");
        }
        return wrapInView(office);
    }

    public List<ResponseView> list(Long orgId, String name, String phone, Boolean isActive) {
        Organization organization = organizationRepository.getById(orgId);
        if (organization == null) {
            throw new ApiException("No organizations with specified id found");
        }
        List<Office> offices = getOfficeList(organization, name, phone, isActive);
        if (offices.isEmpty()) {
            throw new ApiException("No offices with specified parameters found");
        } else {
            List<ResponseView> result = new ArrayList<>();
            for (Office office : offices) {
                result.add(new OfficeListingView(
                        office.getId(),
                        office.getName(),
                        office.getIsActive()));
            }
            return result;
        }
    }

    public ResponseView update(Long id, String name, String address, String phone, Boolean isActive) {
        Office requestedOffice = officeRepository.getById(id);
        if (requestedOffice == null) {
            throw new ApiException("No offices with specified ID found");
        }
        setData(requestedOffice, name, address, phone, isActive);
        officeRepository.save(requestedOffice);
        return new ResultView("success");
    }

    public ResponseView save(Long orgId, String name, String address, String phone, Boolean isActive) {
        Office office = new Office();
        Organization officeOrganization = organizationRepository.getById(orgId);
        if (officeOrganization == null) {
            throw new ApiException("No organizations with specified orgId found");
        }
        office.setOrganization(officeOrganization);
        setData(office, name, address, phone, Objects.requireNonNullElse(isActive,false));
        officeRepository.save(office);
        return new ResultView("success");
    }

    private OfficeView wrapInView(Office office) {
        return new OfficeView(
                office.getId(),
                office.getName(),
                office.getAddress(),
                office.getPhone(),
                office.getIsActive());
    }

    @SuppressWarnings("Duplicates")
    private void setData(Office office, String name, String address, String phone, Boolean isActive) {
        office.setName(name);
        office.setAddress(address);
        if (phone != null) {
            office.setPhone(phone);
        }
        if (isActive != null) {
            office.setIsActive(isActive);
        }
    }

    private List<Office> getOfficeList(Organization organization, String name, String phone, Boolean isActive) {
        List<Office> offices;
        offices = officeRepository.findAll(Specification.where(hasOrganization(organization))
                                                        .and(nameContains(name))
                                                        .and(phoneContains(phone))
                                                        .and(hasIsActive(isActive)));
        return offices;
    }
}