package bell.courses.service;

import bell.courses.dao.CountriesRepository;
import bell.courses.dao.DocTypesRepository;
import bell.courses.dao.DocumentRepository;
import bell.courses.dao.OfficeRepository;
import bell.courses.dao.UserRepository;
import bell.courses.model.Countries;
import bell.courses.model.DocTypes;
import bell.courses.model.Document;
import bell.courses.model.Office;
import bell.courses.model.User;
import bell.courses.view.ApiException;
import bell.courses.view.ResponseView;
import bell.courses.view.ResultView;
import bell.courses.view.UserListingView;
import bell.courses.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static bell.courses.dao.UserRepository.firstNameContains;
import static bell.courses.dao.UserRepository.hasCitizenship;
import static bell.courses.dao.UserRepository.hasDocType;
import static bell.courses.dao.UserRepository.hasOffice;
import static bell.courses.dao.UserRepository.middleNameContains;
import static bell.courses.dao.UserRepository.positionContains;
import static bell.courses.dao.UserRepository.secondNameContains;

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

    public ResponseView get(Long id) {
        User user = userRepository.getById(id);
        if (user == null) {
            throw new ApiException("No user with specified id found");
        } else {
            String docName = null;
            String docNumber = null;
            Date docDate = null;
            String countryName = null;
            Integer countryCode = null;

            Document document = user.getDocument();
            if (document != null){
                docName = document.getDocType().getName();
                docNumber = document.getDocNumber();
                docDate = document.getDocDate();
            }

            Countries country = user.getCountry();
            if (country!=null){
                countryName=country.getName();
                countryCode=country.getCode();
            }
            return new UserView(user.getId(),
                    user.getFirstName(),
                    user.getSecondName(),
                    user.getMiddleName(),
                    user.getPosition(),
                    user.getPhone(),
                    docName,
                    docNumber,
                    docDate,
                    countryName,
                    countryCode,
                    user.getIsIdentified());
        }
    }

    public List<ResponseView> list(Long officeId,
                                   String firstName,
                                   String secondName,
                                   String middleName,
                                   String position,
                                   Integer docCode,
                                   Integer citizenshipCode) {
        List<ResponseView> result = new ArrayList<>();
        Office office = officeRepository.getById(officeId);
        if (office == null) {
            throw new ApiException("Specified office does not exist");
        }

        List<User> users = getUserList(office, firstName, secondName, middleName, position, docCode, citizenshipCode);

        if (!users.isEmpty()) {
            for (User user : users) {
                result.add(new UserListingView(user.getId(),
                                               user.getFirstName(),
                                               user.getSecondName(),
                                               user.getMiddleName(),
                                               user.getPosition()));
            }
        } else {
            throw new ApiException("No users with specified parameters found");
        }
        return result;
    }

    public ResponseView update(Long id,
                               Long officeId,
                               String firstName,
                               String secondName,
                               String middleName,
                               String position,
                               String phone,
                               String docName,
                               String docNumber,
                               Date docDate,
                               Integer citizenshipCode,
                               Boolean isIdentified) {
        User user = userRepository.getById(id);
        if (user == null) {
            throw new ApiException("No user with specified id found");
        }
        setData(user,
                officeId,
                firstName,
                secondName,
                middleName,
                position,
                phone,
                Objects.requireNonNullElse(user.getDocument().getDocType().getCode(), null),
                docName,
                docNumber,
                docDate,
                citizenshipCode,
                isIdentified);
        userRepository.save(user);
        return new ResultView("success");
    }

    public ResponseView save(Long officeId,
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
        User user = new User();
        setData(user,
                officeId,
                firstName,
                secondName,
                middleName,
                position,
                phone,
                docCode,
                docName,
                docNumber,
                docDate,
                citizenshipCode,
                isIdentified);
        if (user.getDocument()!= null) {
            user.setDocument(documentRepository.save(user.getDocument()));
        }
        userRepository.save(user);
        return new ResultView("success");
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
}