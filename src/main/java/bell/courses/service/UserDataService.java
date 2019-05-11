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
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                docName = document.getDocument().getName();
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
                result.add(new UserListingView(user.getId(), user.getFirstName(), user.getSecondName(), user.getMiddleName(), user.getPosition()));
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
                Objects.requireNonNullElse(user.getDocument().getDocument().getCode(), null),
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
            user.getDocument().setUser(user);
            user = userRepository.save(user);
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
            document.setDocument(docType);
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
        if (citizenshipCode == null) {
            if (docCode == null) {
                if (position == null) {
                    if (middleName == null) {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOffice(office);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContaining(office, firstName);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContaining(office, secondName);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContaining(office, firstName, secondName);
                            }
                        }
                    } else {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndMiddleNameContaining(office, middleName);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndMiddleNameContaining(office, firstName, middleName);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndMiddleNameContaining(office, secondName, middleName);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContaining(office, firstName, secondName, middleName);
                            }
                        }
                    }
                } else {
                    if (middleName == null) {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndPositionContaining(office, position);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndPositionContaining(office, firstName, position);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndPositionContaining(office, secondName, position);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndPositionContaining(office, firstName, secondName, position);
                            }
                        }
                    } else {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndMiddleNameContainingAndPositionContaining(office, middleName, position);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndPositionContaining(office, firstName, middleName, position);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndPositionContaining(office, secondName, middleName, position);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndPositionContaining(office, firstName, secondName, middleName, position);
                            }
                        }
                    }
                }
            } else {
                if (position == null) {
                    if (middleName == null) {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndDocument_document_code(office, docCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndDocument_document_code(office, firstName, docCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndDocument_document_code(office, secondName, docCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndDocument_document_code(office, firstName, secondName, docCode);
                            }
                        }
                    } else {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndMiddleNameContainingAndDocument_document_code(office, middleName, docCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndDocument_document_code(office, firstName, middleName, docCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndDocument_document_code(office, secondName, middleName, docCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndDocument_document_code(office, firstName, secondName, middleName, docCode);
                            }
                        }
                    }
                } else {
                    if (middleName == null) {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndPositionContainingAndDocument_document_code(office, position, docCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndPositionContainingAndDocument_document_code(office, firstName, position, docCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndPositionContainingAndDocument_document_code(office, secondName, position, docCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndPositionContainingAndDocument_document_code(office, firstName, secondName, position, docCode);
                            }
                        }
                    } else {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndMiddleNameContainingAndPositionContainingAndDocument_document_code(office, middleName, position, docCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_code(office, firstName, middleName, position, docCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_code(office, secondName, middleName, position, docCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_code(office, firstName, secondName, middleName, position, docCode);
                            }
                        }
                    }
                }
            }
        } else {
            if (docCode == null) {
                if (position == null) {
                    if (middleName == null) {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndCountry_Code(office, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndCountry_Code(office, firstName, citizenshipCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndCountry_Code(office, secondName, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndCountry_Code(office, firstName, secondName, citizenshipCode);
                            }
                        }
                    } else {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndMiddleNameContainingAndCountry_Code(office, middleName, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndCountry_Code(office, firstName, middleName, citizenshipCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndCountry_Code(office, secondName, middleName, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndCountry_Code(office, firstName, secondName, middleName, citizenshipCode);
                            }
                        }
                    }
                } else {
                    if (middleName == null) {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndPositionContainingAndCountry_Code(office, position, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndPositionContainingAndCountry_Code(office, firstName, position, citizenshipCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndPositionContainingAndCountry_Code(office, secondName, position, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndPositionContainingAndCountry_Code(office, firstName, secondName, position, citizenshipCode);
                            }
                        }
                    } else {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndMiddleNameContainingAndPositionContainingAndCountry_Code(office, middleName, position, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndPositionContainingAndCountry_Code(office, firstName, middleName, position, citizenshipCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndCountry_Code(office, secondName, middleName, position, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndCountry_Code(office, firstName, secondName, middleName, position, citizenshipCode);
                            }
                        }
                    }
                }
            } else {
                if (position == null) {
                    if (middleName == null) {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndDocument_document_codeAndCountry_Code(office, docCode, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndDocument_document_codeAndCountry_Code(office, firstName, docCode, citizenshipCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndDocument_document_codeAndCountry_Code(office, secondName, docCode, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndDocument_document_codeAndCountry_Code(office, firstName, secondName, docCode, citizenshipCode);
                            }
                        }
                    } else {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndMiddleNameContainingAndDocument_document_codeAndCountry_Code(office, middleName, docCode, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndDocument_document_codeAndCountry_Code(office, firstName, middleName, docCode, citizenshipCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndDocument_document_codeAndCountry_Code(office, secondName, middleName, docCode, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndDocument_document_codeAndCountry_Code(office, firstName, secondName, middleName, docCode, citizenshipCode);
                            }
                        }
                    }
                } else {
                    if (middleName == null) {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndPositionContainingAndDocument_document_codeAndCountry_Code(office, position, docCode, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(office, firstName, position, docCode, citizenshipCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(office, secondName, position, docCode, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(office, firstName, secondName, position, docCode, citizenshipCode);
                            }
                        }
                    } else {
                        if (secondName == null) {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndMiddleNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(office, middleName, position, docCode, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(office, firstName, middleName, position, docCode, citizenshipCode);
                            }
                        } else {
                            if (firstName == null) {
                                users = userRepository.findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(office, secondName, middleName, position, docCode, citizenshipCode);
                            } else {
                                users = userRepository.findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(office, firstName, secondName, middleName, position, docCode, citizenshipCode);
                            }
                        }
                    }
                }
            }
        }
        return users;
    }
}