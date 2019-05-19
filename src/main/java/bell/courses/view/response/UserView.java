package bell.courses.view.response;

import lombok.Data;

import java.sql.Date;

/**
 * Response view for returning an user info
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class UserView {
    private long id;
    private String firstName;
    private String secondName;
    private String middleName;
    private String position;
    private String phone;
    private String docName;
    private String docNumber;
    private Date docDate;
    private String citizenshipName;
    private Integer citizenshipCode;
    private Boolean isIdentified;

    public UserView(long id, String firstName, String secondName, String middleName, String position, String phone, String docName, String docNumber, Date docDate, String citizenshipName, Integer citizenshipCode, Boolean isIdentified) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.position = position;
        this.phone = phone;
        this.docName = docName;
        this.docNumber = docNumber;
        this.docDate = docDate;
        this.citizenshipName = citizenshipName;
        this.citizenshipCode = citizenshipCode;
        this.isIdentified = isIdentified;
    }
}