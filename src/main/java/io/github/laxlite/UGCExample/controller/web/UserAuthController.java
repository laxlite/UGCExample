package io.github.laxlite.UGCExample.controller.web;

import io.github.laxlite.UGCExample.enums.PasswordErrorType;
import io.github.laxlite.UGCExample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/auth")
public class UserAuthController {
    private final UserService userService;

    @GetMapping
    public String getAuth(
            @RequestParam String mode,
            Model model
    ){
        if(Objects.equals(mode, "login")){
            model.addAttribute("templateName", "user/fragment/auth/login");
            model.addAttribute("fragmentName", "login");
        }
        else if(Objects.equals(mode, "registration")){
            model.addAttribute("templateName", "user/fragment/auth/registration_1");
            model.addAttribute("fragmentName", "registration1");
        }
        else{
            model.addAttribute("templateName", "user/fragment/auth/login_registration");
            model.addAttribute("fragmentName", "loginRegistration");
        }
        return "user/layout/auth";
    }

    @GetMapping("/fragment/registration_1")
    public String getRegStep1(Model model){
        return "user/fragment/auth/registration_1::registration1";
    }


    // В отличие от Get, делает проверку данных
    @PostMapping("/fragment/registration_2")
    public String getRegStep2WithCheck(
            @RequestParam String email,
            Model model){
        if(userService.isEmailExists(email)){
            model.addAttribute("emailError", true);
            return "user/fragment/auth/registration_1::registration1";
        }
        return "user/fragment/auth/registration_2::registration2";
    }

    @GetMapping("/fragment/registration_2")
    public String getRegStep2(){
        return "user/fragment/auth/registration_2::registration2";
    }

    // В отличие от Get, делает проверку данных
    @PostMapping("/fragment/registration_3")
    public String getRegStep3WithCheck(
            @RequestParam String password,
            @RequestParam String passwordConfirm,
            Model model){
        // Сначала проверка на совпадение
        if(!Objects.equals(password, passwordConfirm)){
            model.addAttribute("passwordErrorType", PasswordErrorType.CONFIRMATION_MISMATCH);
            return "user/fragment/auth/registration_2::registration2";
        }

        // Затем - на качество пароля
        // TODO: добавить проверки на качество пароля
        else if(password.length() < 8){
            model.addAttribute("passwordErrorType", PasswordErrorType.WEAK_PASSWORD);
            return "user/fragment/auth/registration_2::registration2";
        }
        else{
            return "user/fragment/auth/registration_3::registration3";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(
            @RequestParam String email,
            @RequestParam String password
    ){
        userService.loginUser(email, password);
//        TODO: добавить id сессии

        // Возврат ResponseEntity, так как post запрос происходит в XHR.
        // Обычная навигация через redirect не работает
        // Поэтому заставим браузер перейти на страницу через заголовки htmx
        return ResponseEntity.ok()
                .header("HX-Redirect", "/feed")
                .build();

//        TODO: если что-то не так - вернуть фрагмент loginRegistration
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name
    ){
//        TODO: добавить проверки на параметры
        userService.registerUser(email, password, name);
//        TODO: добавить id сессии

        // Возврат ResponseEntity, так как post запрос происходит в XHR.
        // Обычная навигация через redirect не работает
        // Поэтому заставим браузер перейти на страницу через заголовки htmx
        return ResponseEntity.ok()
                .header("HX-Redirect", "/feed")
                .build();
//        TODO: если что-то не так - вернуть фрагмент loginRegistration
    }



    @Autowired
    public UserAuthController(UserService userService){
        this.userService = userService;
    }
}
