package sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maxjensendk on 29-02-2016.
 */
public class ValidationController {

    private LoginController loginController;

    //TODO: Validate Create user
    public boolean validateCreateUser()
    {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(loginController.id.getText());
        if (m.find() && m.group().equals(loginController.id.getText())) {
            return true;
        }else{
            loginController.showAlert("ERROR", "Invalid ID number", "Please enter a valid ID.");
            return false;
        }
    }
}
