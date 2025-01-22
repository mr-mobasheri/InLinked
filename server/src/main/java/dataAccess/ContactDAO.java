package dataAccess;

import models.Connect;
import models.ContactInfo;
import models.Education;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ContactDAO extends DAO {
    public void updateUserContactInfo(String username, String email, String phoneNumber, String phoneType,
                                      String address, String birthday, String birthMonth, String birthdayDisplayPolicy,
                                      String instantCommunication) {
        ContactInfo contactInfo = getContactInfo(username);
        if (contactInfo != null) {
            contactInfo.setEmail(email);
            contactInfo.setPhoneNumber(phoneNumber);
            contactInfo.setPhoneType(phoneType);
            contactInfo.setAddress(address);
            contactInfo.setBirthday(birthday);
            contactInfo.setBirthMonth(birthMonth);
            contactInfo.setBirthdayDisplayPolicy(birthdayDisplayPolicy);
            contactInfo.setInstantCommunication(instantCommunication);
            update(contactInfo);
        } else {
            contactInfo = new ContactInfo();
            contactInfo.setUsername(username);
            contactInfo.setEmail(email);
            contactInfo.setPhoneNumber(phoneNumber);
            contactInfo.setPhoneType(phoneType);
            contactInfo.setAddress(address);
            contactInfo.setBirthday(birthday);
            contactInfo.setBirthMonth(birthMonth);
            contactInfo.setBirthdayDisplayPolicy(birthdayDisplayPolicy);
            contactInfo.setInstantCommunication(instantCommunication);
            insert(contactInfo);
        }
    }

    public ContactInfo getContactInfo(String username) {
        ContactInfo contactInfo = null;
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            contactInfo = (ContactInfo) session.createQuery("FROM ContactInfo WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            transaction.commit();
        }
        return contactInfo;
    }
}
