package dataAccess;

import models.ContactInfo;
import models.Skills;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SkillsDAO extends DAO {
    public void updateUserSkills(String username, String skills1, String skills2, String skills3, String skills4,
                                      String skills5) {
        Skills skills = getSKills(username);
        if (skills != null) {
            skills.setSkills1(skills1);
            skills.setSkills2(skills2);
            skills.setSkills3(skills3);
            skills.setSkills4(skills4);
            skills.setSkills5(skills5);
            update(skills);
        } else {
            skills = new Skills();
            skills.setUsername(username);
            skills.setSkills1(skills1);
            skills.setSkills2(skills2);
            skills.setSkills3(skills3);
            skills.setSkills4(skills4);
            skills.setSkills5(skills5);
            insert(skills);
        }
    }

    public Skills getSKills(String username) {
        Skills skills = null;
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            skills = (Skills) session.createQuery("FROM Skills WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            transaction.commit();
        }
        return skills;
    }
}
