/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dao.UserDAO;
import dto.UserDTO;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
public class AuthenUtils {

    public static final String ADMIN_ROLE = "Founder";
    public static final String USER_ROLE = "Team Member";

    //ham nay de lay ra user 
    public static UserDTO getUser(String strUserID) {
        UserDAO udao = new UserDAO();
        UserDTO user = udao.readByID(strUserID);
        return user;
    }

    public static boolean isValidLogin(String strUserID, String strPassword) {
        UserDTO user = getUser(strUserID);
        if (user != null && user.getPassword().equals(strPassword)) {
            return true;
        } else {
            return false;
        }
    }
    //kiem tra nguoi dung da dang nhap chua
    public static boolean isLogged(HttpSession session){
        return session.getAttribute("user")!=null;
    }
    
    public static boolean isAdmin (HttpSession session){
        if (!isLogged(session)) {
            return false;
        } else {
        UserDTO user = (UserDTO)session.getAttribute("user");
        return user.getRole().equals("Founder");
        }
    }
}
