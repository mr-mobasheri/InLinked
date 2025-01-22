package dataAccess;

import models.Education;
import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EducationDAO extends DAO{
    public void updateUserEducation(String username, String nameOfTheSchool, String fieldOfStudy, long startStudying,
                                    long endOfEducation, String grade, String activities, String description) {
        Education education = getEducation(username);
        if(education != null) {
            education.setNameOfTheSchool(nameOfTheSchool);
            education.setFieldOfStudy(fieldOfStudy);
            education.setStartStudying(startStudying);
            education.setEndOfEducation(endOfEducation);
            education.setGrade(grade);
            education.setActivities(activities);
            education.setDescription(description);
            update(education);
        } else {
            education = new Education();
            education.setUsername(username);
            education.setNameOfTheSchool(nameOfTheSchool);
            education.setFieldOfStudy(fieldOfStudy);
            education.setStartStudying(startStudying);
            education.setEndOfEducation(endOfEducation);
            education.setGrade(grade);
            education.setActivities(activities);
            education.setDescription(description);
            insert(education);
        }
    }

    public Education getEducation(String  username) {
        Education education = null;
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            education = (Education) session.createQuery("FROM Education WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            transaction.commit();
        }
        return education;
    }
}
