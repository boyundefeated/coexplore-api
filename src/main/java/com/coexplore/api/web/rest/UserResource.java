package com.coexplore.api.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.coexplore.api.common.constant.UrlConstant;
import com.coexplore.api.common.exception.CoExploreException;
import com.coexplore.api.common.response.DatatableResponse;
import com.coexplore.api.common.response.ListResult;
import com.coexplore.api.common.response.ResponseStatus;
import com.coexplore.api.common.response.StandardResponse;
import com.coexplore.api.config.Constants;
import com.coexplore.api.domain.User;
import com.coexplore.api.repository.UserRepository;
import com.coexplore.api.security.AuthoritiesConstants;
import com.coexplore.api.service.MailService;
import com.coexplore.api.service.UserService;
import com.coexplore.api.service.dto.UserDTO;
import com.coexplore.api.web.rest.vm.request.UserDataTableRequest;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of
 * authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship
 * between User and Authority, and send everything to the client side: there
 * would be no View Model and DTO, a lot less code, and an outer-join which
 * would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities,
 * because people will quite often do relationships with the user, and we don't
 * want them to get the authorities all the time for nothing (for performance
 * reasons). This is the #1 goal: we should not impact our users' application
 * because of this use-case.</li>
 * <li>Not having an outer join causes n+1 requests to the database. This is not
 * a real issue as we have by default a second-level cache. This means on the
 * first HTTP call we do the n+1 requests, but then all authorities come from
 * the cache, so in fact it's much better than doing an outer join (which will
 * get lots of data from the database, for each HTTP call).</li>
 * <li>As this manages users, for security reasons, we'd rather have a DTO
 * layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this
 * case.
 */
@RestController
@RequestMapping(UrlConstant.URL_PREFIX + UrlConstant.USER)
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
public class UserResource {

	private final Logger log = LoggerFactory.getLogger(UserResource.class);

	private final UserService userService;

	private final UserRepository userRepository;

	private final MailService mailService;

	public UserResource(UserService userService, UserRepository userRepository, MailService mailService) {

		this.userService = userService;
		this.userRepository = userRepository;
		this.mailService = mailService;
	}

	/**
	 * POST /users : Creates a new user.
	 * <p>
	 * Creates a new user if the login and email are not already used, and sends
	 * an mail with an activation link. The user needs to be activated on
	 * creation.
	 *
	 * @param userDTO
	 *            the user to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new user, or with status 400 (Bad Request) if the login or email
	 *         is already in use
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 * @throws BadRequestAlertException
	 *             400 (Bad Request) if the login or email is already in use
	 */
	@PostMapping("/")
	@Timed
	public StandardResponse<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
		log.debug("REST request to save User : {}", userDTO);

		if (userDTO.getId() != null) {
			throw new CoExploreException(ResponseStatus.USER_NEW_BUT_HAVE_ID);

			// Lowercase the user login before comparing with database
		} else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
			throw new CoExploreException(ResponseStatus.USER_LOGIN_ALREADY_USED);
		} else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
			throw new CoExploreException(ResponseStatus.USER_EMAIL_ALREADY_USED);
		} else {
			User newUser = userService.createUser(userDTO);
			mailService.sendCreationEmail(newUser);

			StandardResponse<User> response = new StandardResponse<User>(ResponseStatus.SUCCESS, newUser);
			return response;
		}
	}

	/**
	 * PUT /users : Updates an existing User.
	 *
	 * @param userDTO
	 *            the user to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         user
	 * @throws EmailAlreadyUsedException
	 *             400 (Bad Request) if the email is already in use
	 * @throws LoginAlreadyUsedException
	 *             400 (Bad Request) if the login is already in use
	 */
	@PutMapping("/")
	@Timed
	public StandardResponse<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
		log.debug("REST request to update User : {}", userDTO);
		Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
			throw new CoExploreException(ResponseStatus.USER_EMAIL_ALREADY_USED);
		}
		existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
			throw new CoExploreException(ResponseStatus.USER_LOGIN_ALREADY_USED);
		}
		UserDTO updatedUser = userService.updateUser(userDTO)
				.orElseThrow(() -> new CoExploreException(ResponseStatus.USER_UPDATE_FAIL));
		StandardResponse<UserDTO> response = new StandardResponse<UserDTO>(ResponseStatus.SUCCESS, updatedUser);
		return response;
	}

	/**
	 * GET /users : get all users.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and with body all users
	 */
	@GetMapping("/")
	@Timed
	public StandardResponse<ListResult<UserDTO>> getAllUsers(Pageable pageable) {
		Page<UserDTO> page = userService.getAllManagedUsers(pageable);
		ListResult<UserDTO> listResult = new ListResult<>();
		listResult.setList(page.getContent());
		listResult.setTotalCount(page.getTotalElements());
		StandardResponse<ListResult<UserDTO>> response = new StandardResponse<ListResult<UserDTO>>(
				ResponseStatus.SUCCESS, listResult);
		return response;
	}

	/**
	 * GET : get all for DataTable
	 *
	 * @param pageable
	 *            and order information
	 * @return the DatatableResponse with status 200 (OK) and with body all
	 *         users
	 */
	@GetMapping("/datatable")
	@Timed
	@SuppressWarnings("deprecation")
	public DatatableResponse<List<UserDTO>> getAllUsersDataTable(UserDataTableRequest dataTableRequest) {
		int offset = dataTableRequest.getStart() == null ? 0 : dataTableRequest.getStart();
		int pageSize = dataTableRequest.getLength() == null ? 10 : dataTableRequest.getLength();
		int pageNum = offset / pageSize;
		Long draw = dataTableRequest.getDraw() == null ? null : dataTableRequest.getDraw().longValue();
		PageRequest pageRequest = new PageRequest(pageNum, pageSize);
		Page<UserDTO> page = userService.getAllManagedUsers(pageRequest);
		DatatableResponse<List<UserDTO>> response = new DatatableResponse<List<UserDTO>>(draw, page.getTotalElements(),
				page.getContent());
		return response;
	}

	/**
	 * @return a string list of the all of the roles
	 */
	@GetMapping("/authorities")
	@Timed
	public StandardResponse<List<String>> getAuthorities() {
		List<String> list = userService.getAuthorities();
		StandardResponse<List<String>> response = new StandardResponse<List<String>>(ResponseStatus.SUCCESS, list);
		return response;
	}

	/**
	 * GET /users/:login : get the "login" user.
	 *
	 * @param login
	 *            the login of the user to find
	 * @return the ResponseEntity with status 200 (OK) and with body the "login"
	 *         user, or with status 404 (Not Found)
	 */
	@GetMapping("/{login:" + Constants.LOGIN_REGEX + "}")
	@Timed
	public StandardResponse<UserDTO> getUser(@PathVariable String login) {
		log.debug("REST request to get User : {}", login);
		UserDTO userDTO = userService.getUserWithAuthoritiesByLogin(login).map(UserDTO::new)
				.orElseThrow(() -> new CoExploreException(ResponseStatus.USER_NOT_FOUND));
		StandardResponse<UserDTO> response = new StandardResponse<UserDTO>(ResponseStatus.SUCCESS, userDTO);
		return response;
	}

	/**
	 * DELETE /users/:login : delete the "login" User.
	 *
	 * @param login
	 *            the login of the user to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/{login:" + Constants.LOGIN_REGEX + "}")
	@Timed
	public StandardResponse<String> deleteUser(@PathVariable String login) {
		log.debug("REST request to delete User: {}", login);
		userService.deleteUser(login);
		StandardResponse<String> response = new StandardResponse<String>(ResponseStatus.SUCCESS, "Successfully");
		return response;
	}
}
