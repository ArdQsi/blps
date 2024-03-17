package com.webapp.repository;

import com.webapp.model.UserEntity;
import com.webapp.model.Users;
import com.webapp.service.XMLService;

import java.util.List;


public class UserRepositoryImpl implements UserXmlRepository {
    private final XMLService xmlService;
    private final String xmlPath = "C:\\Users\\denvv\\OneDrive\\Desktop\\6 сем\\БЛПС\\blps\\src\\main\\resources\\users.xml";//Указать свой путь
    public UserRepositoryImpl(XMLService xmlService) {
        this.xmlService = xmlService;
    }

    public UserEntity findByUsername(String username) {
        Users users = (Users) xmlService.getEntity(Users.class, "userList", xmlPath);
        if (users==null || users.getUser()==null) return null;
        List<UserEntity> userEntities = users.getUser();
        for (UserEntity cur: userEntities) {
            if (cur.getUsername().equals(username)) return cur;
        }
        return null;
    }

    public UserEntity findByIdUser(Long id) {
        Users users = (Users) xmlService.getEntity(Users.class, "userList", xmlPath);
        if (users==null || users.getUser()==null) return null;
        List<UserEntity> userEntities = users.getUser();
        for (UserEntity cur: userEntities) {
            if (cur.getId().equals(id)) return cur;
        }
        return null;
    }


    public void save(UserEntity user) {
        Users users = (Users) xmlService.getEntity(Users.class, "users", xmlPath);
        if (users==null) users = new Users();
        boolean userExist = false;
        List<UserEntity> userEntities = users.getUser();
        for (UserEntity cur: userEntities) {
            if (cur.getId().equals(user.getId())){
                userEntities.remove(cur);
                userEntities.add(user);
                userExist = true;
                break;
            }
        }
        if(!userExist) {
            users.getUser().add(user);
        }
        xmlService.saveEntity(users.getUser(), xmlPath);
    }
}
