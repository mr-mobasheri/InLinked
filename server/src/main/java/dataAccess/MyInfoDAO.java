package dataAccess;

import models.MyInformation;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MyInfoDAO extends DAO {
    public void updateUserMyInfo(String username, String additionalName,
                                 String headline, String currentJobPosition, String levelOfEducation,
                                 String educationPlace, String city, String profession,
                                 String condition) {
        MyInformation myInfo = getMyInfo(username);
        if (myInfo != null) {
            myInfo.setAdditionalName(additionalName);
            myInfo.setHeadline(headline);
            myInfo.setCurrentJobPosition(currentJobPosition);
            myInfo.setLevelOfEducation(levelOfEducation);
            myInfo.setEducationPlace(educationPlace);
            myInfo.setCondition(condition);
            myInfo.setCity(city);
            myInfo.setProfession(profession);
            update(myInfo);
        } else {
            myInfo = new MyInformation();
            myInfo.setUsername(username);
            myInfo.setAdditionalName(additionalName);
            myInfo.setHeadline(headline);
            myInfo.setCurrentJobPosition(currentJobPosition);
            myInfo.setLevelOfEducation(levelOfEducation);
            myInfo.setEducationPlace(educationPlace);
            myInfo.setCondition(condition);
            myInfo.setCity(city);
            myInfo.setProfession(profession);
            insert(myInfo);
        }
    }

    public MyInformation getMyInfo(String username) {
        MyInformation myInfo = null;
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            myInfo = (MyInformation) session.createQuery("FROM MyInformation WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            transaction.commit();
        }
        return myInfo;
    }
}
