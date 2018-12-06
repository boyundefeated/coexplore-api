package com.coexplore.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.common.constant.UrlConstant;
import com.coexplore.api.common.exception.CoExploreException;
import com.coexplore.api.common.response.ResponseStatus;
import com.coexplore.api.common.response.StandardResponse;
import com.coexplore.api.domain.User;
import com.coexplore.api.repository.UserRepository;
import com.coexplore.api.security.SecurityUtils;
import com.coexplore.api.service.MailService;
import com.coexplore.api.service.UserService;
import com.coexplore.api.service.dto.PasswordChangeDTO;
import com.coexplore.api.service.dto.UserDTO;
import com.coexplore.api.web.rest.vm.KeyAndPasswordVM;
import com.coexplore.api.web.rest.vm.ManagedUserVM;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.validation.Valid;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping(UrlConstant.URL_PREFIX + UrlConstant.ACCOUNT)
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already used
     */
    @PostMapping("/register")
    @Timed
    public StandardResponse<String> registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new CoExploreException(ResponseStatus.USER_INVALID_PASSWORD);
        }
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
        StandardResponse<String> response = new StandardResponse<String>(ResponseStatus.SUCCESS, "Successfully");
        return response;
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    @Timed
    public StandardResponse<String> activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new CoExploreException(ResponseStatus.USER_ACTIVATION_NOT_FOUND);
        }
        StandardResponse<String> response = new StandardResponse<String>(ResponseStatus.SUCCESS, "Successfully");
        return response;
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/")
    @Timed
    public  StandardResponse<UserDTO> getAccount() {
        UserDTO userDTO =  userService.getUserWithAuthorities().map(UserDTO::new).orElseThrow(() -> new CoExploreException(ResponseStatus.USER_NOT_FOUND));
        StandardResponse<UserDTO> response = new StandardResponse<UserDTO>(ResponseStatus.SUCCESS, userDTO);
        return response;
    }

    /**
     * PUT /account : update the current user information.
     *
     * @param userDTO the current user information
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws RuntimeException 500 (Internal Server Error) if the user login wasn't found
     */
    @PutMapping("/")
    @Timed
    public StandardResponse<String>  saveAccount(@Valid @RequestBody UserDTO userDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new CoExploreException(ResponseStatus.USER_CURRENT_LOGIN_NOT_FOUND));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new CoExploreException(ResponseStatus.USER_EMAIL_ALREADY_USED);
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new CoExploreException(ResponseStatus.USER_NOT_FOUND);
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getLangKey(), userDTO.getImageUrl());
        StandardResponse<String> response = new StandardResponse<String>(ResponseStatus.SUCCESS, "Successfully");
        return response;
    }

    /**
     * PUT  /account/change-password : changes the current user's password
     *
     * @param passwordChangeDto current and new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @PutMapping(path = "/change-password")
    @Timed
    public StandardResponse<String> changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new CoExploreException(ResponseStatus.USER_INVALID_PASSWORD);
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
        StandardResponse<String> response = new StandardResponse<String>(ResponseStatus.SUCCESS, "Successfully");
        return response;
    }

    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/reset-password/init")
    @Timed
    public StandardResponse<String> requestPasswordReset(@RequestBody String mail) {
        mailService.sendPasswordResetMail(userService.requestPasswordReset(mail).orElseThrow(() -> new CoExploreException(ResponseStatus.USER_EMAIL_NOT_FOUND)));
        StandardResponse<String> response = new StandardResponse<String>(ResponseStatus.SUCCESS, "Successfully");
        return response;
    }

    /**
     * POST   /account/reset-password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/reset-password/finish")
    @Timed
    public StandardResponse<String> finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new CoExploreException(ResponseStatus.USER_INVALID_PASSWORD);
        }
        Optional<User> user = userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new CoExploreException(ResponseStatus.USER_RESET_KEY_NOT_FOUND);
        }
        StandardResponse<String> response = new StandardResponse<String>(ResponseStatus.SUCCESS, "Successfully");
        return response;
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) && password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH && password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
