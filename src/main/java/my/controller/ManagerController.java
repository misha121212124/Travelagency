package my.controller;

import my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManagerController {
    @Autowired
    private UserService userService;

    @GetMapping("/manager")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "manager";
    }

    @PostMapping("/manager")
    public String  processingUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
            return "redirect:/manager";
        }else{
            if (action.equals("show")){
                model.addAttribute(userService.findUserById(userId));
                return "user_details";
            }
        }
        return "redirect:/manager";
    }
}
