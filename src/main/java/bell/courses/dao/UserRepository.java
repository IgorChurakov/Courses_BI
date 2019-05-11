package bell.courses.dao;

import bell.courses.model.Office;
import bell.courses.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User getById(Long id);

    List<User> findAllByOffice(Office office);

    List<User> findAllByOfficeAndFirstNameContaining(Office office, String firstName);

    List<User> findAllByOfficeAndSecondNameContaining(Office office, String secondName);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContaining(Office office, String firstName, String secondName);

    List<User> findAllByOfficeAndMiddleNameContaining(Office office, String middleName);

    List<User> findAllByOfficeAndFirstNameContainingAndMiddleNameContaining(Office office, String firstName, String middleName);

    List<User> findAllByOfficeAndSecondNameContainingAndMiddleNameContaining(Office office, String secondName, String middleName);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContaining(Office office, String firstName, String secondName, String middleName);

    List<User> findAllByOfficeAndPositionContaining(Office office, String position);

    List<User> findAllByOfficeAndFirstNameContainingAndPositionContaining(Office office, String firstName, String position);

    List<User> findAllByOfficeAndSecondNameContainingAndPositionContaining(Office office, String secondName, String position);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndPositionContaining(Office office, String firstName, String secondName, String position);

    List<User> findAllByOfficeAndMiddleNameContainingAndPositionContaining(Office office, String middleName, String position);

    List<User> findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndPositionContaining(Office office, String firstName, String middleName, String position);

    List<User> findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndPositionContaining(Office office, String secondName, String middleName, String position);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndPositionContaining(Office office, String firstName, String secondName, String middleName, String position);

    List<User> findAllByOfficeAndDocument_document_code(Office office, Integer docCode);

    List<User> findAllByOfficeAndFirstNameContainingAndDocument_document_code(Office office, String firstName, Integer docCode);

    List<User> findAllByOfficeAndSecondNameContainingAndDocument_document_code(Office office, String secondName, Integer docCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndDocument_document_code(Office office, String firstName, String secondName, Integer docCode);

    List<User> findAllByOfficeAndMiddleNameContainingAndDocument_document_code(Office office, String middleName, Integer docCode);

    List<User> findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndDocument_document_code(Office office, String firstName, String middleName, Integer docCode);

    List<User> findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndDocument_document_code(Office office, String secondName, String middleName, Integer docCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndDocument_document_code(Office office, String firstName, String secondName, String middleName, Integer docCode);

    List<User> findAllByOfficeAndPositionContainingAndDocument_document_code(Office office, String position, Integer docCode);

    List<User> findAllByOfficeAndFirstNameContainingAndPositionContainingAndDocument_document_code(Office office, String firstName, String position, Integer docCode);

    List<User> findAllByOfficeAndSecondNameContainingAndPositionContainingAndDocument_document_code(Office office, String secondName, String position, Integer docCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndPositionContainingAndDocument_document_code(Office office, String firstName, String secondName, String position, Integer docCode);

    List<User> findAllByOfficeAndMiddleNameContainingAndPositionContainingAndDocument_document_code(Office office, String middleName, String position, Integer docCode);

    List<User> findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_code(Office office, String firstName, String middleName, String position, Integer docCode);

    List<User> findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_code(Office office, String secondName, String middleName, String position, Integer docCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_code(Office office, String firstName, String secondName, String middleName, String position, Integer docCode);

    List<User> findAllByOfficeAndCountry_Code(Office office, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndCountry_Code(Office office, String firstName, Integer citizenshipCode);

    List<User> findAllByOfficeAndSecondNameContainingAndCountry_Code(Office office, String secondName, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndCountry_Code(Office office, String firstName, String secondName, Integer citizenshipCode);

    List<User> findAllByOfficeAndMiddleNameContainingAndCountry_Code(Office office, String middleName, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndCountry_Code(Office office, String firstName, String middleName, Integer citizenshipCode);

    List<User> findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndCountry_Code(Office office, String secondName, String middleName, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndCountry_Code(Office office, String firstName, String secondName, String middleName, Integer citizenshipCode);

    List<User> findAllByOfficeAndPositionContainingAndCountry_Code(Office office, String position, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndPositionContainingAndCountry_Code(Office office, String firstName, String position, Integer citizenshipCode);

    List<User> findAllByOfficeAndSecondNameContainingAndPositionContainingAndCountry_Code(Office office, String secondName, String position, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndPositionContainingAndCountry_Code(Office office, String firstName, String secondName, String position, Integer citizenshipCode);

    List<User> findAllByOfficeAndMiddleNameContainingAndPositionContainingAndCountry_Code(Office office, String middleName, String position, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndPositionContainingAndCountry_Code(Office office, String firstName, String middleName, String position, Integer citizenshipCode);

    List<User> findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndCountry_Code(Office office, String secondName, String middleName, String position, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndCountry_Code(Office office, String firstName, String secondName, String middleName, String position, Integer citizenshipCode);

    List<User> findAllByOfficeAndDocument_document_codeAndCountry_Code(Office office, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndDocument_document_codeAndCountry_Code(Office office, String firstName, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndSecondNameContainingAndDocument_document_codeAndCountry_Code(Office office, String secondName, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndDocument_document_codeAndCountry_Code(Office office, String firstName, String secondName, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndMiddleNameContainingAndDocument_document_codeAndCountry_Code(Office office, String middleName, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndDocument_document_codeAndCountry_Code(Office office, String firstName, String middleName, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndDocument_document_codeAndCountry_Code(Office office, String secondName, String middleName, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndDocument_document_codeAndCountry_Code(Office office, String firstName, String secondName, String middleName, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndPositionContainingAndDocument_document_codeAndCountry_Code(Office office, String position, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(Office office, String firstName, String position, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndSecondNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(Office office, String secondName, String position, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(Office office, String firstName, String secondName, String position, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndMiddleNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(Office office, String middleName, String position, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(Office office, String firstName, String middleName, String position, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(Office office, String secondName, String middleName, String position, Integer docCode, Integer citizenshipCode);

    List<User> findAllByOfficeAndFirstNameContainingAndSecondNameContainingAndMiddleNameContainingAndPositionContainingAndDocument_document_codeAndCountry_Code(Office office, String firstName, String secondName, String middleName, String position, Integer docCode, Integer citizenshipCode);
}