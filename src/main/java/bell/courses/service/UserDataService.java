package bell.courses.service;

import bell.courses.dao.CountriesRepository;
import bell.courses.dao.DocTypesRepository;
import bell.courses.dao.DocumentRepository;
import bell.courses.dao.OfficeRepository;
import bell.courses.dao.UserRepository;
import bell.courses.error.ApiException;
import bell.courses.model.Countries;
import bell.courses.model.DocTypes;
import bell.courses.model.Document;
import bell.courses.model.Office;
import bell.courses.model.User;
import bell.courses.view.request.UserFilterView;
import bell.courses.view.request.UserSaveView;
import bell.courses.view.request.UserUpdateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

import static bell.courses.dao.UserRepository.firstNameContains;
import static bell.courses.dao.UserRepository.hasCitizenship;
import static bell.courses.dao.UserRepository.hasDocType;
import static bell.courses.dao.UserRepository.hasOffice;
import static bell.courses.dao.UserRepository.middleNameContains;
import static bell.courses.dao.UserRepository.positionContains;
import static bell.courses.dao.UserRepository.secondNameContains;

/**
 * Service for operating with Users in the database
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Service
public class UserDataService {

    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;
    private final CountriesRepository countriesRepository;
    private final DocTypesRepository docTypesRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    UserDataService(UserRepository userRepository,
                    OfficeRepository officeRepository,
                    CountriesRepository countriesRepository,
                    DocTypesRepository docTypesRepository,
                    DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.officeRepository = officeRepository;
        this.countriesRepository = countriesRepository;
        this.docTypesRepository = docTypesRepository;
        this.documentRepository = documentRepository;
    }

    /**
     * Method for getting Office by his ID
     * @param id User's ID in the database
     * @return {@link User}
     */
    public User get(Long id) {
        User user = userRepository.getById(id);
        if (user == null) {
            throw new ApiException("No user with specified id found");
        } else {
            return user;
        }
    }

    /**
     * Method for getting list of Users from the database
     * @param request {@link UserFilterView} with filter params
     * @return List of {@link User}
     */
    public List<User> list(UserFilterView request) {
        Office office = officeRepository.getById(request.getOfficeId());
        if (office == null) {
            throw new ApiException("Specified office does not exist");
        }

        List<User> users = getUserList(office, request.getFirstName(), request.getSecondName(), request.getMiddleName(), request.getPosition(), request.getDocCode(), request.getCitizenshipCode());

        if (users.isEmpty()) {
            throw new ApiException("No users with specified parameters found");
        } else {
            return users;
        }
    }

    /**
     * Method for updating an User in the database
     * @param request {@link UserUpdateView}
     * @return true if successful
     */
    public Boolean update(UserUpdateView request) {
        User user = userRepository.getById(request.getId());
        if (user == null) {
            throw new ApiException("No user with specified id found");
        }
        setData(user,
                request.getOfficeId(),
                request.getFirstName(),
                request.getSecondName(),
                request.getMiddleName(),
                request.getPosition(),
                request.getPhone(),
                Objects.requireNonNullElse(user.getDocument().getDocType().getCode(), null),
                request.getDocName(),
                request.getDocNumber(),
                convertDate(request.getDocDate()),
                request.getCitizenshipCode(),
                request.getIsIdentified());
        userRepository.save(user);
        return true;
    }

    /**
     * Method for creating a new User in the database
     * @param request {@link UserSaveView}
     * @return true if successful
     */
    public Boolean save(UserSaveView request) {
        User user = new User();
        setData(user,
                request.getOfficeId(),
                request.getFirstName(),
                request.getSecondName(),
                request.getMiddleName(),
                request.getPosition(),
                request.getPhone(),
                request.getDocCode(),
                request.getDocName(),
                request.getDocNumber(),
                convertDate(request.getDocDate()),
                request.getCitizenshipCode(),
                request.getIsIdentified());
        if (user.getDocument()!= null) {
            user.setDocument(documentRepository.save(user.getDocument()));
        }
        userRepository.save(user);
        return true;
    }

    private void setData(User user,
                         Long officeId,
                         String firstName,
                         String secondName,
                         String middleName,
                         String position,
                         String phone,
                         Integer docCode,
                         String docName,
                         String docNumber,
                         Date docDate,
                         Integer citizenshipCode,
                         Boolean isIdentified) {
        Office office = null;
        if (officeId != null) {
            office = officeRepository.getById(officeId);
            if (office == null) {
                throw new ApiException("No offices with specified id found");
            }
        }
        Countries country = null;
        if (citizenshipCode != null) {
            country = countriesRepository.getByCode(citizenshipCode);
            if (country == null) {
                throw new ApiException("No countries with specified code found");
            }
        }
        DocTypes docType = null;
        if (docCode != null && user.getDocument() == null) {
            docType = docTypesRepository.getByCode(docCode);
            if (docType == null) {
                if (docName != null) {
                    docType = new DocTypes();
                    docType.setCode(docCode);
                    docType.setName(docName);
                    docType = docTypesRepository.save(docType);
                } else {
                    throw new ApiException("Name required to create a new doctype. Specify name or choose from already created DocTypes");
                }
            }
        } else if (docName != null) {
            docType = docTypesRepository.getByNameContaining(docName);
            if (docType == null) {
                throw new ApiException("No document types with specified name found");
            }
        }
        if (office != null) {
            user.setOffice(office);
        }
        user.setFirstName(firstName);
        if (secondName != null) {
            user.setSecondName(secondName);
        }
        if (middleName != null) {
            user.setMiddleName(middleName);
        }
        user.setPosition(position);
        if (phone != null) {
            user.setPhone(phone);
        }
        Document document = user.getDocument();
        if (docType != null) {
            if (document == null) {
                document = new Document();
            }
            document.setDocType(docType);
            user.setDocument(document);
        }
        if (docNumber != null) {
            if (document == null) {
                document = new Document();
            }
            document.setDocNumber(docNumber);
            user.setDocument(document);
        }
        if (docDate != null) {
            if (document == null) {
                document = new Document();
            }
            document.setDocDate(docDate);
            user.setDocument(document);
        }
        if (country != null) {
            user.setCountry(country);
        }
        user.setIsIdentified(Objects.requireNonNullElse(isIdentified, false));
    }

    private List<User> getUserList(Office office,
                                   String firstName,
                                   String secondName,
                                   String middleName,
                                   String position,
                                   Integer docCode,
                                   Integer citizenshipCode) {
        List<User> users;
        users = userRepository.findAll(Specification.where(hasOffice(office))
                                                    .and(firstNameContains(firstName))
                                                    .and(secondNameContains(secondName))
                                                    .and(middleNameContains(middleName))
                                                    .and(positionContains(position))
                                                    .and(hasDocType(docCode))
                                                    .and(hasCitizenship(citizenshipCode)));
        return users;
    }

    private Date convertDate(String date){
        Date result = null;
        if (date!=null) {
            result = Date.valueOf(date);
        }
        return result;
    }
}