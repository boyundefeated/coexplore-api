package com.coexplore.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.common.constant.UrlConstant;
import com.coexplore.api.common.response.ResponseStatus;
import com.coexplore.api.common.response.StandardResponse;
import com.coexplore.api.domain.User;
import com.coexplore.api.security.jwt.JWTFilter;
import com.coexplore.api.security.jwt.TokenProvider;
import com.coexplore.api.service.UserService;
import com.coexplore.api.service.dto.UserDTO;
import com.coexplore.api.web.rest.vm.LoginVM;
import com.coexplore.api.web.rest.vm.response.LoginResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */

@RestController
@RequestMapping(UrlConstant.URL_PREFIX + UrlConstant.AUTHENTICATION)
public class AuthenticationController {

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }
    
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Timed
    public StandardResponse<LoginResponse> authorize(@Valid @RequestBody LoginVM loginVM) {
        log.info("REST Login request: {}", loginVM.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        LoginResponse loginResponse = tokenProvider.createTokenResponse(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + loginResponse.getAccessToken());

        StandardResponse<LoginResponse> response = new StandardResponse<LoginResponse>(ResponseStatus.SUCCESS, loginResponse);
        return response;
    }

    @PostMapping("/social")
    @Timed
    @Transactional
    public StandardResponse<LoginResponse> loginWithSocial(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.loginWithSocial(userDTO);
        LoginResponse loginResponse = tokenProvider.createTokenResponseForSocial(user);
        StandardResponse<LoginResponse> response = new StandardResponse<LoginResponse>(ResponseStatus.SUCCESS, loginResponse);
        return response;
    }

    
    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    public StandardResponse<String> isAuthenticated(HttpServletRequest request) {
        log.debug("REST request isAuthenticated: {}", request.getRemoteUser());
        StandardResponse<String> response = new StandardResponse<String>(ResponseStatus.SUCCESS, request.getRemoteUser());
        return response;
    }

    @PostMapping("/logout")
    @Timed
    public StandardResponse<String> logout() {
        log.debug("REST request logout");
        StandardResponse<String> response = new StandardResponse<String>(ResponseStatus.SUCCESS, "Logout successfully");
        return response;
    }

}
