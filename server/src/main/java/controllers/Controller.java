package controllers;

import dataAccess.DAO;

public abstract class Controller {
    DAO dao = new DAO();
}
